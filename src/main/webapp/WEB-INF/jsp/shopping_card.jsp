<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
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
        <th>Price,$</th>
        <th>Status</th>
        <%--        <th></th>--%>
    </tr>
    <c:forEach var="subscription" items="${sessionScope.subscrshows}">
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
<c:if test="${not empty requestScope.errorChooseMessage}">
    <b style="color: red">${requestScope.errorChooseMessage}</b>
    <br>
</c:if>
<br>
<a>Total price: ${requestScope.totalPrice}</a>
<br><br>
<form name="payment" action="${pageContext.request.contextPath}/controller?command=confirmation" method="post">
<%--    <label for="card-name">Card Name:</label>--%>
<%--    <input id="card-name" type="text" name="card_name" value=""/>--%>
<%--    <br><br>--%>
    <label for="card-number">Card Number:</label>
    <input id="card-number" type="text" name="card_number" value=""/>
    <br><br>
    <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
    <br/>
    <c:if test="${not empty requestScope.errorCardMessage}">
        <b style="color: red">${requestScope.errorCardMessage}</b>
        <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorAmountMessage}">
        <b style="color: red">${requestScope.errorAmountMessage}</b>
        <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
        <br>
    </c:if>
    <input type="submit" value="Pay"/>
</form>
</body>
</html>