<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1>Add new university</h1>

<%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.City>"--%>
<form:form method="post" action="/admin/unis/add" commandName="uni">
    <table>
        <tr>
            <td><form:label path="citySelect">City:</form:label></td>
            <td><form:select path="citySelect" items="${cities}" /></td>
        </tr>
        <tr>
            <td><form:label path="unName">University name:</form:label></td>
            <td><form:input path="unName" /></td>
        </tr>
        <tr>
            <td><form:label path="unAddress">Address:</form:label></td>
            <td><form:input path="unAddress" /></td>
        </tr>
    </table>
    <input type="submit" value="Submit">
</form:form>