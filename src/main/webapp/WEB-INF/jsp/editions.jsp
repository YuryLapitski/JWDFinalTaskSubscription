<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Editions</h3>
<table>
    <tr>
        <th>Name</th>
        <th>Category</th>
    </tr>
    <c:forEach var="edition" items="${requestScope.editions}">
        <tr>
            <td>${edition.name}</td>
            <td>${edition.category}</td>
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
