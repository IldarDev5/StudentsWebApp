<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="sub.localizedSubjects" /></h1>

<script type="text/javascript" src="/scripts/localizedSubjects.js">
</script>

<form:form method="post" action="/admin/subjects/localized" commandName="subject">
    <table>
        <tr>
            <td><spring:message code="sub.name" />:</td>
            <td><%--@elvariable id="subjects" type="java.util.List<ru.ildar.database.entities.Subject>"--%>
            <form:select path="subjectName" items="${subjects}"
                             itemLabel="subjectName" itemValue="subjectName" /></td>
        </tr>
        <tr>
            <td><spring:message code="sub.language" />:</td>
            <td><%--@elvariable id="languages" type="java.util.List<ru.ildar.database.entities.Language>"--%>
            <form:select path="languageAbbrev" items="${languages}"
                         itemValue="abbreviation" itemLabel="language" /></td>
        </tr>
        <tr>
            <td><spring:message code="sub.translation" />:</td>
            <td><form:input path="subjectTranslation" /></td>
        </tr>
    </table>
    <form:hidden path="id" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="sub.submit" />">
</form:form>