<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript" src="/scripts/teachers_groups.js"></script>

<h1><spring:message code="teacher.groupsAndSubjects" /></h1>

<a href="/admin/teachers/groups/add">
    <spring:message code="teacher.addTGroup" />
</a>

<%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.City>"--%>
<form:form method="post" action="/admin/teachers/groups" commandName="taughtGroup">
    <table>
        <tr>
            <td><spring:message code="teacher.city" /></td>
            <td><form:select path="citySelect" items="${cities}" itemValue="id" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.uni" /></td>
            <td><form:select path="uniSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.teacher" /></td>
            <td><form:select path="teacherSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teacher.find" />">
</form:form>

<%--@elvariable id="tGroups" type="java.util.List<ru.ildar.database.entities.TeachersGroups>"--%>
<c:if test="${tGroups != null}">
    <table border="1" id="tGroupsTable">
        <tr>
            <th><spring:message code="teacher.subjectName" /></th>
            <th><spring:message code="teacher.semester" /></th>
            <th><spring:message code="teacher.groupId" /></th>
            <th><spring:message code="teacher.teacher" /></th>
        </tr>
        <c:forEach items="${tGroups}" var="tGroup">
            <tr>
                <td>${tGroup.subjectName}</td>
                <td>${tGroup.semester}</td>
                <td>${tGroup.group.groupId}</td>
                <td>${tGroup.teacher.username}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>