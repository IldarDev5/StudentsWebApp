<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="teach.addGrade" /></h1>

<%--@elvariable id="students" type="java.util.List<ru.ildar.database.entities.Student>"--%>

<script type="text/javascript" src="/scripts/add_grade.js"></script>
<script type="text/javascript">
    setVars('${studGrade.subject}', ${studGrade.semester});

    var i18n = [];
    i18n["alreadyHasGrade"] = "<spring:message code="stud.alreadyHasGrade" />";
    i18n["gradeBetween"] = "<spring:message code="stud.gradeBetween" />";
</script>

<form:form method="post" action="/teacher/grades/add" id="addForm" commandName="studGrade">
    <table>
        <tr>
            <td><spring:message code="teach.group" />:</td>
            <td><c:out value="${studGrade.groupId}" /></td>
            <td><form:errors path="groupId" /> </td>
        </tr>
        <tr>
            <td><spring:message code="teach.subject" />:</td>
            <td><c:out value="${studGrade.subject}" /></td>
            <td><form:errors path="subject" /></td>
        </tr>
        <tr>
            <td><spring:message code="teach.semester" />:</td>
            <td><c:out value="${studGrade.semester}" /></td>
            <td><form:errors path="semester" /></td>
        </tr>
        <tr>
            <td>
                <label for="studentSelect">
                    <spring:message code="teach.student" />:
                </label>
            </td>
            <td>
                <form:select path="studentSelect">
                    <c:forEach items="${students}" var="stud">
                        <form:option value="${stud.username}">
                            <c:out value="${stud.firstName} ${stud.lastName} (${stud.username})" />
                        </form:option>
                    </c:forEach>
                </form:select>
            </td>
            <td>
                <span id="warningSpan"><form:errors path="studentSelect" /></span>
            </td>
        </tr>
        <tr>
            <td>
                <label for="gradeValue"><spring:message code="teach.gradeValue" /></label>
            </td>
            <td><form:input path="gradeValue" /></td>
            <td><form:errors path="gradeValue" /></td>
        </tr>
    </table>
    <form:hidden path="groupId" />
    <form:hidden path="subject" />
    <form:hidden path="semester" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teach.setGrade" />">
</form:form>