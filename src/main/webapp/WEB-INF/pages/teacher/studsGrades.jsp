<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--@elvariable id="grades" type="java.util.List<ru.ildar.database.entities.Grade>"--%>
<%--@elvariable id="tGroup" type="ru.ildar.database.entities.TeachersGroups"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeGrade(gradeId) {
        $('#gradeId').val(gradeId);
        $('#tGroupsId').val(${tGroup.id});
        $('#removeGradeForm').submit();
    }
</script>

<h2>
    <spring:message code="teach.studGrades">
        <spring:argument value="${tGroup.teacher.firstName}" />
        <spring:argument value="${tGroup.teacher.lastName}" />
        <spring:argument value="${tGroup.teacher.username}" />
        <spring:argument value="${tGroup.subjectName}" />
        <spring:argument value="${tGroup.semester}" />
    </spring:message>
</h2>

<table>
    <tr>
        <th><spring:message code="teach.student" /></th>
        <th><spring:message code="teach.semester" /></th>
        <th><spring:message code="teach.subjectName" /></th>
        <th><spring:message code="teach.grade" /></th>
    </tr>
    <c:forEach items="${grades}" var="grade">
        <tr>
            <td>${grade.student.username}</td>
            <td>${grade.semester}</td>
            <td>${grade.subjectName}</td>
            <td>${grade.gradeValue}</td>
            <td><a><spring:message code="teach.updateGrade" /></a></td>
            <td>
                <a href="javascript:removeGrade(${grade.gradeId});">
                    <spring:message code="teach.remove" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<form hidden="hidden" id="removeGradeForm" method="post" action="/teacher/grades/remove">
    <input type="hidden" name="gradeId" id="gradeId">
    <input type="hidden" name="tGroupsId" id="tGroupsId">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<a href="/teacher/grades/add?subject=${tGroup.subjectName}
&groupId=${tGroup.group.groupId}&semester=${tGroup.semester}">
    <spring:message code="teach.addGradeToStud" />
</a>