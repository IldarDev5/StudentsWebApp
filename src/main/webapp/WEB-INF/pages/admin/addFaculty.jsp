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
<form method="post" action="/admin/faculties/add">
    <table>
        <tr>
            <td><spring:message code="fac.name" /></td>
            <td><input type="text" id="facultyName" name="facultyName"></td>
        </tr>
        <tr>
            <td><spring:message code="fac.date" /></td>
            <td><input type="text" id="foundDate" name="foundDate"></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="hidden" name="uni" value="${university.unId}">
    <input type="submit" value="<spring:message code="fac.submit" />">
</form>