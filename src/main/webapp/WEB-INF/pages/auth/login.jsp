<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>Log In</h1>
<form method="post" action="/login">
    <table>
        <tr>
            <td><label for="username">Enter the username:</label></td>
            <td><input type="text" name="username" id="username"></td>
        </tr>
        <tr>
            <td><label for="password">Enter the password:</label></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Log In">
</form>