<%--@elvariable id="university" type="ru.ildar.database.entities.University"--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--<script type="text/javascript" src="/scripts/selectBox.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--$(function() {--%>
        <%--var citySelect = $('#city');--%>
        <%--loadDataForSelect('/auth/cities', '#city', "", function() {--%>
            <%--var cityId = citySelect.find("option:first").val();--%>
            <%--citySelect.val(cityId);--%>
            <%--loadUnis();--%>
        <%--});--%>

        <%--citySelect.change(function() {--%>
            <%--loadUnis();--%>
        <%--});--%>

        <%--function loadUnis() {--%>
            <%--var cityId = citySelect.val();--%>
            <%--loadDataForSelect('/auth/universities', '#uni', '?cityId=' + cityId);--%>
        <%--}--%>
    <%--});--%>
<%--</script>--%>

<h1>Add new faculty to university ${university.unName} of ${university.city.cityName}</h1>
<form method="post" action="/admin/faculties/add">
    <table>
        <tr>
            <td><label for="facultyName">Faculty name:</label></td>
            <td><input type="text" id="facultyName" name="facultyName"></td>
        </tr>
        <tr>
            <td><label for="foundDate">Foundation date:</label></td>
            <td><input type="text" id="foundDate" name="foundDate"></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="hidden" name="uni" value="${university.unId}">
    <input type="submit" value="Submit">
</form>

<%--<h1>Add new faculty</h1>--%>
<%--<form method="post" action="/admin/faculties/add">--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td><form:label path="facultyName">Faculty name:</form:label></td>--%>
            <%--<td><form:input path="facultyName" /></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><form:label path="foundDate">Foundation date:</form:label></td>--%>
            <%--<td><form:input path="foundDate" /></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><form:label path="city">City:</form:label></td>--%>
            <%--<td><form:select path="city" /></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><form:label path="uni">University:</form:label></td>--%>
            <%--<td><form:select path="uni" /></td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form>--%>