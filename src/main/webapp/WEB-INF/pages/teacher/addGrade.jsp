<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="teach.addGrade" /></h1>

<%--@elvariable id="groupId" type="java.lang.String"--%>
<%--@elvariable id="subject" type="java.lang.String"--%>
<%--@elvariable id="semester" type="java.lang.Integer"--%>
<%--@elvariable id="students" type="java.util.List<ru.ildar.database.entities.Student>"--%>

<script type="text/javascript" src="/scripts/add_grade.js"></script>
<script type="text/javascript">
    setVars('${subject}', ${semester});
</script>

<form method="post" action="/teacher/grades/add" id="addForm">
    <table>
        <tr>
            <td><spring:message code="teach.group" /></td>
            <td><span><c:out value="${groupId}" /></span></td>
        </tr>
        <tr>
            <td><spring:message code="teach.subject" /></td>
            <td><span><c:out value="${subject}" /></span></td>
        </tr>
        <tr>
            <td><spring:message code="teach.semester" />:</td>
            <td><span><c:out value="${semester}" /></span></td>
        </tr>
        <tr>
            <td>
                <label for="studentSelect">
                    <spring:message code="teach.student" />:
                </label>
            </td>
            <td>
                <select id="studentSelect" name="studentSelect">
                    <c:forEach items="${students}" var="stud">
                        <option value="${stud.username}">
                            ${stud.firstName} ${stud.lastName} (${stud.username})
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td><span id="warningSpan" style="color: red;"></span></td>
        </tr>
        <tr>
            <td><label for="gradeValue">
                <spring:message code="teach.gradeValue" />
            </label></td>
            <td><input type="text" name="gradeValue" id="gradeValue" value="0"></td>
        </tr>
    </table>
    <input type="hidden" name="groupId" value="${groupId}">
    <input type="hidden" name="subject" value="${subject}">
    <input type="hidden" name="semester" value="${semester}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teach.setGrade" />">
</form>