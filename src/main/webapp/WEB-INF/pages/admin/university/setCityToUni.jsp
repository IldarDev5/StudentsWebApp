<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="cityToUni.set" /></h1>

<form:form method="post" action="/admin/unis/city" commandName="uni">
    <table>
        <tr>
            <td><spring:message code="cityToUni.uni" />:</td>
            <td><%--@elvariable id="university" type="ru.ildar.database.entities.University"--%>
            <c:out value="${university.unName}" /></td>
        </tr>
        <tr>
            <td><spring:message code="cityToUni.address" />:</td>
            <td><form:input path="address" /></td>
            <td><form:errors path="address" /></td>
        </tr>
        <tr>
            <td><spring:message code="cityToUni.city" />:</td>
            <td><form:select path="cityId" items="${cities}" itemLabel="cityName" itemValue="id" /></td>
            <td><form:errors path="cityId" /></td>
        </tr>
    </table>
    <form:errors path="unId" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <form:hidden path="unId" />
    <input type="submit" value="<spring:message code="cityToUni.submit" />">
</form:form>