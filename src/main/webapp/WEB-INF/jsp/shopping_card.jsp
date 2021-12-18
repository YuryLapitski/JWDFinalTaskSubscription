<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.shoppingCard" var="pageShoppingCard" />
<fmt:message bundle="${loc}" key="label.message.shoppingCard" var="shoppingCard" />
<fmt:message bundle="${loc}" key="label.field.edition" var="edition" />
<fmt:message bundle="${loc}" key="label.field.firstName" var="firstName" />
<fmt:message bundle="${loc}" key="label.field.lastName" var="lastName" />
<fmt:message bundle="${loc}" key="label.field.term" var="term" />
<fmt:message bundle="${loc}" key="label.field.price" var="price" />
<fmt:message bundle="${loc}" key="label.field.status" var="status" />
<fmt:message bundle="${loc}" key="label.field.totalPrice" var="totalPrice" />
<fmt:message bundle="${loc}" key="label.field.cardNumber" var="cardNumber" />
<fmt:message bundle="${loc}" key="label.errorChooseMessage" var="errorChoose" />
<fmt:message bundle="${loc}" key="label.errorAmountMessage" var="errorAmount" />
<fmt:message bundle="${loc}" key="label.errorCardMessage" var="errorCard" />
<fmt:message bundle="${loc}" key="label.submit.pay" var="pay" />
<fmt:message bundle="${loc}" key="label.submit.deleteItem" var="deleteItem" />
<html>
<head>
    <title>${pageShoppingCard}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/table.css"%>
    <%@include file="/WEB-INF/css/text.css"%>
    <%@include file="/WEB-INF/css/shoppingCard.css"%>
</style>
<h2>${shoppingCard}</h2>
<table id="table">
    <tr>
        <th>${edition}</th>
        <th>${firstName}</th>
        <th>${lastName}</th>
        <th>${term}</th>
        <th>${price},$</th>
        <th>${status}</th>
        <%--        <th></th>--%>
    </tr>
    <c:forEach var="subscription" items="${sessionScope.subscrshows}" varStatus="status">
        <tr>
            <td>${subscription.editionName}</td>
            <td>${subscription.firstName}</td>
            <td>${subscription.lastName}</td>
            <td>${subscription.term}</td>
            <td>${subscription.price}</td>
            <td>${subscription.status}</td>
            <td>
                <form name="delete_item" action="${pageContext.request.contextPath}
                    /controller?command=delete_from_shopping_card" method="post">
                    <input type="hidden" name="index" value="${status.index}"/>
                    <button type="submit" >${deleteItem}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty requestScope.errorChooseMessage}">
    <h2 style="color: red">${errorChoose}</h2>
    <br>
</c:if>
<br>
<h2>${totalPrice} ${requestScope.totalPrice}</h2>
<br><br>
<ul id="shoppingCard">
<form name="payment" action="${pageContext.request.contextPath}/controller?command=confirmation" method="post">
    <label for="card-number">${cardNumber}</label>
    <input id="card-number" type="text" name="card_number" value=""/>
    <br><br>
    <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
    <br/>
    <c:if test="${not empty requestScope.errorCardMessage}">
        <b style="color: red">${errorCard}</b>
        <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorAmountMessage}">
        <b style="color: red">${errorAmount}</b>
        <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}"/>
        <br>
    </c:if>
    <button type="submit" >${pay}</button>
</form>
</ul>
</body>
</html>