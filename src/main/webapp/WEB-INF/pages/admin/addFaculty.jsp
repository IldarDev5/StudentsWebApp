<%--@elvariable id="university" type="ru.ildar.database.entities.University"--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1>
    <spring:message code="fac.addFac">
        <spring:argument value="${university.unName}" />
        <spring:argument value="${university.city.cityName}" />
    </spring:message>
</h1>
<form:form method="post" action="/admin/faculties/add" commandName="faculty">
    <table>
        <tr>
            <td><spring:message code="fac.name" />:</td>
            <td><form:input path="facultyName" /></td>
            <td><form:errors path="facultyName" /></td>
        </tr>
        <tr>
            <td><spring:message code="fac.date" />:</td>
            <td><form:input path="foundDate" /></td>
            <td><form:errors path="foundDate" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <form:hidden path="unId" />
    <input type="submit" value="<spring:message code="fac.submit" />">
</form:form>