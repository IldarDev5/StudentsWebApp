<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript" src="/scripts/teachers_groups.js"></script>
<script type="text/javascript">
    $(function() {
        setToken('${_csrf.token}');
    });
</script>

<h1><spring:message code="teacher.groupsAndSubjects" /></h1>

<div class="button_small">
    <a href="/admin/teachers/groups/add">
        <spring:message code="teacher.addTGroup" />
    </a>
</div><br /><br />

<%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.City>"--%>
<form:form method="get" action="/admin/teachers/groups" commandName="taughtGroup">
    <table>
        <tr>
            <td><spring:message code="teacher.city" /></td>
            <td><form:select path="citySelect" items="${cities}" itemValue="id" /></td>
            <td><form:errors path="citySelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.uni" /></td>
            <td><form:select path="uniSelect" /></td>
            <td><form:errors path="uniSelect" /></td>
        </tr>
        <tr>
            <td><spring:message code="teacher.teacher" /></td>
            <td><form:select path="teacherSelect" /></td>
            <td><form:errors path="teacherSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teacher.find" />" name="get">
</form:form>

<%--@elvariable id="tGroups" type="java.util.List<ru.ildar.database.entities.TeachersGroups>"--%>
<c:if test="${tGroups != null}">
    <table class="prettyTable" id="tGroupsTable">
        <tr>
            <td><spring:message code="teacher.subjectName" /></td>
            <td><spring:message code="teacher.semester" /></td>
            <td><spring:message code="teacher.groupId" /></td>
            <td><spring:message code="teacher.teacher" /></td>
            <td><spring:message code="teacher.removeTGroup" /></td>
        </tr>
        <c:forEach items="${tGroups}" var="tGroup">
            <tr id="tGroup${tGroup.id}Tr">
                <td>${tGroup.subjectName}</td>
                <td>${tGroup.semester}</td>
                <td>${tGroup.group.groupId}</td>
                <td>${tGroup.teacher.username}</td>
                <td>
                    <a href="javascript:removeTGroup(${tGroup.id})">
                        <img src="/images/user_icons/remove.png"
                             title="<spring:message code="teacher.removeTGroup" />">
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>