<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="uni.description.title" /></h1>

<%--@elvariable id="description" type="ru.ildar.database.entities.UniversityDescription"--%>

<table>
    <tr>
        <td><spring:message code="uni.descr.lastChangeDate" />:</td>
        <td><c:out value="${description.lastChangeDateAsString()}" /></td>
    </tr>
    <tr>
        <td><spring:message code="uni.descr.lastChangePerson" />:</td>
        <td><c:out value="${description.lastChangePersonFormat()}" /></td>
    </tr>
    <tr>
        <td><spring:message code="uni.descr.university" />:</td>
        <td>
            <c:out value="${description.university.unName}, ${description.university.city.cityName}" />
        </td>
    </tr>
    <tr>
        <td><spring:message code="uni.descr.language" />:</td>
        <td><c:out value="${description.language}" /></td>
    </tr>
</table>