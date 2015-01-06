<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Student teachers</h1>
<%--@elvariable id="teachers" type="java.util.Map<ru.ildar.database.entities.Person, java.lang.String>"--%>
<table>
    <tr>
        <th>Username</th>
        <th>First Name</th>
        <th>Last name</th>
        <th>Subjects</th>
    </tr>
    <c:forEach items="${teachers}" var="teacher">
        <tr>
            <td><c:out value="${teacher.key.username}" /></td>
            <td><c:out value="${teacher.key.details.firstName}" /></td>
            <td><c:out value="${teacher.key.details.lastName}" /></td>
            <td><c:out value="${teacher.value}" /></td>
            <td>
                <a href="/auth/info?username=${teacher.key.username}">More info</a>
            </td>
        </tr>
    </c:forEach>
</table>