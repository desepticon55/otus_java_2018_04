<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
</head>
<body>
<div class="top">
    <a href="login">Login</a>
    <a href="admin">Admin</a>
</div>
<div class="login">
    <form action="/login" method="POST">
        login: <input type="text" name="login"/>
        <input type="submit" value="submit">
    </form>
    <p>Your login: ${login}</p>
    <p>${help}</p>
</div>
</body>
</html>
