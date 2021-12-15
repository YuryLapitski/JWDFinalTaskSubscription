<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.address" var="pageAddress" />
<fmt:message bundle="${loc}" key="label.message.address" var="messageAddress" />
<fmt:message bundle="${loc}" key="label.field.city" var="cityField" />
<fmt:message bundle="${loc}" key="label.field.street" var="streetField" />
<fmt:message bundle="${loc}" key="label.field.house" var="houseField" />
<fmt:message bundle="${loc}" key="label.field.flat" var="flatField" />
<fmt:message bundle="${loc}" key="label.submit.address" var="submitAddress" />
<fmt:message bundle="${loc}" key="label.errorAddressMessage" var="errorAddress" />
<html>
<head>
    <title>${pageAddress}</title>
</head>
<body>
<h3>${messageAddress} "${requestScope.edition.name}":</h3>
<form name="subscription" action="${pageContext.request.contextPath}/controller?command=show_term" method="post">
    <label for="city-input">${cityField}</label>
    <input id="city-input" type="text" name="city" value=""/>
    <br><br>
    <label for="street-input">${streetField}</label>
    <input id="street-input" type="text" name="street" value=""/>
    <br><br>
    <label for="house-input">${houseField}</label>
    <input id="house-input" type="text" name="house" value=""/>
    <br><br>
    <label for="flat-input">${flatField}</label>
    <input id="flat-input" type="number" name="flat" value=""/>
    <br><br>
    <input type="hidden" name="editionId" value="${requestScope.edition.id}"/>
    <input type="hidden" name="name" value="${requestScope.edition.name}"/>
    <input type="hidden" name="threeMonthsPrice" value="${requestScope.edition.threeMonthsPrice}"/>
    <input type="hidden" name="sixMonthsPrice" value="${requestScope.edition.sixMonthsPrice}"/>
    <input type="hidden" name="twelveMonthsPrice" value="${requestScope.edition.twelveMonthsPrice}"/>
    <br/>
    <c:if test="${not empty requestScope.errorAddressMessage}">
    <b>${errorAddress}</b>
    <br>
    </c:if>
    <input type="submit" value="${submitAddress}"/>
</form>
</body>
</html>

