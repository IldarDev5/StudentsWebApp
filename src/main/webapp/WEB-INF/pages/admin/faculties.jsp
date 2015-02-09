<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    var token;
    function setToken(t) { token = t; }

    function removeFaculty(facultyId) {
        $.ajax({
            url : '/admin/faculties/remove?facultyId=' + facultyId,
            type : 'post',
            contentType : "application/json",
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', token);
            },
            success : function(data) {
                $('#faculty' + facultyId).remove();
            }
        });
    }
</script>
<script type="text/javascript">
    $(function() {
        setToken('${_csrf.token}');
    });
</script>

<%--@elvariable id="uni" type="ru.ildar.database.entities.University"--%>
<h1><spring:message code="fac.facs" arguments="${uni.unName}" /></h1>
<div class="button_small">
    <a href="/admin/faculties/add?unId=${uni.unId}">
        <spring:message code="fac.add" />
    </a>
</div>
<br /> <br />
<table class="prettyTable">
    <tr>
        <td><spring:message code="fac.name" /></td>
        <td><spring:message code="fac.date" /></td>
        <td><spring:message code="fac.studsCount" /></td>
        <td><spring:message code="fac.remove" /></td>
    </tr>
    <c:forEach items="${uni.faculties}" var="faculty">
        <tr id="faculty${faculty.facultyId}">
            <td>${faculty.facultyName}</td>
            <td>${faculty.foundDateAsString()}</td>
            <td>${faculty.studentsCount}</td>
            <td>
                <a href="javascript:removeFaculty(${faculty.facultyId});">
                    <img src="/images/user_icons/remove.png"
                         title="<spring:message code="fac.remove" />">
                 </a>
            </td>
        </tr>
    </c:forEach>
</table>