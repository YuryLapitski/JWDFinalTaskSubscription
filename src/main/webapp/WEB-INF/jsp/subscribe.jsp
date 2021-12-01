<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<p>City: ${requestScope.address.city}</p>
<p>Street: ${requestScope.address.street}</p>
<p>House: ${requestScope.address.house}</p>
<p>Flat: ${requestScope.address.flat}</p>
<br>
<p>Subscription term: ${requestScope.term.months} months</p>
<p>Price: ${requestScope.price.value}</p>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=subscribe" method="post">
    <input type="submit" value="Add to shopping card"/>
</form>
</body>
</html>
