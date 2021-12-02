<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Term</title>
</head>
<body>
<h3>Please select a subscription period to "${requestScope.edition.name}":</h3>
<h3>Address id: "${requestScope.address.id}":</h3>
<form name="subscription"
      action="${pageContext.request.contextPath}/controller?command=show_subscription" method="post">
    <label>Term:</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.threeMonthsPrice}"/>3 months</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.sixMonthsPrice}"/>6 months</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.twelveMonthsPrice}"/>12 months</label>
    <br><br>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="name" value="${requestScope.edition.name}"/>
    <input type="hidden" name="addressId" value="${requestScope.address.id}"/>
    <input type="hidden" name="city" value="${requestScope.address.city}"/>
    <input type="hidden" name="street" value="${requestScope.address.street}"/>
    <input type="hidden" name="house" value="${requestScope.address.house}"/>
    <input type="hidden" name="flat" value="${requestScope.address.flat}"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
