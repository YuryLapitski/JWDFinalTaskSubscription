<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>Subscriptions</title>
</head>
<body>
<h3>Subscriptions</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Account Id</th>
        <th>Edition</th>
        <th>Term</th>
        <th>Price</th>
        <th>Address</th>
        <th>Status</th>
        <th>Subscription Date</th>
    </tr>
    <c:forEach var="archive" items="${requestScope.archives}">
        <tr>
            <td>${archive.id}</td>
            <td>${archive.accId}</td>
            <td>${archive.editionName}
            <td>${archive.term} Months</td>
            <td>$${archive.price}</td>
            <td>${archive.city}, ${archive.street}, ${archive.house}, ${archive.flat}</td>
            <td>${archive.statusName}</td>
            <td>${archive.date}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


