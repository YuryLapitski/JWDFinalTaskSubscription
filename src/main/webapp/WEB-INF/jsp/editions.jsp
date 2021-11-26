<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<html>
<head>
    <title>Editions</title>
</head>
<body>
<h3>Editions</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Category</th>
        <th>3 months</th>
        <th>6 months</th>
        <th>12 months</th>
        <th></th>
    </tr>
    <c:forEach var="edition" items="${requestScope.editions}">
        <tr>
            <td>${edition.id}</td>
<%--            <c:choose>--%>
<%--                <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.USER}">--%>
<%--                    <td><a href="${pageContext.request.contextPath}/controller?command=show_price&id=${edition.id}">${edition.name}</a></td>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <td>${edition.name}</td>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
            <td>${edition.name}</td>
            <td>${edition.category}</td>
            <td>${edition.threeMonthsPrice}</td>
            <td>${edition.sixMonthsPrice}</td>
            <td>${edition.twelveMonthsPrice}</td>
            <c:choose>
                <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.USER}">
                    <td><input type="submit" value="Choose"/></td>
                </c:when>
                <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td>Log in to choose</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<%--<ul>--%>
<%--    <c:forEach var="edition" items="${requestScope.editions}">--%>
<%--        <li>${edition.name}</li>--%>
<%--    </c:forEach>--%>
<%--</ul>--%>
</body>
</html>