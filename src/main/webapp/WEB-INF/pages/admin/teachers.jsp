<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${subject != null}">
        <h1>Teachers of the ${subject} subject</h1>
    </c:when>
    <c:otherwise>
        <h1>Teachers</h1>
    </c:otherwise>
</c:choose>

<%--@elvariable id="teachers" type="java.util.List<ru.ildar.database.entities.Person>"--%>
<table>
    <tr>
        <th>Username</th>
        <th>Real name</th>
        <th>E-Mail</th>
        <th>Title</th>
        <th>Faculty, University</th>
        <th>Work start date</th>
        <th>Photo</th>
    </tr>
    <c:forEach items="${teachers}" var="teacher">
        <tr>
            <td>${teacher.username}</td>
            <td>${teacher.details.firstName} ${teacher.details.lastName}</td>
            <td>${teacher.details.email}</td>
            <td>${teacher.details.title}</td>
            <td>${teacher.details.faculty.facultyName}, ${teacher.details.faculty.university.unName}</td>
            <td>${teacher.details.enrollmentDateAsString()}</td>
            <td><img src="/pictures/avatar?username=${teacher.username}" height="100"></td>
        </tr>
    </c:forEach>
</table>

<%--@elvariable id="pagesCount" type="java.lang.Integer"--%>
<c:if test="${pagesCount != null}">
    <%--@elvariable id="pageNumber" type="java.lang.Integer"--%>
    <c:forEach begin="1" end="${pagesCount}" step="1" var="page">
        <c:choose>
            <c:when test="${page eq pageNumber}">
                <b>${page}</b>
            </c:when>
            <c:otherwise>
                <a href="/admin/subjects/${page}">${page}</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${page != pagesCount}">,</c:if>
    </c:forEach>
</c:if>