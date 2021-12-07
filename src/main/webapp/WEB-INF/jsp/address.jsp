<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address</title>
</head>
<body>
<h3>Please enter enter delivery address to "${requestScope.edition.name}":</h3>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=show_term" method="post">
    <label for="city-input">City:</label>
    <input id="city-input" type="text" name="city" value=""/>
    <br><br>
    <label for="street-input">Street:</label>
    <input id="street-input" type="text" name="street" value=""/>
    <br><br>
    <label for="house-input">House:</label>
    <input id="house-input" type="text" name="house" value=""/>
    <br><br>
    <label for="flat-input">Flat:</label>
    <input id="flat-input" type="number" name="flat" value=""/>
    <br><br>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="name" value="${requestScope.edition.name}"/>
    <input type="hidden" name="threeMonthsPrice" value="${requestScope.edition.threeMonthsPrice}"/>
    <input type="hidden" name="sixMonthsPrice" value="${requestScope.edition.sixMonthsPrice}"/>
    <input type="hidden" name="twelveMonthsPrice" value="${requestScope.edition.twelveMonthsPrice}"/>
    <br/>
    <c:if test="${not empty requestScope.errorAddressMessage}">
    <b>${requestScope.errorAddressMessage}</b>
    <br>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
