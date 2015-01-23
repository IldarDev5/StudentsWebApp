<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeSubject(subjectName) {
        var choice = confirm('Removal of subject will incur removals of grades and data ' +
                'that depends on this subject. Do you really want to delete it?');
        if(choice == false)
            return;

        $.ajax({
            type: 'post',
            url: '/admin/subjects/remove?subjectName=' + subjectName,
            beforeSend: function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
            },
            success: function(data) {
                $('#' + subjectName + 'Tr').remove();
            }
        });
    }
</script>

<h1><spring:message code="sub.subjects" /></h1>
<%--@elvariable id="subjects" type="java.util.List<ru.ildar.database.entities.Subject>"--%>
<table border="1">
    <tr>
        <th><spring:message code="sub.name" /></th>
        <th><spring:message code="sub.type" /></th>
    </tr>
    <c:forEach items="${subjects}" var="subject">
        <tr id="${subject.subjectName}Tr">
            <td>${subject.subjectName}</td>
            <td>${subject.subjectType}</td>
            <td>
                <a href="/admin/teachers/bySubject?subject=${subject.subjectName}">
                    <spring:message code="sub.teachers" />
                </a>
            </td>
            <td>
                <a href="javascript:removeSubject('${subject.subjectName}');">
                    <spring:message code="sub.remove" />
                </a>
            </td>
        </tr>
    </c:forEach>
    <a href="/admin/subjects/add">
        <spring:message code="sub.add" />
    </a>
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