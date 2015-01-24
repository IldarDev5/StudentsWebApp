<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="teacher.addTGroup" /></h1>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript" src="/scripts/add_teachers_groups.js"></script>

<%--@elvariable id="subjects" type="java.util.List<java.lang.String>"--%>
<form:form method="post" action="/admin/teachers/groups/add" commandName="tgroup">
    <table>
        <tr>
            <td><spring:message code="sub.name" />:</td>
            <c:choose>
                <c:when test="${tgroup.subjectName != null}">
                    <td>
                        <c:out value="${tgroup.subjectName}" />
                        <form:hidden path="subjectName" />
                    </td>
                </c:when>
                <c:otherwise>
                    <td><form:select path="subjectName" items="${subjects}" /></td>
                </c:otherwise>
            </c:choose>
        </tr>
        <tr>
            <td><spring:message code="teacher.teacher" />:</td>
            <td><form:select path="teacherSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.semester" />:</td>
            <td><form:input path="semester" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.city" />:</td>
            <td><form:select path="citySelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.uni" />:</td>
            <td><form:select path="uniSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.fac" />:</td>
            <td><form:select path="facSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.group" />:</td>
            <td><form:select path="groupSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teacher.submit" />">
</form:form>
