<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.accounts" var="pageAccounts" />
<fmt:message bundle="${loc}" key="label.accounts" var="accounts" />
<fmt:message bundle="${loc}" key="label.field.id" var="id" />
<fmt:message bundle="${loc}" key="label.field.login" var="login" />
<fmt:message bundle="${loc}" key="label.field.password" var="password" />
<fmt:message bundle="${loc}" key="label.field.role" var="role" />
<fmt:message bundle="${loc}" key="label.button.accDelete" var="delete" />
<html>
<head>
    <title>${pageAccounts}</title>
</head>
<body>
<h3>${accounts}</h3>
<table>
    <tr>
        <th>${id}</th>
        <th>${login}</th>
        <th>${password}</th>
        <th>${role}</th>
    </tr>
    <c:forEach var="account" items="${requestScope.accounts}">
        <tr>
            <td>${account.id}</td>
            <td>${account.login}</td>
            <td>${account.password}</td>
            <td>${account.role}</td>
            <td>
                <c:if test="${account.role eq Role.USER}">
                    <form name="delete_account" action="${pageContext.request.contextPath}
                    /controller?command=delete_account" method="post">
                        <input type="hidden" name="accId" value="${account.id}"/>
                        <c:if test="${not empty requestScope.errorFindAccountMessage}">
                            <b>${requestScope.errorFindAccountMessage}</b>
                            <br>
                        </c:if>
                        <input type="submit" value="${delete}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
