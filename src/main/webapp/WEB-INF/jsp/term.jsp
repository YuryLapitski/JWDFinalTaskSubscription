<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.term" var="titleTerm" />
<fmt:message bundle="${loc}" key="label.termMessage" var="termMessage" />
<fmt:message bundle="${loc}" key="label.submit.term" var="submitTerm" />
<fmt:message bundle="${loc}" key="label.term.threeMonths" var="threeMonths" />
<fmt:message bundle="${loc}" key="label.term.sixMonths" var="sixMonths" />
<fmt:message bundle="${loc}" key="label.term.twelveMonths" var="twelveMonths" />
<fmt:message bundle="${loc}" key="label.term.errorFindUser" var="errorFindUser" />

<html>
<head>
    <title>${titleTerm}</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/term.css"%>
    <%@include file="/WEB-INF/css/text.css"%>
</style>
<h2>${termMessage} "${requestScope.edition.name}":</h2>
<ul id="term">
<form name="subscription"
      action="${pageContext.request.contextPath}/controller?command=show_subscription" method="post">
    <label>Term:</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.threeMonthsPrice}" checked/>
        ${threeMonths}</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.sixMonthsPrice}"/>
        ${sixMonths}</label>
    <label><input type="radio" name="monthsTerm" value="${requestScope.edition.twelveMonthsPrice}"/>
        ${twelveMonths}</label>
    <br><br>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="name" value="${requestScope.edition.name}"/>
    <input type="hidden" name="addressId" value="${requestScope.address.id}"/>
    <input type="hidden" name="city" value="${requestScope.address.city}"/>
    <input type="hidden" name="street" value="${requestScope.address.street}"/>
    <input type="hidden" name="house" value="${requestScope.address.house}"/>
    <input type="hidden" name="flat" value="${requestScope.address.flat}"/>
    <c:if test="${not empty requestScope.errorFindUserMessage}">
        <b style="color: red">${errorFindUser}</b>
        <br>
    </c:if>
    <button type="submit">${submitTerm}</button>
</form>
</ul>
</body>
</html>