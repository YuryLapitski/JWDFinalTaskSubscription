<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<h3>${confMessage}</h3>

</body>
</html>