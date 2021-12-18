<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.confirmation" var="pageConfirmation" />
<fmt:message bundle="${loc}" key="label.title.confMessage" var="confMessage" />
<html>
<head>
    <title>${pageConfirmation}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${confMessage}</h2>
</body>
</html>