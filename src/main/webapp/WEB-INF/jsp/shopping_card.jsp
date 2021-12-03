<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping card</title>
</head>
<body>
<h3>Shopping card</h3>
<table>
    <tr>
        <th>Edition</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Term</th>
        <th>Price</th>
        <th>Status</th>
        <%--        <th></th>--%>
    </tr>
    <c:forEach var="subscription" items="${sessionScope.subscriptions}">
        <tr>
            <td>${subscription.editionName}</td>
            <td>${subscription.firstName}</td>
            <td>${subscription.lastName}</td>
            <td>${subscription.term}</td>
            <td>${subscription.price}</td>
            <td>${subscription.status}</td>
        </tr>
    </c:forEach>
</table>
<a>Total price: ${requestScope.totalPrice}</a>
</body>
</html>
