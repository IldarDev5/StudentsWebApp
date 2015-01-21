<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeUn(unId) {
        $("#unIdHidden").val(unId);
        $('#removeUnForm').submit();
    }

    var universityId;
    function uploadImageForUni(unId) {
        universityId = unId;
        $('#uniPic').click();
    }

    $(function() {
        $('#uniPic').change(function() {
            $('#uploadUnId').val(universityId);
            $('#uploadUniPicForm').submit();
        });
    });
</script>

<form method="post" action="/admin/unis/image?${_csrf.parameterName}=${_csrf.token}"
      id="uploadUniPicForm" enctype="multipart/form-data">
    <input type="hidden" id="uploadUnId" name="uploadUnId">
    <input type="file" style="display: none;" id="uniPic" name="uniPic">
    <input type="hidden" name="pageNumber" value="${pageNumber}">
    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">--%>
</form>


<h1>Universities</h1>
<%--@elvariable id="universities" type="java.util.List<ru.ildar.database.entities.University>"--%>
<table border="1">
    <tr>
        <th><spring:message code="uni.city" /></th>
        <th><spring:message code="uni.name" /></th>
        <th><spring:message code="uni.address" /></th>
        <th><spring:message code="uni.image" /></th>
        <th><spring:message code="uni.teachersCount" /></th>
    </tr>
    <c:forEach items="${universities}" var="un">
        <tr>
            <td><c:out value="${un.city.cityName}" /></td>
            <td><c:out value="${un.unName}" /></td>
            <td><c:out value="${un.unAddress}" /></td>
            <td>
                <a href="javascript:uploadImageForUni(${un.unId});">
                    <img src="/admin/unis/image?unId=${un.unId}" width="100">
                </a>
            </td>
            <td><c:out value="${un.teachersCount}" /></td>
            <td>
                <a href="/admin/faculties?un_id=${un.unId}">
                    <spring:message code="uni.faculties" />
                </a>
            </td>
            <td>
                <a href="javascript:removeUn(${un.unId});">
                    <spring:message code="uni.remove" />
                </a>
            </td>
            <td>
                <a href="/admin/unis/description?unId=${un.unId}">
                    <spring:message code="uni.description" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<form hidden="hidden" id="removeUnForm" method="post" action="/admin/unis/remove">
    <input type="hidden" id="unIdHidden" name="unId">
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

<br />
<a href="/admin/unis/add">Add new university</a>