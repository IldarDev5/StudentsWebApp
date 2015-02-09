<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1><spring:message code="stud.register" /></h1>
<span style="color: red">
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
            <td><form:errors path="username" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="password">
                    <spring:message code="stud.reg.enterPassword" />
                </form:label>
            </td>
            <td><form:password path="password" /></td>
            <td><form:errors path="password" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="repeatPassword">
                    <spring:message code="stud.reg.repeatPassword"/>
                </form:label>
            </td>
            <td><form:password path="repeatPassword" /></td>
            <td><form:errors path="repeatPassword" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="citySelect">
                    <spring:message code="stud.reg.chooseCity" />
                </form:label>
            </td>
            <td><%--@elvariable id="cities" type="java.util.List<ru.ildar.database.entities.LocalizedCity>"--%>
            <form:select path="citySelect" items="${cities}"
                         itemValue="city.id" itemLabel="translatedName" /></td>
            <td><form:errors path="citySelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="uniSelect">
                    <spring:message code="stud.reg.chooseUni" />
                </form:label>
            </td>
            <td><form:select path="uniSelect" /></td>
            <td><form:errors path="uniSelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="facSelect">
                    <spring:message code="stud.reg.chooseFac" />
                </form:label>
            </td>
            <td><form:select path="facSelect" /></td>
            <td><form:errors path="facSelect" /></td>
        </tr>
        <tr>
            <td>
                <form:label path="groupSelect">
                    <spring:message code="stud.reg.chooseGroup" />
                </form:label>
            </td>
            <td><form:select path="groupSelect" /></td>
            <td><form:errors path="groupSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="role" value="${student.role}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="stud.reg.register" />">
</form:form>