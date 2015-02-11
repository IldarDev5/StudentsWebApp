<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="university" type="ru.ildar.controllers.pojos.UniversityInfoPojo"--%>
<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    var unCityId = null;
    var unId = null;

    <c:if test="${university.cityId != null}">
        unCityId = "${university.cityId}";
        unId = "${university.unId}";
    </c:if>

    var i18n = [];
    i18n["someDataAbsent"] = "<spring:message code="unis.someDataAbsent" />";
</script>
<script type="text/javascript" src="/scripts/unisInfo.js"></script>

<h1><spring:message code="header.unisInfo" /></h1>
<form:form method="post" action="/unis/info" commandName="university">
    <table>
        <tr>
            <td><spring:message code="uni.city" />:</td>
            <td><form:select path="cityId"
                    items="${cities}" itemLabel="translatedName" itemValue="city.id" /></td>
        </tr>
        <tr>
            <td><spring:message code="uni.name" />:</td>
            <td><form:select path="unId" id="uniSelect" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="uni.find" />">
</form:form>

<%--@elvariable id="studentsCount" type="java.lang.Integer"--%>
<c:if test="${description != null}">
    <c:choose>
        <c:when test="${description.description != null}">
            <img src="/unis/image?unId=${description.university.unId}" width="400">
            <table>
                <tr>
                    <td><spring:message code="uni.address" />:</td>
                    <td><c:out value="${description.university.unAddress}" /></td>
                </tr>
                <tr>
                    <td><spring:message code="uni.teachersCount" />:</td>
                    <td><c:out value="${description.university.teachersCount}" /></td>
                </tr>
                <tr>
                    <td><spring:message code="uni.studentsCount" />:</td>
                    <td><c:out value="${studentsCount}" /></td>
                </tr>
            </table>
            <div>
                <c:out value="${description.description}" />
            </div>
        </c:when>
        <c:otherwise>
            <i><spring:message code="uni.descriptionsNotFound" /></i>
        </c:otherwise>
    </c:choose>
</c:if>