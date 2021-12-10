<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title" var="pageTitle" />
<fmt:message bundle="${loc}" key="label.subscription" var="subscriptionMessage" />
<fmt:message bundle="${loc}" key="label.welcome" var="welcomeMessage" />
<fmt:message bundle="${loc}" key="label.link.editions" var="editionsLink" />
<fmt:message bundle="${loc}" key="label.link.users" var="usersLink" />
<fmt:message bundle="${loc}" key="label.link.accounts" var="accountsLink" />
<fmt:message bundle="${loc}" key="label.link.login" var="loginLink" />
<fmt:message bundle="${loc}" key="label.link.logout" var="logoutLink" />
<fmt:message bundle="${loc}" key="label.link.signup" var="signupLink" />
<fmt:message bundle="${loc}" key="label.link.user_data" var="user_dataLink" />
<fmt:message bundle="${loc}" key="label.link.shopping_card" var="shopping_cardLink" />
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
        <th>Name</th>
        <th>Category</th>
        <th>3 months</th>
        <th>6 months</th>
        <th>12 months</th>
<%--        <th></th>--%>
    </tr>
    <c:forEach var="edition" items="${requestScope.editions}">
        <tr>
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
                    <form name="delete_edition" action="${pageContext.request.contextPath}
                    /controller?command=delete_edition" method="post">
                        <input type="hidden" name="editionId" value="${edition.id}"/>
                        <input type="hidden" name="name" value="${edition.name}"/>
                        <c:if test="${not empty requestScope.errorFindUserMessage}">
                            <b>${requestScope.errorFindUserMessage}</b>
                            <br>
                        </c:if>
                        <input type="submit" value="Delete"/>
                    </form>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.USER}">
                    </c:when>
                    <c:when test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
                        <form name="update_edition" action="${pageContext.request.contextPath}
                        /controller?command=show_update_edition" method="post">
                            <input type="hidden" name="editionId" value="${edition.id}"/>
                            <input type="hidden" name="name" value="${edition.name}"/>
                            <c:if test="${not empty requestScope.errorFindUserMessage}">
                                <b>${requestScope.errorFindUserMessage}</b>
                                <br>
                            </c:if>
                            <input type="submit" value="Update"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        Log in to choose
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
    <form name="add_edition" action="${pageContext.request.contextPath}/controller?command=show_add_edition" method="post">
    <input type="submit" value="Add edition"/>
</c:if>
</form>
</body>
</html>