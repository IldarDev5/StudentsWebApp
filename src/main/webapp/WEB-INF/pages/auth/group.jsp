<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="group" type="ru.ildar.database.entities.Group"--%>
<h1>Group <c:out value="${group.groupId}" /></h1>

<table border="1">
    <tr>
        <th>Username</th>
        <th>Name</th>
    </tr>
    <c:forEach items="${group.students}" var="student">
        <tr>
            <td>${student.username}</td>
            <td>${student.firstName} ${student.lastName}</td>
            <td><a href="/info/student?username=${student.username}">More information</a></td>
        </tr>
    </c:forEach>
</table>