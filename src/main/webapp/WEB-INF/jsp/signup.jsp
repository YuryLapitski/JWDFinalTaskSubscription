<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<h3>Please sign up:</h3>
<form name="signup-form" action="${pageContext.request.contextPath}/controller?command=signup" method="post">
    <label for="signup-input">Login:</label>
    <input id="signup-input" type="text" name="signup" value=""/>
    <br>
    <label for="password-input">Password:</label>
    <input id="password-input" type="password" name="password" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorSignUpMessage}">
        <b>${requestScope.errorSignUpMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="Sign up"/>
</form>
</body>
</html>