<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<h1>Faculties of <c:out value="${uni.unName}" /></h1>
<table>
    <tr>
        <th>Faculty name</th>
        <th>Foundation date</th>
        <th>Students count</th>
        <th>Teachers count</th>
    </tr>
    <c:forEach items="${uni.faculties}" var="faculty">
        <tr id="faculty${faculty.facultyId}">
            <td>${faculty.facultyName}</td>
            <td>${faculty.foundDateAsString()}</td>
            <td>${faculty.studentsCount}</td>
            <td>${faculty.teachersCount}</td>
            <td><a href="javascript:removeFaculty(${faculty.facultyId});">Remove</a></td>
        </tr>
    </c:forEach>
</table>
<a href="/admin/faculties/add?unId=${uni.unId}">Add new faculty</a>