<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="teach.groupsYouTeach" /></h1>

<%--@elvariable id="groups" type="java.util.List<ru.ildar.database.entities.TeachersGroups>"--%>
<table class="prettyTable">
    <tr>
        <td><spring:message code="teach.gr.groupId" /></td>
        <td><spring:message code="teach.gr.semester" /></td>
        <td><spring:message code="teach.gr.subjectName" /></td>
        <td><spring:message code="teach.gr.grades" /></td>
    </tr>
    <c:forEach items="${groups}" var="tGroup">
        <c:set var="groupId" value="${tGroup.group.groupId}" />
        <tr>
            <td><a href="/auth/studentGroup?groupId=${groupId}">${groupId}</a></td>
            <td>${tGroup.semester}</td>
            <td>${tGroup.subjectName}</td>
            <td>
                <a href="/teacher/grades?id=${tGroup.id}">
                    <spring:message code="teach.gr.grades" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>