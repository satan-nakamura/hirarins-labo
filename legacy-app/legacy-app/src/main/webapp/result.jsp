<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="user" type="labo.hirarins.legacy.app.UserDaoRmi.User" scope="session"/>
<html>

<body>
    <h2>hello ${user.userName}.</h2>
    <p style="display : ${user.unlockTargetFlag ? 'block' : 'none'};">
        accept unlock request.<br>
        please wait a few minites.
    </p>
</body>

</html>