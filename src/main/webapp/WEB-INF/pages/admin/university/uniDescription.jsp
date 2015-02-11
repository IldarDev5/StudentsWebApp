<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="uni.description.title" /></h1>

<script type="text/javascript">
    $(function() {
        $('#language').change(function() {
            var form = $('#getDescrForm');
            form.attr("action", form.attr("action") + "/" + $(this).val());
            form.submit();
        });
    });
</script>

<%--@elvariable id="description" type="ru.ildar.database.entities.UniversityDescription"--%>

<form:form method="post" acceptCharset="UTF-8" action="/admin/unis/description" commandName="description">
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
                <input type="hidden" name="university" value="${description.university.unId}">
            </td>
        </tr>
        <tr>
            <td><spring:message code="uni.descr.language" />:</td>
            <td>
                <%--@elvariable id="languages" type="java.util.List<java.lang.String>"--%>
                <form:select path="language" items="${languages}" />
            </td>
        </tr>
    </table>
    <c:if test="${description.id != 0}">
        <input type="hidden" name="id" value="${description.id}">
    </c:if>
    <form:textarea path="description" rows="25" cols="55" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="uni.descr.submit" />">
</form:form>

<form hidden="hidden" method="get" action="/admin/unis/description" id="getDescrForm">
    <input type="hidden" name="unId" value="${description.university.unId}">
</form>