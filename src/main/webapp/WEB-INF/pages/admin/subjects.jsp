<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeSubject(subjectName) {

    }
</script>

<h1>Subjects</h1>
<%--@elvariable id="subjects" type="java.util.List<ru.ildar.database.entities.Subject>"--%>
<table>
    <tr>
        <th>Subject name</th>
        <th>Subject type</th>
    </tr>
    <c:forEach items="${subjects}" var="subject">
        <tr>
            <td>${subject.subjectName}</td>
            <td>${subject.subjectType}</td>
            <td><a href="/admin/teachers?subject=${subject.subjectName}">Teachers of the subject</a></td>
            <td><a href="javascript:removeSubject('${subject.subjectName}');">Remove</a></td>
        </tr>
    </c:forEach>
    <a href="/admin/subjects/add">Add new subject</a>
</table>

<%--@elvariable id="pagesCount" type="java.lang.Integer"--%>
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