<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript" src="/scripts/stud_grades.js"></script>
<script type="text/javascript">
    var i18n = [];
    i18n["semester"] = "<spring:message code="studGrades.semester" />";
    i18n["grade"] = "<spring:message code="studGrades.grade" />";
    i18n["teacher"] = "<spring:message code="studGrades.teacher" />";
    i18n["subject"] = "<spring:message code="studGrades.subject" />";
</script>

<h1><spring:message code="stud.grades" /></h1>
<%--@elvariable id="semesters" type="java.util.List<java.lang.Long>"--%>
<div style="display: inline">
    <h3><spring:message code="stud.selectSemester" /></h3>
    <select id="semesterBox">
        <c:forEach items="${semesters}" var="semester">
            <option value="${semester}">${semester}</option>
        </c:forEach>
    </select>
</div>
<table id="gradesTable" class="prettyTable">
</table>