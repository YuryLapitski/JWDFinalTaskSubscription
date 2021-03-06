<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.signup" var="pageSignup" />
<fmt:message bundle="${loc}" key="label.login" var="login" />
<fmt:message bundle="${loc}" key="label.password" var="password" />
<fmt:message bundle="${loc}" key="label.submit.signUp" var="signUpSubmit" />
<fmt:message bundle="${loc}" key="label.login.message" var="loginMessage" />
<fmt:message bundle="${loc}" key="label.errorSignUpMessage" var="errorSignUp" />
<fmt:message bundle="${loc}" key="label.errorPasswordMismatchMessage" var="errorPasswordMismatch" />
<fmt:message bundle="${loc}" key="label.errorAccountExistMessage" var="errorAccountExist" />
<fmt:message bundle="${loc}" key="label.signupMessage" var="signupMessage" />
<html>
<head>
    <title>${pageSignup}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/login.css"%>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${signupMessage}</h2>
<ul id="login">
<form name="signup-form" action="${pageContext.request.contextPath}/controller?command=signup" method="post">
    <label for="signup-input">${login}</label>
    <input id="signup-input" type="text" name="signup" value=""/>
    <br><br>
    <label for="password-input">${password}</label>
    <input id="password-input" type="password" name="password" value=""/>
    <br><br>
    <label for="passwordCopy-input">${password}</label>
    <input id="passwordCopy-input" type="password" name="passwordCopy" value=""/>
    <br><br>
    <c:if test="${not empty requestScope.errorSignUpMessage}">
        <b style="color: red">${errorSignUp}</b>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorPasswordMismatchMessage}">
        <b style="color: red">${errorPasswordMismatch}</b>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorAccountExistMessage}">
        <b style="color: red">${errorAccountExist}</b>
        <br>
    </c:if>
    <button type="submit">${signUpSubmit}</button>
</form>
</ul>
</body>
</html>