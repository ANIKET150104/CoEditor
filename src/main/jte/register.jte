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
      <h1>Create a Room</h1>
      <form id="regForm">
        <label>Room Name</label>
        <input name="username" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button type="submit">Register</button>
      </form>
      <p>Already have one? <a class="muted" href="/login">Back to Login</a></p>
    </div>
  </main>

  <script>
    document.getElementById('regForm').addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { username: fd.get('username'), password: fd.get('password') };

      try {
        const res = await fetch('/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
        });
        if (!res.ok) { alert('Registration failed'); return; }
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
