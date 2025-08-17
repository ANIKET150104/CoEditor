<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>CoEditor</title>
  <script src="https://unpkg.com/htmx.org@1.9.12"></script>
  <link rel="stylesheet" href="/css/app.css">
</head>
<body class="bg-dark">
  <main class="auth-wrap">
    <div class="auth-card">
      <h1>Login</h1>
      <form id="loginForm">
        <label>Room Name</label>
        <input name="username" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button type="submit">Login</button>
      </form>
      <a class="muted" href="/register">Create a Room</a>
    </div>
  </main>

  <script>
    // Store JWT in localStorage and redirect to /editor/{roomId}
    document.getElementById('loginForm').addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { username: fd.get('username'), password: fd.get('password') };

      try {
        const res = await fetch('/auth/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
        });
        if (!res.ok) { alert('Login failed'); return; }
        const data = await res.json(); // { token: "..." }
        localStorage.setItem('jwt', data.token);
        location.href = '/editor/' + encodeURIComponent(payload.username);
      } catch (err) {
        alert('Network error: ' + err.message);
      }
    });
  </script>
</body>
</html>
