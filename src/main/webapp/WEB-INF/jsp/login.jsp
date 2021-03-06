<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.login" var="pageLogin" />
<fmt:message bundle="${loc}" key="label.login" var="login" />
<fmt:message bundle="${loc}" key="label.password" var="password" />
<fmt:message bundle="${loc}" key="label.submit.login" var="loginSubmit" />
<fmt:message bundle="${loc}" key="label.login.message" var="loginMessage" />
<html>
<head>
    <title>${pageLogin}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/login.css"%>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${loginMessage}</h2>
<ul id="login">
<form name="login-form" action="${pageContext.request.contextPath}/controller?command=login" method="post">
    <label for="login-input">${login}</label>
    <input id="login-input" type="text" name="login" value=""/>
    <br><br>
    <label for="password-input">${password}</label>
    <input id="password-input" type="password" name="password" value=""/>
    <br><br>
    <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b style="color: red">${requestScope.errorLoginPassMessage}</b>
        <br>
    </c:if>
    <button type="submit">${loginSubmit}</button>
</form>
</ul>
</body>
</html>
