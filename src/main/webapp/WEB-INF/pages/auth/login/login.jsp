<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    $(function() {
        $('#loginForm').submit(function() {
            var username = $('#username').val();
            var password = $('#password').val();

            if(username.trim() == '' || password.trim() == '') {
                $('#errSpan').html('<spring:message code="auth.fieldsEmpty" />');
                return false;
            }

            return true;
        });
    });
</script>

<h1><spring:message code="auth.login" /></h1>

<span style="color:red;" id="errSpan">
    <c:if test="${param.auth eq 'fail'}">
        <spring:message code="auth.wrongLogin" />
    </c:if>
</span>

<form method="post" action="/login" id="loginForm">
    <table>
        <tr>
            <td><spring:message code="auth.enterUsername" /></td>
            <td><input type="text" name="username" id="username"></td>
        </tr>
        <tr>
            <td><spring:message code="auth.enterPassword" /></td>
            <td><input type="password" name="password" id="password"></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="auth.login" />">
</form>