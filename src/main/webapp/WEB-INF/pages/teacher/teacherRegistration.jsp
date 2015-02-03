<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<h1><spring:message code="teach.register" /></h1>
<span style="color: red">
    <c:if test="${hasUsername != null}">
        <spring:message code="teach.usernameExists" />
    </c:if>
</span>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    $(function() {
        setLoad(true, true, false);
        loadCities();

        $('#city').change(function() {
            loadUnis("?cityId=" + $(this).val());
        });
    });
</script>

<form:form method="post" action="/register/teacher" commandName="teacher">
    <table>
        <tr>
            <td>
                <form:label path="username">
                    <spring:message code="teach.reg.username" />
                </form:label>
            </td>
            <td><form:input path="username" /></td>
            <td><form:errors path="username" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message code="teach.reg.password" />
                </form:label>
            </td>
            <td><form:password path="password" /></td>
            <td><form:errors path="password" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="repeatPassword">
                    <spring:message code="teach.reg.repeatPassword" />
                </form:label>
            </td>
            <td><form:password path="repeatPassword" /></td>
            <td><form:errors path="repeatPassword" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="citySelect">
                    <spring:message code="teach.reg.city" />
                </form:label>
            </td>
            <td><form:select path="citySelect" /></td>
            <td><form:errors path="citySelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="uniSelect">
                    <spring:message code="teach.reg.university" />
                </form:label>
            </td>
            <td><form:select path="uniSelect" /></td>
            <td><form:errors path="uniSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="role" value="${teacher.role}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="teach.reg.register" />">
</form:form>