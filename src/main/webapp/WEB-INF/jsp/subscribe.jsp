<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Subscribe</title>
</head>
<body>
<h3>Please enter data to subscribe to "${requestScope.editionName}":</h3>
<p>First Name</p>
<p>Last Name</p>
<p>Email</p>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=subscribe" method="post">
    <label for="city-input">City:</label>
    <input id="city-input" type="text" name="city" value=""/>
    <br>
    <label for="street-input">Street:</label>
    <input id="street-input" type="text" name="street" value=""/>
    <br>
    <label for="house-input">House:</label>
    <input id="house-input" type="number" name="house" value=""/>
    <br>
    <label for="flat-input">Flat:</label>
    <input id="flat-input" type="number" name="flat" value=""/>
    <br>
    <label for="term-input">Term:</label>
    <input id="term-input" type="radio" name="term" value=""/>3 months
    <input id="term-input" type="radio" name="term" value=""/>6 months
    <input id="term-input" type="radio" name="term" value=""/>12 months
    <br/>
    <c:if test="${not empty requestScope.errorUserDataMessage}">
        <b>${requestScope.errorUserDataMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
