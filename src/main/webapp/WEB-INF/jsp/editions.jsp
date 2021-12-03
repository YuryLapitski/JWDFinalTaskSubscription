<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<html>
<head>
    <title>Editions</title>
</head>
<body>
<h3>Editions</h3>
<%--<form name="choose_edition" action="${pageContext.request.contextPath}--%>
<%--                    /controller?command=choose_edition" method="post">--%>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Category</th>
        <th>3 months</th>
        <th>6 months</th>
        <th>12 months</th>
<%--        <th></th>--%>
    </tr>
    <c:forEach var="edition" items="${requestScope.editions}">
        <tr>
            <td>${edition.id}</td>
            <td>${edition.name}</td>
            <td>${edition.category}</td>
            <td>${edition.threeMonthsPrice}</td>
            <td>${edition.sixMonthsPrice}</td>
            <td>${edition.twelveMonthsPrice}</td>
            <td>
                <c:choose>
                <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.USER}">
                    <form name="choose_edition" action="${pageContext.request.contextPath}
                    /controller?command=show_address" method="post">
                        <input type="hidden" name="editionId" value="${edition.id}"/>
                        <input type="hidden" name="name" value="${edition.name}"/>
                        <c:if test="${not empty requestScope.errorFindUserMessage}">
                            <b>${requestScope.errorFindUserMessage}</b>
                            <br>
                        </c:if>
                        <input type="submit" value="Choose"/>
                    </form>
                </c:when>
                <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
                </c:when>
                <c:otherwise>
                    Log in to choose
                </c:otherwise>
            </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>