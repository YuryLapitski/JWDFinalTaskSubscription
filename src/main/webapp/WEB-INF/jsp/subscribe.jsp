<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.subscriptionInfo" var="subscriptionInfo" />
<fmt:message bundle="${loc}" key="label.message.subscriptionInfo" var="subscriptionInfoMessage" />
<fmt:message bundle="${loc}" key="label.field.firstName" var="firstName" />
<fmt:message bundle="${loc}" key="label.field.lastName" var="lastName" />
<fmt:message bundle="${loc}" key="label.field.email" var="email" />
<fmt:message bundle="${loc}" key="label.field.deliveryAddress" var="deliveryAddress" />
<fmt:message bundle="${loc}" key="label.field.city" var="city" />
<fmt:message bundle="${loc}" key="label.field.street" var="streetField" />
<fmt:message bundle="${loc}" key="label.field.house" var="houseField" />
<fmt:message bundle="${loc}" key="label.field.flat" var="flatField" />
<fmt:message bundle="${loc}" key="label.field.subTerm" var="subTerm" />
<fmt:message bundle="${loc}" key="label.months" var="months" />
<fmt:message bundle="${loc}" key="label.submit.addToSC" var="addToSC" />
<html>
<head>
    <title>${subscriptionInfo}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${subscriptionInfoMessage} "${requestScope.edition.name}":</h2>
<p>${firstName}: ${requestScope.user.firstName}</p>
<p>${lastName}: ${requestScope.user.lastName}</p>
<p>${email}: ${requestScope.user.email}</p>
<br>
<p>${deliveryAddress}:</p>
<p>${city} ${requestScope.address.city}</p>
<p>${streetField} ${requestScope.address.street}</p>
<p>${houseField} ${requestScope.address.house}</p>
<p>${flatField} ${requestScope.address.flat}</p>
<br>
<p>${subTerm}: ${requestScope.term.months} ${months}</p>
<p>Price: $${requestScope.price.value}</p>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=add_to_shopping_card" method="post">
    <input type="hidden" name="userId" value="${requestScope.user.id}"/>
    <input type="hidden" name="addressId" value="${requestScope.address.id}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="termId" value="${requestScope.term.id}"/>
    <input type="hidden" name="priceId" value="${requestScope.price.id}"/>
    <input type="hidden" name="statusId" value="1"/>
    <button type="submit">${addToSC}</button>
</form>
</body>
</html>
