<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="stud.register" /></h1>
<span style="color: red">
    <c:if test="${passNotEqual != null}">
        <spring:message code="stud.passNotEqual" />
    </c:if>
    <c:if test="${hasUsername != null}">
        <spring:message code="stud.usernameExists" />
    </c:if>
</span>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript" src="/scripts/register_stud.js"></script>

<form:form method="post" action="/register/student" commandName="student">
    <table>
        <tr>
            <td>
                <form:label path="username">
                    <spring:message code="stud.reg.enterUsername" />
                </form:label>
            </td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message code="stud.reg.enterPassword" />
                </form:label>
            </td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="repeatPassword">
                    <spring:message code="stud.reg.repeatPassword"/>
                </form:label>
            </td>
            <td><form:password path="repeatPassword" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="citySelect">
                    <spring:message code="stud.reg.chooseCity" />
                </form:label>
            </td>
            <td><form:select path="citySelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="uniSelect">
                    <spring:message code="stud.reg.chooseUni" />
                </form:label>
            </td>
            <td><form:select path="uniSelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="facSelect">
                    <spring:message code="stud.reg.chooseFac" />
                </form:label>
            </td>
            <td><form:select path="facSelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="groupSelect">
                    <spring:message code="stud.reg.chooseGroup" />
                </form:label>
            </td>
            <td><form:select path="groupSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="role" value="${student.role}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="stud.reg.register" />">
</form:form>