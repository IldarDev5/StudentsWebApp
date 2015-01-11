<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="grades" type="java.util.List<ru.ildar.database.entities.Grade>"--%>
<%--@elvariable id="tGroup" type="ru.ildar.database.entities.TeachersGroups"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>Grades of students of the teacher <c:out value="${tGroup.teacher.firstName}" />
<c:out value="${tGroup.teacher.lastName}" /> (<c:out value="${tGroup.teacher.username}" />)
on subject <c:out value="${tGroup.subjectName}" /> in the semester #<c:out value="${tGroup.semester}" /></h2>

<table>
    <tr>
        <th>Student</th>
        <th>Semester</th>
        <th>Subject</th>
        <th>Grade</th>
    </tr>
    <c:forEach items="${grades}" var="grade">
        <tr>
            <td>${grade.student.username}</td>
            <td>${grade.semester}</td>
            <td>${grade.subjectName}</td>
            <td>${grade.gradeValue}</td>
            <td><a>Update grade</a></td>
            <td><a>Remove</a></td>
        </tr>
    </c:forEach>
</table>
<a href="/teacher/addGrade?subject=${tGroup.subjectName}
&groupId=${tGroup.group.groupId}&semester=${tGroup.semester}">Add a grade to the student</a>