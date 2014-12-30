<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>Log In</h1>
<form:form method="post" action="/login" commandName="user">
    <table>
        <tr>
            <td><form:label path="username">Enter the username:</form:label></td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td><form:label path="password">Enter the password:</form:label></td>
            <td><form:input path="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Log In">
</form:form>