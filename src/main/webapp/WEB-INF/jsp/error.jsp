<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.error" var="pageError" />
<fmt:message bundle="${loc}" key="label.errorMessage" var="errorMessage" />
<html>
<head>
    <title>${pageError}</title>
</head>
<body>
<h3>${errorMessage}</h3>
</body>
</html>