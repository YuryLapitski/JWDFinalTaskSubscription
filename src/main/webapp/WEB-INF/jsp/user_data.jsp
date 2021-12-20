<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.userData" var="titleUserData" />
<fmt:message bundle="${loc}" key="label.userDataMessage" var="userDataMessage" />
<fmt:message bundle="${loc}" key="label.field.firstName" var="firstName" />
<fmt:message bundle="${loc}" key="label.field.lastName" var="lastName" />
<fmt:message bundle="${loc}" key="label.field.age" var="age" />
<fmt:message bundle="${loc}" key="label.field.email" var="email" />
<fmt:message bundle="${loc}" key="label.errorUserDataMessage" var="errorUserData" />
<fmt:message bundle="${loc}" key="label.submit.userData" var="submitUserData" />
<html>
<head>
    <title>${titleUserData}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/user_data.css"%>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${userDataMessage}</h2>
<ul id="user-data">
<form name="user_data-form" action="${pageContext.request.contextPath}/controller?command=user_data" method="post">
    <label for="first_name-input">${firstName}:</label>
    <input id="first_name-input" type="text" name="first_name" value="${requestScope.user.firstName}"/>
    <br>
    <label for="last_name-input">${lastName}:</label>
    <input id="last_name-input" type="text" name="last_name" value="${requestScope.user.lastName}"/>
    <br>
    <label for="age-input">${age}:</label>
    <input id="age-input" type="number" name="age" value="${requestScope.user.age}"/>
    <br>
    <label for="email-input">${email}:</label>
    <input id="email-input" type="text" name="email" value="${requestScope.user.email}"/>
    <br/>
    <c:if test="${not empty requestScope.errorUserDataMessage}">
        <b style="color: red">${errorUserData}</b>
        <br>
    </c:if>
    <button type="submit">${submitUserData}</button>
</form>
</ul>
</body>
</html>