package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JteregisterGenerated {
	public static final String JTE_NAME = "register.jte";
	public static final int[] JTE_LINE_INFO = {36,36,36,36,36,36,36,46,46,46,46,46,46};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor) {
		jteOutput.writeContent("<!doctype html>\r\n<html lang=\"en\">\r\n<head>\r\n  <meta charset=\"utf-8\">\r\n  <title>Create Room</title>\r\n  <script src=\"https://unpkg.com/htmx.org@1.9.12\"></script>\r\n  <link rel=\"stylesheet\" href=\"/css/app.css\">\r\n</head>\r\n<body class=\"bg-dark\">\r\n  <main class=\"auth-wrap\">\r\n    <div class=\"auth-card\">\r\n      <h1>Create a Room</h1>\r\n      <form id=\"regForm\">\r\n        <label>Room Name</label>\r\n        <input name=\"username\" required>\r\n        <label>Password</label>\r\n        <input type=\"password\" name=\"password\" required>\r\n        <button type=\"submit\">Register</button>\r\n      </form>\r\n      <p>Already have one? <a class=\"muted\" href=\"/login\">Back to Login</a></p>\r\n    </div>\r\n  </main>\r\n\r\n  <script>\r\n    document.getElementById('regForm').addEventListener('submit', async (e) => {\r\n      e.preventDefault();\r\n      const fd = new FormData(e.target);\r\n      const payload = { username: fd.get('username'), password: fd.get('password') };\r\n\r\n      try {\r\n        const res = await fetch('/auth/register', {\r\n          method: 'POST',\r\n          headers: { 'Content-Type': 'application/json' },\r\n          body: JSON.stringify(payload)\r\n        });\r\n        if (!res.ok) { alert('Registration failed'); return; }\r\n        const data = await res.json(); ");
		jteOutput.writeContent("\n        localStorage.setItem('jwt', data.token);\r\n        location.href = '/editor/' + encodeURIComponent(payload.username);\r\n      } catch (err) {\r\n        alert('Network error: ' + err.message);\r\n      }\r\n    });\r\n  </script>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		render(jteOutput, jteHtmlInterceptor);
	}
}
