<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>Update Edition</title>
</head>
<body>
<h3>Please enter new data to "${requestScope.edition.name}":</h3>
<form name="update edition" action="${pageContext.request.contextPath}/controller?command=update_edition" method="post">
    <label for="name-input">Name:</label>
    <input id="name-input" type="text" name="name" value="${requestScope.edition.name}"/>
    <br><br>
    <label for="category-input">Category:</label>
    <label>
        <select id="category-input" name="category">
            <option>${requestScope.edition.category}</option>
            <option>Newspaper</option>
            <option>Magazine</option>
            <option>Book</option>
        </select>
    </label>
<%--    <input id="category-input" type="text" name="category_name" value="${requestScope.edition.category}"/>--%>
    <br><br>
    <label for="threeMonthsPrice-input">Three months price:</label>
    <input id="threeMonthsPrice-input" type="text" name="threeMonthsPrice" value="${requestScope.edition.threeMonthsPrice}"/>
    <br><br>
    <label for="sixMonthsPrice-input">Six months price:</label>
    <input id="sixMonthsPrice-input" type="text" name="sixMonthsPrice" value="${requestScope.edition.sixMonthsPrice}"/>
    <br><br>
    <label for="twelveMonthsPrice-input">Twelve months price:</label>
    <input id="twelveMonthsPrice-input" type="text" name="twelveMonthsPrice" value="${requestScope.edition.twelveMonthsPrice}"/>
    <br><br>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.name}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.category}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.threeMonthsPrice}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.sixMonthsPrice}"/>
    <input type="hidden" name="editionId" value="${requestScope.edition.twelveMonthsPrice}"/>
    <br/>
    <c:if test="${not empty requestScope.errorEditionMessage}">
        <b style="color: red">${requestScope.errorEditionMessage}</b>
    <br>
    </c:if>
    <input type="submit" value="Update"/>
</form>
</body>
</html>
