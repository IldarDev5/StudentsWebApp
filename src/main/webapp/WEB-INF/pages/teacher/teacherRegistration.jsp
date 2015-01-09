<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>Register</h1>
<span style="color: red">
    <c:if test="${passNotEqual != null}">
        Please enter equal passwords.
    </c:if>
    <c:if test="${hasUsername != null}">
        Such username already exists. Please choose another.
    </c:if>
</span>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    $(function() {
        function loadCities() {
            loadDataForSelect('/auth/cities', '#city', "",
                    function() {
                        var cityId = $('#city').val();
                        if(cityId)
                            loadUnis("?cityId=" + cityId);
                    });
        }

        function loadUnis(param) {
            loadDataForSelect('/auth/universities', '#uniId', param);
        }

        loadCities();

        $('#city').change(function() {
            loadUnis("?cityId=" + $(this).val());
        });
    });
</script>

<form:form method="post" action="/register/teacher" commandName="teacher">
    <table>
        <tr>
            <td><form:label path="username">Enter the username:</form:label></td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td><form:label path="password">Enter the password:</form:label></td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td><form:label path="repeatPassword">Repeat the password:</form:label></td>
            <td><form:password path="repeatPassword" /></td>
        </tr>
        <tr>
            <td><form:label path="city">Choose the city:</form:label></td>
            <td><form:select path="city" /></td>
        </tr>
        <tr>
            <td><form:label path="uniId">Choose the university:</form:label></td>
            <td><form:select path="uniId" /></td>
        </tr>
    </table>
    <input type="hidden" name="role" value="${teacher.role}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Register">
</form:form>