<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.updateEdition" var="titleUpdateEdition" />
<fmt:message bundle="${loc}" key="label.updateEditionMessage" var="updateEditionMessage" />
<fmt:message bundle="${loc}" key="label.field.editionName" var="editionName" />
<fmt:message bundle="${loc}" key="label.field.category" var="category" />
<fmt:message bundle="${loc}" key="label.newspaper" var="newspaper" />
<fmt:message bundle="${loc}" key="label.magazine" var="magazine" />
<fmt:message bundle="${loc}" key="label.book" var="book" />
<fmt:message bundle="${loc}" key="label.threeMonthsPrice" var="threeMonthsPrice" />
<fmt:message bundle="${loc}" key="label.sixMonthsPrice" var="sixMonthsPrice" />
<fmt:message bundle="${loc}" key="label.twelveMonthsPrice" var="twelveMonthsPrice" />
<fmt:message bundle="${loc}" key="label.error.errorEditionMessage" var="errorEdition" />
<fmt:message bundle="${loc}" key="label.submit.updateEdition" var="updateEdition" />
errorEditionMessage
<html>
<head>
    <title>${titleUpdateEdition}</title>
</head>
<body>
<h3>${updateEditionMessage} "${requestScope.edition.name}":</h3>
<form name="update edition" action="${pageContext.request.contextPath}/controller?command=update_edition" method="post">
    <label for="name-input">${editionName}</label>
    <input id="name-input" type="text" name="name" value="${requestScope.edition.name}"/>
    <br><br>
    <label for="category-input">${category}</label>
    <label>
        <select id="category-input" name="category">
            <option>${requestScope.edition.category}</option>
            <option>${newspaper}</option>
            <option>${magazine}</option>
            <option>${book}}</option>
        </select>
    </label>
<%--    <input id="category-input" type="text" name="category_name" value="${requestScope.edition.category}"/>--%>
    <br><br>
    <label for="threeMonthsPrice-input">${threeMonthsPrice}</label>
    <input id="threeMonthsPrice-input" type="text" name="threeMonthsPrice" value="${requestScope.edition.threeMonthsPrice}"/>
    <br><br>
    <label for="sixMonthsPrice-input">${sixMonthsPrice}</label>
    <input id="sixMonthsPrice-input" type="text" name="sixMonthsPrice" value="${requestScope.edition.sixMonthsPrice}"/>
    <br><br>
    <label for="twelveMonthsPrice-input">${twelveMonthsPrice}</label>
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
        <b style="color: red">${errorEdition}</b>
    <br>
    </c:if>
    <input type="submit" value="${updateEdition}"/>
</form>
</body>
</html>