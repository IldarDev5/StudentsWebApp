<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="localizeCity.localize" /></h1>

<script type="text/javascript">
    var cityId;

    $(function() {
        $('#language').change(function() {
            var lang = $(this).val();
            $.getJSON('/admin/cities/localize/get', { cityId : cityId, language: lang },
                        function(resp) {
                            if(resp.translation) {
                                $('#cityTranslation').val(resp.translation);
                                $('#locCityId').val(resp.locCityId);
                            }
                            else {
                                $('#cityTranslation').val('');
                                $('#locCityId').val('');
                            }
                        })
        });
    });
</script>
<script type="text/javascript">
    cityId = "${city.id}";
</script>

<%--@elvariable id="city" type="ru.ildar.database.entities.City"--%>
<%--@elvariable id="languages" type="java.util.List<ru.ildar.database.entities.Language>"--%>
<form:form method="post" action="/admin/cities/localize" commandName="cityLocalization">
    <table>
        <tr>
            <td><spring:message code="localizeCity.cityCountry" />:</td>
            <td><c:out value="${city.cityName}" />, <c:out value="${city.country}" /></td>
        </tr>
        <tr>
            <td><spring:message code="localizeCity.language" />:</td>
            <td><form:select path="language" items="${languages}"
                             itemLabel="language" itemValue="abbreviation" /></td>
            <td><form:errors path="language" /></td>
        </tr>
        <tr>
            <td><spring:message code="localizeCity.translation" />:</td>
            <td><form:input path="cityTranslation" /></td>
            <td><form:errors path="cityTranslation" /></td>
        </tr>
    </table>
    <form:errors path="locCityId" />
    <input type="hidden" name="locCityId" id="locCityId">

    <input type="hidden" name="cityId" value="${city.id}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<spring:message code="localizeCity.submit" />">
</form:form>