<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CoEditor</title>
  <script src="https://unpkg.com/htmx.org@1.9.12"></script>
  <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>

  <link rel="stylesheet" href="https://unpkg.com/codemirror@5.65.15/lib/codemirror.css">
  <script src="https://unpkg.com/codemirror@5.65.15/lib/codemirror.js"></script>
  <script src="https://unpkg.com/codemirror@5.65.15/mode/javascript/javascript.js"></script>
  <script src="https://unpkg.com/codemirror@5.65.15/mode/python/python.js"></script>
  <script src="https://unpkg.com/codemirror@5.65.15/mode/clike/clike.js"></script>
  <link rel="stylesheet" href="/css/app.css">

  <style>
    .panes { display:flex; gap:16px; }
    .pane { flex:1; min-height:480px; background:#fff; border:1px solid #ddd; border-radius:10px; }
    #editor { height: 60vh; }
    #spinner { display:none; }
    .htmx-request #spinner { display:inline-block; }
  </style>
</head>
<body>
  <div class="container">
    <h1>Collaborative Editor</h1>

    <div style="display:flex; gap:12px; align-items:center; margin-bottom:12px;">
      <label>Language</label>
      <select id="langSel">
        <option value="63" data-mode="javascript">JavaScript</option>
        <option value="71" data-mode="python">Python</option>
        <option value="54" data-mode="text/x-c++src">C++</option>
        <option value="62" data-mode="text/x-java">Java</option>
      </select>

      <button id="runBtn"
              hx-post="/run"
              hx-target="#output"
              hx-indicator="#spinner"
              hx-headers='{"Accept": "text/plain"}'>
        Run
      </button>
      <span id="spinner" class="spinner"></span>
    </div>

    <div class="panes">
      <div class="pane"><div id="editor"></div></div>
      <div class="pane">
        <textarea id="stdin" placeholder="stdin..." rows="6" style="width:100%;border:0;border-bottom:1px solid #ddd;padding:8px"></textarea>
        <pre id="output" style="margin:0;padding:12px;white-space:pre-wrap"></pre>
      </div>
    </div>
  </div>

  <script>
    // Attach JWT to all HTMX requests + add params for /run
    document.body.addEventListener("htmx:configRequest", function(evt) {
      const token = localStorage.getItem("jwt");
      if (token) evt.detail.headers["Authorization"] = "Bearer " + token;
      evt.detail.parameters["languageId"] = document.getElementById('langSel').value;
      evt.detail.parameters["stdin"] = document.getElementById('stdin').value;
      evt.detail.parameters["code"] = (window.cm) ? window.cm.getValue() : '';
    });

    // CodeMirror
    const cm = CodeMirror(document.getElementById('editor'), {
      value: "// Write code here\n",
      lineNumbers: true,
      mode: "javascript"
    });
    window.cm = cm;

    const langSel = document.getElementById('langSel');
    langSel.addEventListener('change', function () {
      cm.setOption('mode', this.selectedOptions[0].dataset.mode);
      sendEditorEvent('LANG_CHANGE', null);
    });

    // STOMP over SockJS with JWT in handshake (?token=...)
    const roomId = location.pathname.split('/').pop();
    let stomp = null;
    const clientId = Math.random().toString(36).slice(2);

    function connectWS() {
      const token = localStorage.getItem("jwt") || "";
      const sock = new SockJS('/ws?token=' + encodeURIComponent(token));
      stomp = Stomp.over(sock);
      stomp.debug = null;
      // NOTE: No headers here; token is in the handshake URL
      stomp.connect({}, onConnect, onError);
    }

    function onConnect() {
      stomp.subscribe('/topic/' + roomId, frame => {
        const msg = JSON.parse(frame.body);
        switch (msg.type) {
          case 'FULL_SYNC':
            if (msg.language) {
              const target = String(msg.language);
              const opt = [...langSel.options].find(o => o.value === target);
              if (opt) { opt.selected = true; cm.setOption('mode', opt.dataset.mode); }
            }
            if (typeof msg.code === 'string') cm.setValue(msg.code);
            break;
          case 'LANG_CHANGE':
            {
              const target = String(msg.language);
              const opt = [...langSel.options].find(o => o.value === target);
              if (opt) { opt.selected = true; cm.setOption('mode', opt.dataset.mode); }
            }
            break;
          case 'CODE_UPDATE':
          default:
            if (msg.sender !== clientId && typeof msg.code === 'string') {
              cm.setValue(msg.code);
            }
        }
      });

      // Ask server for the current doc state
      sendEditorEvent('REQUEST_FULL', null);
    }

    function onError() {
      alert('WebSocket disconnected. Reconnecting in 3s…');
      setTimeout(connectWS, 3000);
    }

    connectWS();

    function sendEditorEvent(type, codeOverride) {
      if (!stomp || !stomp.connected) return;
      const payload = {
        type,
        roomId,
        sender: clientId,
        code: codeOverride != null ? codeOverride : cm.getValue(),
        language: document.getElementById('langSel').value
      };
      stomp.send('/app/editor/' + roomId, {}, JSON.stringify(payload));
    }

    let debounce;
    cm.on('change', () => {
      clearTimeout(debounce);
      debounce = setTimeout(() => sendEditorEvent('CODE_UPDATE', null), 200);
    });
  </script>
</body>
</html>
