<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.subscriptions" var="pageSubscriptions" />
<fmt:message bundle="${loc}" key="label.subscriptions" var="subscriptions" />
<fmt:message bundle="${loc}" key="label.field.subId" var="subId" />
<fmt:message bundle="${loc}" key="label.field.accId" var="accId" />
<fmt:message bundle="${loc}" key="label.field.edition" var="edition" />
<fmt:message bundle="${loc}" key="label.field.term" var="term" />
<fmt:message bundle="${loc}" key="label.field.price" var="price" />
<fmt:message bundle="${loc}" key="label.field.address" var="address" />
<fmt:message bundle="${loc}" key="label.field.status" var="status" />
<fmt:message bundle="${loc}" key="label.field.subDate" var="subDate" />
<fmt:message bundle="${loc}" key="label.months" var="months" />
<html>
<head>
    <title>${pageSubscriptions}</title>
</head>
<body>
<h3>${subscriptions}</h3>
<table>
    <tr>
        <th>${subId}</th>
        <th>${accId}</th>
        <th>${edition}</th>
        <th>${term}</th>
        <th>${price}</th>
        <th>${address}</th>
        <th>${status}</th>
        <th>${subDate}</th>
    </tr>
    <c:forEach var="archive" items="${requestScope.archives}">
        <tr>
            <td>${archive.id}</td>
            <td>${archive.accId}</td>
            <td>${archive.editionName}
            <td>${archive.term} ${months}</td>
            <td>$${archive.price}</td>
            <td>${archive.city}, ${archive.street}, ${archive.house}, ${archive.flat}</td>
            <td>${archive.statusName}</td>
            <td>${archive.date}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>