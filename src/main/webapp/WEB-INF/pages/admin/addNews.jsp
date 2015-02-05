<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="news.addNews" /></h1>

<form:form method="post" action="/admin/news/add" commandName="newsObj">
    <table>
        <tr>
            <td><spring:message code="news.briefDescription" />:</td>
            <td><form:input path="briefDescription" /></td>
        </tr>
        <tr>
            <td><spring:message code="news.fullDescription" />:</td>
            <td><form:textarea path="fullDescription" rows="25" cols="30" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="news.submit" />">
</form:form>