<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--@elvariable id="user" type="ru.ildar.database.entities.Person"--%>
<h1>Information about <c:out value="${user.username}" /></h1>
<table>
    <tr>
        <td>First name:</td>
        <td>${user.details.firstName}</td>
    </tr>
    <tr>
        <td>Last name:</td>
        <td>${user.details.lastName}</td>
    </tr>
    <tr>
        <td>E-Mail:</td>
        <td>${user.details.email}</td>
    </tr>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <tr>
            <td>Title:</td>
            <td>${user.details.title}</td>
        </tr>
    </sec:authorize>
    <tr>
        <td>Enrollment/work start date:</td>
        <td>${user.details.enrollmentDateAsString()}</td>
    </tr>
    <tr>
        <td>Faculty:</td>
        <td>${user.details.faculty.facultyName}</td>
    </tr>
    <tr>
        <td>University:</td>
        <td>${user.details.faculty.university.unName}</td>
    </tr>
</table>