<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    function removeFaculty(facultyId) {
        $.ajax({
            url : '/admin/faculties/remove?facultyId=' + facultyId,
            type : 'post',
            contentType : "application/json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}');
            },
            success : function(data) {
                $('#faculty' + facultyId).remove();
            }
        });
    }
</script>

<%--@elvariable id="uni" type="ru.ildar.database.entities.University"--%>
<h1><spring:message code="fac.facs" arguments="${uni.unName}" /></h1>
<table border="1">
    <tr>
        <th><spring:message code="fac.name" /></th>
        <th><spring:message code="fac.date" /></th>
        <th><spring:message code="fac.studsCount" /></th>
    </tr>
    <c:forEach items="${uni.faculties}" var="faculty">
        <tr id="faculty${faculty.facultyId}">
            <td>${faculty.facultyName}</td>
            <td>${faculty.foundDateAsString()}</td>
            <td>${faculty.studentsCount}</td>
            <td>
                <a href="javascript:removeFaculty(${faculty.facultyId});">
                    <spring:message code="fac.remove" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/admin/faculties/add?unId=${uni.unId}">
    <spring:message code="fac.add" />
</a>