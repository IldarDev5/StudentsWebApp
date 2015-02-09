<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="cities.cities" /></h1>
<script type="text/javascript" src="/scripts/cities.js"></script>
<script type="text/javascript">
    $(function() {
        setToken('${_csrf.token}');
    });

    var i18n = [];
    i18n["addSuccessful"] = "<spring:message code="cities.addSuccessful" />";
    i18n["localize"] = "<spring:message code="cities.localize" />";
    i18n["cityRemovalAttention"] = "<spring:message code="cities.cityRemovalAttention" />";
    i18n["removeCity"] = "<spring:message code="cities.removeCity" />";
</script>

<table class="prettyTable" id="citiesTable">
    <tr>
        <td><spring:message code="cities.cityName" /></td>
        <td><spring:message code="cities.country" /></td>
        <td><spring:message code="cities.operations" /></td>
    </tr>
    <c:forEach items="${cities}" var="city">
        <tr>
            <td><c:out value="${city.cityName}" /></td>
            <td><c:out value="${city.country}" /></td>
            <td>
                <a href="/admin/cities/localize?cityId=${city.id}">
                    <img src="/images/user_icons/localize_cities.png"
                         title="<spring:message code="cities.operations" />">
                </a>
                <a href="javascript:removeCity(${city.id})">
                    <img src="/images/user_icons/remove.png"
                        title="<spring:message code="cities.removeCity" />">
                </a>
            </td>
        </tr>
    </c:forEach>
</table> <br />

<!--Remove city form-->
<form hidden="hidden" id="removeCityForm" method="post" action="/admin/cities/remove">
    <input type="hidden" name="cityId" id="removeCityFormCityId">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<!--Add new city form-->
<div class="button_small">
    <a href="javascript:showAddCityForm();">
        <spring:message code="cities.showCityForm" />
    </a>
</div><br /><br />

<form method="post" action="/admin/cities/add" id="addCityForm" style="display: none;">
    <table>
        <tr>
            <td><spring:message code="cities.cityName" />:</td>
            <td><input type="text" id="cityName" name="cityName" ></td>
        </tr>
        <tr>
            <td><spring:message code="cities.country" />:</td>
            <td><input type="text" id="country" name="country" ></td>
        </tr>
    </table>
    <input type="submit" value="<spring:message code="cities.submit" />">
</form>
<span id="infoSpan" style="color: red;"></span>