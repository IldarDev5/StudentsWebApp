<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/admin_unis.js"></script>

<form method="post" action="/admin/unis/image?${_csrf.parameterName}=${_csrf.token}"
      id="uploadUniPicForm" enctype="multipart/form-data">
    <input type="hidden" id="uploadUnId" name="uploadUnId">
    <input type="file" style="display: none;" id="uniPic" name="uniPic">
    <input type="hidden" name="pageNumber" value="${pageNumber}">
</form>


<h1><spring:message code="uni.unis" /></h1>
<div class="button_small">
    <a href="/admin/unis/add"><spring:message code="uni.add" /></a>
</div>
<br /> <br />
<%--@elvariable id="universities" type="java.util.List<ru.ildar.database.entities.University>"--%>
<table class="prettyTable">
    <tr>
        <td><spring:message code="uni.city" /></td>
        <td><spring:message code="uni.name" /></td>
        <td><spring:message code="uni.address" /></td>
        <td><spring:message code="uni.image" /></td>
        <td><spring:message code="uni.teachersCount" /></td>
        <td><spring:message code="operations" /></td>
    </tr>
    <c:forEach items="${universities}" var="un">
        <tr>
            <td><c:out value="${un.city.cityName}" /></td>
            <td><c:out value="${un.unName}" /></td>
            <td><c:out value="${un.unAddress}" /></td>
            <td>
                <a href="javascript:uploadImageForUni(${un.unId});">
                    <img src="/unis/image?unId=${un.unId}" width="100">
                </a>
            </td>
            <td><c:out value="${un.teachersCount}" /></td>
            <td>
                <a href="/admin/faculties?un_id=${un.unId}" style="text-decoration: none;">
                    <img src="/images/user_icons/faculties.png"
                         title="<spring:message code="uni.faculties" />">
                </a>
                <a href="javascript:removeUn(${un.unId});" style="text-decoration: none;">
                    <img src="/images/user_icons/remove_uni.png"
                         title="<spring:message code="uni.remove" />">
                </a>
                <a href="/admin/unis/description?unId=${un.unId}" style="text-decoration: none;">
                    <img src="/images/user_icons/description.png"
                         title="<spring:message code="uni.description" />">
                </a>
                <a href="/admin/unis/city?unId=${un.unId}" style="text-decoration: none;">
                    <img src="/images/user_icons/city.png" height="24"
                         title="<spring:message code="uni.setCity" />">
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