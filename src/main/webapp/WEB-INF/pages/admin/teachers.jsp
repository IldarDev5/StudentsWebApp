<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<c:choose>
    <c:when test="${subject != null}">
        <h1><spring:message code="teacher.subjTeachers" arguments="${subject}" /></h1>
        <a href="/admin/teachers/groups/add?subject=${subject}">
            <spring:message code="teacher.teachesTeachSubj" />
        </a>
    </c:when>
    <c:otherwise>
        <h1><spring:message code="teacher.teachers" /></h1>
        <a href="/admin/teachers/groups">
            <spring:message code="teacher.teachersGroups" />
        </a>
    </c:otherwise>
</c:choose>

<%--@elvariable id="teachers" type="java.util.List<ru.ildar.database.entities.Teacher>"--%>
<table class="prettyTable">
    <tr>
        <td><spring:message code="teacher.username" /></td>
        <td><spring:message code="teacher.realName" /></td>
        <td><spring:message code="teacher.email" /></td>
        <td><spring:message code="teacher.title" /></td>
        <td><spring:message code="teacher.university" /></td>
        <td><spring:message code="teacher.workStartDate" /></td>
        <td><spring:message code="teacher.photo" /></td>
    </tr>
    <c:forEach items="${teachers}" var="teacher">
        <tr>
            <td>${teacher.username}</td>
            <td>${teacher.firstName} ${teacher.lastName}</td>
            <td>${teacher.email}</td>
            <td>${teacher.title}</td>
            <td>${teacher.university.unName}</td>
            <td>${teacher.workStartDateAsString()}</td>
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
                <a href="/admin/teachers/${page}">${page}</a>
            </c:otherwise>
        </c:choose>
        <c:if test="${page != pagesCount}">,</c:if>
    </c:forEach>
</c:if>