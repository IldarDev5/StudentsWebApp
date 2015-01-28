<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="uni.addUni" /></h1>

<%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.City>"--%>
<form:form method="post" action="/admin/unis/add" commandName="uni">
    <table>
        <tr>
            <td><spring:message code="uni.city" />:</td>
            <td><form:select path="citySelect" items="${cities}" itemValue="id" /></td>
            <td><form:errors path="citySelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="uni.name" />:</td>
            <td><form:input path="unName" /></td>
            <td><form:errors path="unName" /> </td>
        </tr>
        <tr>
            <td><spring:message code="uni.address" />:</td>
            <td><form:input path="unAddress" /></td>
            <td><form:errors path="unAddress" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="uni.submit" />">
</form:form>