<%@ page contentType="text/html; charset=UTF-8" %>
<html>

<body>
    <h2>Account Locked.</h2>
    <p>please unlock request this form.</p>
    <form action="/legacy-app/unlock" method="POST">
        <div>
            <label style="width: 40px;">ID</label><input type="text" name="unlock.userId" value="" />
        </div>
        <div>
            <label style="width: 40px;">PW</label><input type="password" name="unlock.passcode" value="" />
        </div>
        <div>
            <input type="submit" value="unlock" name="unlock.unlock">
        </div>
    </form>
</body>

</html>