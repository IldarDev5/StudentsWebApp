<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1><spring:message code="auth.login" /></h1>

<c:if test="${param.auth eq 'fail'}">
    <span style="color: red;"><spring:message code="auth.wrongLogin" /></span>
</c:if>

<form method="post" action="/login">
    <table>
        <tr>
            <td><spring:message code="auth.enterUsername" /></td>
            <td><input type="text" name="username" id="username"></td>
        </tr>
        <tr>
            <td><spring:message code="auth.enterPassword" /></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="auth.login" />">
</form>