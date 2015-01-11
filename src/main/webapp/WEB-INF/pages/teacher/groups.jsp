<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Groups you teach</h1>

<%--@elvariable id="groups" type="java.util.List<ru.ildar.database.entities.TeachersGroups>"--%>
<table border="1">
    <tr>
        <th>Group ID</th>
        <th>Semester</th>
        <th>Subject name</th>
    </tr>
    <c:forEach items="${groups}" var="tGroup">
        <c:set var="groupId" value="${tGroup.group.groupId}" />
        <tr>
            <td><a href="/auth/studentGroup?groupId=${groupId}">${groupId}</a></td>
            <td>${tGroup.semester}</td>
            <td>${tGroup.subjectName}</td>
            <td><a href="/teacher/grades?id=${tGroup.id}">Grades</a></td>
        </tr>
    </c:forEach>
</table>