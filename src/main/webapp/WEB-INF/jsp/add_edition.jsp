<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>Add edition</title>
</head>
<body>
<h3>Please enter new data to new edition:</h3>
<form name="add edition" action="${pageContext.request.contextPath}/controller?command=add_edition" method="post">
    <label for="name-input">Name:</label>
    <input id="name-input" type="text" name="name" value=""/>
    <br><br>
    <label for="category-input">Category:</label>
    <label>
        <select id="category-input" name="category">
            <option></option>
            <option>Newspaper</option>
            <option>Magazine</option>
            <option>Book</option>
        </select>
    </label>
    <br><br>
    <label for="threeMonthsPrice-input">Three months price:</label>
    <input id="threeMonthsPrice-input" type="text" name="threeMonthsPrice" value=""/>
    <br><br>
    <label for="sixMonthsPrice-input">Six months price:</label>
    <input id="sixMonthsPrice-input" type="text" name="sixMonthsPrice" value=""/>
    <br><br>
    <label for="twelveMonthsPrice-input">Twelve months price:</label>
    <input id="twelveMonthsPrice-input" type="text" name="twelveMonthsPrice" value=""/>
    <br><br>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>--%>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.name}"/>--%>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.category}"/>--%>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.threeMonthsPrice}"/>--%>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.sixMonthsPrice}"/>--%>
<%--    <input type="hidden" name="editionId" value="${requestScope.edition.twelveMonthsPrice}"/>--%>
    <br/>
    <c:if test="${not empty requestScope.errorEditionMessage}">
        <b style="color: red">${requestScope.errorEditionMessage}</b>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorEditionExistMessage}">
        <b style="color: red">${requestScope.errorEditionExistMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="Add edition"/>
</form>
</body>
</html>
