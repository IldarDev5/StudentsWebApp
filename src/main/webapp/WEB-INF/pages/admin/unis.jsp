<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeUn(unId) {
        alert(unId);
        $("#unIdHidden").val(unId);
        $('#removeUnForm').submit();
    }
</script>

<h1>Universities</h1>
<%--@elvariable id="universities" type="java.util.List<ru.ildar.database.entities.University>"--%>
<table>
    <tr>
        <th>Name</th>
        <th>Address</th>
        <th>Image</th>
    </tr>
    <c:forEach items="${universities}" var="un">
        <tr>
            <td><c:out value="${un.unName}" /></td>
            <td><c:out value="${un.unAddress}" /></td>
            <td><c:out value="Here must be an image" /></td>
            <td><a href="/admin/faculties?un_id=${un.unId}">Faculties</a></td>
            <td><a href="javascript:removeUn(${un.unId});">Remove university</a></td>
        </tr>
    </c:forEach>
</table>
<form hidden="hidden" id="removeUnForm" method="post" action="/admin/removeUn">
    <input type="hidden" id="unIdHidden">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<br />

<%--@elvariable id="pagesCount" type="java.lang.Integer"--%>
<%--@elvariable id="pageNumber" type="java.lang.Integer"--%>
<c:forEach begin="1" end="${pagesCount}" step="1" var="page">
    <c:choose>
        <c:when test="${page eq pageNumber}">
            <b>${page}</b>
        </c:when>
        <c:otherwise>
            <a href="/admin/unis/${page}">${page}</a>
        </c:otherwise>
    </c:choose>
    <c:if test="${page != pagesCount}">,</c:if>
</c:forEach>

<a href="/admin/unis/add">Add new university</a>