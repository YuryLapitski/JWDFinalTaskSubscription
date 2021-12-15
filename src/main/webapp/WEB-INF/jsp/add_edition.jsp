<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.addEdition" var="pageAddEdition" />
<fmt:message bundle="${loc}" key="label.message.addEdition" var="messageAddEdition" />
<fmt:message bundle="${loc}" key="label.edition.name" var="editionName" />
<fmt:message bundle="${loc}" key="label.edition.category" var="editionCategory" />
<fmt:message bundle="${loc}" key="label.newspaper" var="newspaper" />
<fmt:message bundle="${loc}" key="label.magazine" var="magazine" />
<fmt:message bundle="${loc}" key="label.book" var="book" />
<fmt:message bundle="${loc}" key="label.threeMonthsPrice" var="threeMonthsPrice" />
<fmt:message bundle="${loc}" key="label.sixMonthsPrice" var="sixMonthsPrice" />
<fmt:message bundle="${loc}" key="label.twelveMonthsPrice" var="twelveMonthsPrice" />
<fmt:message bundle="${loc}" key="label.errorEditionMessage" var="errorEditionMessage" />
<fmt:message bundle="${loc}" key="label.errorEditionExistMessage" var="errorEditionExistMessage" />
<fmt:message bundle="${loc}" key="label.button.addEdition" var="buttonAddEdition" />
errorEditionMessage
<html>
<head>
    <title>${pageAddEdition}</title>
</head>
<body>
<h3>${messageAddEdition}</h3>
<form name="add edition" action="${pageContext.request.contextPath}/controller?command=add_edition" method="post">
    <label for="name-input">${editionName}</label>
    <input id="name-input" type="text" name="name" value=""/>
    <br><br>
    <label for="category-input">${editionCategory}</label>
    <label>
        <select id="category-input" name="category">
            <option></option>
            <option>${newspaper}</option>
            <option>${magazine}</option>
            <option>${book}</option>
        </select>
    </label>
    <br><br>
    <label for="threeMonthsPrice-input">${threeMonthsPrice}</label>
    <input id="threeMonthsPrice-input" type="text" name="threeMonthsPrice" value=""/>
    <br><br>
    <label for="sixMonthsPrice-input">${sixMonthsPrice}</label>
    <input id="sixMonthsPrice-input" type="text" name="sixMonthsPrice" value=""/>
    <br><br>
    <label for="twelveMonthsPrice-input">${twelveMonthsPrice}</label>
    <input id="twelveMonthsPrice-input" type="text" name="twelveMonthsPrice" value=""/>
    <br><br>
    <br/>
    <c:if test="${not empty requestScope.errorEditionMessage}">
        <b style="color: red">${errorEditionMessage}</b>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.errorEditionExistMessage}">
        <b style="color: red">${errorEditionExistMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="${buttonAddEdition}"/>
</form>
</body>
</html>