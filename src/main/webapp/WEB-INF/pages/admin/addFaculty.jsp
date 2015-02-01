<%--@elvariable id="university" type="ru.ildar.database.entities.University"--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<link rel="stylesheet" href="/css/jquery-ui.min.css">
<script type="text/javascript" src="/scripts/jquery/jquery-ui.min.js"></script>
<script type="text/javascript">
//    $(function() {
//        var foundDate = $('#foundDate');
//        foundDate.datepicker({ dateFormat : 'dd/mm/yy' });
//        if(foundDate.val() == '')
//            foundDate.val('01/01/2000');
//    });
</script>

<h1>
    <spring:message code="fac.addFac">
        <spring:argument value="${university.unName}" />
        <spring:argument value="${university.city.cityName}" />
    </spring:message>
</h1>
    <%--@elvariable id="facultyExists" type="java.lang.Boolean"--%>
    <c:if test="${facultyExists != null && facultyExists == true}">
        <h3 style="color: red;">
            <spring:message code="fac.facExists" />
        </h3>
    </c:if>
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