<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<h1>User Details</h1>
<form:form method="post" action="personDetails" commandName="user">
    <table>
        <tr>
            <td><form:label path="firstName">Your first name:</form:label></td>
            <td><form:input path="firstName" /></td>
        </tr>
        <tr>
            <td><form:label path="lastName">Your last name:</form:label></td>
            <td><form:input path="lastName" /></td>
        </tr>
        <tr>
            <td><form:label path="email">Your E-Mail:</form:label></td>
            <td><form:input path="email" /></td>
        </tr>
        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <tr>
                <td><form:label path="title">Your title:</form:label></td>
                <td><form:input path="title" /></td>
            </tr>
        </sec:authorize>
        <tr>
            <td><form:label path="enrollment">Your enrollment/work start date:</form:label></td>
            <td><form:input path="enrollment" /></td>
        </tr>
    </table>
    <input type="submit" value="Save">
</form:form>