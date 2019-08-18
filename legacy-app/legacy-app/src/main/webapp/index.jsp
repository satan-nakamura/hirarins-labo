<%@ page contentType="text/html; charset=UTF-8" %>
<html>

<body>
    <h2>login</h2>
    <form action="/legacy-app/sample" method="POST">
        <div>
            <label style="width: 40px;">ID</label><input type="text" name="login.userId" value="" />
        </div>
        <div>
            <label style="width: 40px;">PW</label><input type="password" name="login.passcode" value="" />
        </div>
        <div>
            <input type="submit" value="login" name="login.login">
        </div>
    </form>
</body>

</html>