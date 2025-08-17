package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteloginGenerated {
	public static final String JTE_NAME = "login.jte";
	public static final int[] JTE_LINE_INFO = {24,24,24,24,24,24,24,37,47,47,47,47,47,47};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n  <meta charset=\"utf-8\">\r\n  <title>Login</title>\r\n  <script src=\"https://unpkg.com/htmx.org@1.9.12\"></script>\r\n  <link rel=\"stylesheet\" href=\"/css/app.css\">\r\n</head>\r\n<body class=\"bg-dark\">\r\n  <main class=\"auth-wrap\">\r\n    <div class=\"auth-card\">\r\n      <h1>Login</h1>\r\n      <form id=\"loginForm\">\r\n        <label>Room Name</label>\r\n        <input name=\"username\" required>\r\n        <label>Password</label>\r\n        <input type=\"password\" name=\"password\" required>\r\n        <button type=\"submit\">Login</button>\r\n      </form>\r\n      <a class=\"muted\" href=\"/register\">Create a Room</a>\r\n    </div>\r\n  </main>\r\n\r\n  <script>\r\n    ");
		jteOutput.writeContent("\n    document.getElementById('loginForm').addEventListener('submit', async (e) => {\r\n      e.preventDefault();\r\n      const fd = new FormData(e.target);\r\n      const payload = { username: fd.get('username'), password: fd.get('password') };\r\n\r\n      try {\r\n        const res = await fetch('/auth/login', {\r\n          method: 'POST',\r\n          headers: { 'Content-Type': 'application/json' },\r\n          body: JSON.stringify(payload)\r\n        });\r\n        if (!res.ok) { alert('Login failed'); return; }\r\n        const data = await res.json(); ");
		jteOutput.writeContent("\n        localStorage.setItem('jwt', data.token);\r\n        location.href = '/editor/' + encodeURIComponent(payload.username);\r\n      } catch (err) {\r\n        alert('Network error: ' + err.message);\r\n      }\r\n    });\r\n  </script>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
