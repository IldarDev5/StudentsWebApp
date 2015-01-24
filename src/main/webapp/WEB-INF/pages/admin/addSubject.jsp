<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/add_subject.js"></script>

<h1><spring:message code="sub.addSubject" /></h1>
<%--@elvariable id="subjectTypes" type="java.util.List<java.lang.String>"--%>
<form:form id="addForm" method="post" action="/admin/subjects/add" commandName="subject">
    <table>
        <tr>
            <td><spring:message code="sub.name" />:</td>
            <td><form:input path="subjectName" /></td>
            <td><span id="subjectNameErrSpan" style="color: red;"></span></td>
        </tr>
        <tr>
            <td><spring:message code="sub.type" />:</td>
            <td><form:select path="subjectType" items="${subjectTypes}" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="sub.submit" />">
</form:form>