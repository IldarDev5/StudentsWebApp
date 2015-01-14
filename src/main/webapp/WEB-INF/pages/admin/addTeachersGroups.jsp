<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1>Add teacher's taught group and subject</h1>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    $(function() {
        setLoad(true, true, true);

        loadCities();

        $('#citySelect').change(function() {
            var city = $(this).val();
            loadUnis("?cityId=" + city);
        });

        $('#uniSelect').change(function() {
            var uni = $(this).val();
            loadFacs("?uniId=" + uni);
        });

        $('#facSelect').change(function() {
            var fac = $(this).val();
            loadGroups("?facId=" + fac);
        });
    });
</script>

<%--@elvariable id="subjects" type="java.util.List<java.lang.String>"--%>
<form:form method="post" action="/admin/teachers/groups" commandName="tgroup">
    <table>
        <tr>
            <td><form:label path="subjectName">Subject name:</form:label></td>
            <c:if test="${tgroup.subjectName != null}">
                <td><c:out value="${tgroup.subjectName}" /></td>
            </c:if>
            <c:if test="${tgroup.subjectName == null}">
                <td><form:select path="subjectName" items="${subjects}" /></td>
            </c:if>
        </tr>
        <tr>
            <td><form:label path="teacher">Teacher:</form:label></td>
            <td><form:input path="teacher" /></td>
        </tr>
        <tr>
            <td><form:label path="semester">Semester:</form:label></td>
            <td><form:input path="semester" /></td>
        </tr>
        <tr>
            <td><form:label path="citySelect">City:</form:label></td>
            <td><form:select path="citySelect" /></td>
        </tr>
        <tr>
            <td><form:label path="uniSelect">University:</form:label></td>
            <td><form:select path="uniSelect" /></td>
        </tr>
        <tr>
            <td><form:label path="facSelect">Faculty:</form:label></td>
            <td><form:select path="facSelect" /></td>
        </tr>
        <tr>
            <td><form:label path="groupSelect">Group:</form:label></td>
            <td><form:select path="groupSelect" /></td>
        </tr>
    </table>
    <input type="submit" value="Submit">
</form:form>
