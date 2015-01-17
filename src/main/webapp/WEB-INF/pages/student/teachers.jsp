<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1><spring:message code="stud.teachers" /></h1>
<%--@elvariable id="teachers" type="java.util.Map<ru.ildar.database.entities.Teacher, java.lang.String>"--%>
<table>
    <tr>
        <th><spring:message code="stud.teachers.username" /></th>
        <th><spring:message code="stud.teachers.firstName" /></th>
        <th><spring:message code="stud.teachers.lastName" /></th>
        <th><spring:message code="stud.teachers.subjects" /></th>
    </tr>
    <c:forEach items="${teachers}" var="teacher">
        <tr>
            <td><c:out value="${teacher.key.username}" /></td>
            <td><c:out value="${teacher.key.firstName}" /></td>
            <td><c:out value="${teacher.key.lastName}" /></td>
            <td><c:out value="${teacher.value}" /></td>
            <td>
                <a href="/info/teacher?username=${teacher.key.username}">
                    <spring:message code="stud.teachers.moreInfo" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>