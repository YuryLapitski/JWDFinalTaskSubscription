<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Status" %>
<html>
<head>
    <title>Subscribe</title>
</head>
<body>
<h3>Please check data to subscription to "${requestScope.edition.name}":</h3>
<p>First Name: ${requestScope.user.firstName}</p>
<p>Last Name: ${requestScope.user.lastName}</p>
<p>Email: ${requestScope.user.email}</p>
<br>
<p>Delivery address:</p>
<p>Address id: ${requestScope.address.id}</p>
<p>City: ${requestScope.address.city}</p>
<p>Street: ${requestScope.address.street}</p>
<p>House: ${requestScope.address.house}</p>
<p>Flat: ${requestScope.address.flat}</p>
<br>
<p>Subscription term: ${requestScope.term.months} months</p>
<p>Price: ${requestScope.price.value}</p>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=add_to_shopping_card" method="post">
    <input type="hidden" name="userId" value="${requestScope.user.id}"/>
    <input type="hidden" name="addressId" value="${requestScope.address.id}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="termId" value="${requestScope.term.id}"/>
    <input type="hidden" name="priceId" value="${requestScope.price.id}"/>
    <input type="hidden" name="statusId" value="1"/>
    <input type="submit" value="Add to shopping card"/>
</form>
</body>
</html>
