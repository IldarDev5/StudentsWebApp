<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    $(function() {
        setLoad(true, false, false);
        triggerUniChange = true;

        var uniSelect = $('#uniSelect');
        var citySelect = $('#citySelect');

        uniSelect.change(function() {
            var uniId = $(this).val();
            var teacherSelect = $('#teacherSelect');
            $.getJSON('/admin/teachers/get', { uniId : uniId }, function(data) {
                teacherSelect.empty();
                $.each(data, function(index, val) {
                    teacherSelect.append("<option value=\"" + val.username + "\">"
                            + val.firstName + ", " + val.lastName + " (" + val.username + ")" + "</option>");
                });
            });
        });

        loadUnis("?cityId=" + citySelect.val());

        citySelect.change(function() {
            loadUnis("?cityId=" + $(this).val());
        });
    });
</script>

<h1><spring:message code="teacher.groupsAndSubjects" /></h1>

<%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.City>"--%>
<form:form method="post" action="/admin/teachers/groups" commandName="taughtGroup">
    <table>
        <tr>
            <td><spring:message code="teacher.city" /></td>
            <td><form:select path="citySelect" items="${cities}" itemValue="id" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.uni" /></td>
            <td><form:select path="uniSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.teacher" /></td>
            <td><form:select path="teacherSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teacher.find" />">
</form:form>

<%--@elvariable id="tGroups" type="java.util.List<ru.ildar.database.entities.TeachersGroups>"--%>
<c:if test="${tGroups != null}">
    <table border="1" id="tGroupsTable">
        <tr>
            <th>Subject name</th>
            <th>Semester</th>
            <th>Group ID</th>
            <th>Teacher</th>
        </tr>
        <c:forEach items="${tGroups}" var="tGroup">
            <tr>
                <td>${tGroup.subjectName}</td>
                <td>${tGroup.semester}</td>
                <td>${tGroup.group.groupId}</td>
                <td>${tGroup.teacher.username}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>