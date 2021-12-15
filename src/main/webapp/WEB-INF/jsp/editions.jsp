<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.editions" var="pageTitleEditions" />
<fmt:message bundle="${loc}" key="label.editionsMessage" var="editionsMessage" />
<fmt:message bundle="${loc}" key="label.field.edName" var="edName" />
<fmt:message bundle="${loc}" key="label.field.edCat" var="edCat" />
<fmt:message bundle="${loc}" key="label.field.months" var="months" />
<fmt:message bundle="${loc}" key="label.submit.choose" var="submitChoose" />
<fmt:message bundle="${loc}" key="label.submit.delete" var="submitDelete" />
<fmt:message bundle="${loc}" key="label.submit.update" var="submitUpdate" />
<fmt:message bundle="${loc}" key="label.submit.addEdition" var="submitAddEdition" />
<fmt:message bundle="${loc}" key="label.logInToChooseMessage" var="logInToChoose" />
<html>
<head>
    <title>${pageTitleEditions}</title>
</head>
<body>
<h3>${editionsMessage}</h3>
<table>
    <tr>
        <th>${edName}</th>
        <th>${edCat}</th>
        <th>3 ${months},$</th>
        <th>6 ${months},$</th>
        <th>12 ${months},$</th>
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
                        <input type="submit" value="${submitChoose}"/>
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
                        <input type="submit" value="${submitDelete}"/>
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
                            <input type="submit" value="${submitUpdate}"/>
                        </form>
                    </c:when>
                    <c:otherwise>
                        ${logInToChoose}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
    <form name="add_edition" action="${pageContext.request.contextPath}/controller?command=show_add_edition" method="post">
    <input type="submit" value="${submitAddEdition}"/>
</c:if>
</form>
</body>
</html>