<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/subjects.js"></script>
<script type="text/javascript">
    $(function() {
        setToken('${_csrf.token}');
    });

    var i18n = [];
    i18n["confirmRemoval"] = "<spring:message code="sub.confirmRemoval" />";
</script>

<h1><spring:message code="sub.subjects" /></h1>
<div class="button_small">
    <a href="/admin/subjects/add">
        <spring:message code="sub.add" />
    </a>
</div>
<br /><br />
<%--@elvariable id="subjects" type="java.util.List<ru.ildar.database.entities.Subject>"--%>
<table class="prettyTable">
    <tr>
        <td><spring:message code="sub.name" /></td>
        <td><spring:message code="sub.type" /></td>
        <td><spring:message code="operations" /></td>
    </tr>
    <c:forEach items="${subjects}" var="subject">
        <tr id="${subject.subjectName.replaceAll(" ","")}Tr">
            <td>${subject.subjectName}</td>
            <td>${subject.subjectType}</td>
            <td>
                <a href="/admin/teachers/bySubject?subject=${subject.subjectName}">
                    <img src="/images/user_icons/teachers.png"
                         title="<spring:message code="sub.teachers" />">
                </a>

                <a href="javascript:removeSubject('${subject.subjectName}');">
                    <img src="/images/user_icons/remove_uni.png"
                         title="<spring:message code="sub.remove" />">
                </a>

                <a href="/admin/subjects/localized?subject=${subject.subjectName}">
                    <img src="/images/user_icons/localize.png"
                         title="<spring:message code="sub.localized" />">
                </a>
            </td>
        </tr>
    </c:forEach>
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