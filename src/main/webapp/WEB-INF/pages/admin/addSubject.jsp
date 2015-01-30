<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/add_subject.js"></script>
<script type="text/javascript">
    var i18n = [];
    i18n["subjectExists"] = "<spring:message code="subj.subjectExists" />";
    i18n["correctErrors"] = "<spring:message code="subj.correctErrors" />";
</script>

<h1><spring:message code="sub.addSubject" /></h1>
<%--@elvariable id="subjectTypes" type="java.util.List<java.lang.String>"--%>
<form:form id="addForm" method="post" action="/admin/subjects/add" commandName="subject">
    <table>
        <tr>
            <td><spring:message code="sub.name" />:</td>
            <td><form:input path="subjectName" /></td>
            <td><form:errors path="subjectName" /></td>
        </tr>
        <tr>
            <td><spring:message code="sub.type" />:</td>
            <td><form:select path="subjectType" items="${subjectTypes}" /></td>
            <td><form:errors path="subjectType" /></td>
        </tr>
    </table>
    <span style="color: red;" id="inputErr"></span> <br />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="sub.submit" />">
</form:form>