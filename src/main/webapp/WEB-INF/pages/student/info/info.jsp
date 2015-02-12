<%--@elvariable id="cityLoc" type="ru.ildar.database.entities.LocalizedCity"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/css/jquery-ui.min.css">
<script type="text/javascript" src="/scripts/jquery/jquery-ui.min.js"></script>
<script type="text/javascript">
    $(function() {
        var enrollment = $('#enrollment');
        enrollment.datepicker({ dateFormat : 'dd/mm/yy' });
        if(enrollment.val() == '')
            enrollment.val('01/01/2000');
    });
</script>

<%--@elvariable id="stud" type="ru.ildar.database.entities.Student"--%>
<h1><spring:message code="stud.about" arguments="${stud.username}" /></h1>
<img src="/pictures/avatar?username=${stud.username}" height="200" />
<table>
    <tr>
        <td><spring:message code="stud.firstName" /></td>
        <td id="firstNameTd">${stud.firstName}</td>
    </tr>
    <tr>
        <td><spring:message code="stud.lastName" /></td>
        <td id="lastNameTd">${stud.lastName}</td>
    </tr>
    <tr>
        <td><spring:message code="stud.email" /></td>
        <td id="emailTd">${stud.email}</td>
    </tr>
    <tr>
        <td><spring:message code="stud.enrollmentDate" /></td>
        <td id="enrollmentTd">${stud.enrollmentDateAsString()}</td>
    </tr>
    <tr>
        <td><spring:message code="stud.group" /></td>
        <td id="groupTd">
            <a href="/auth/studentGroup?groupId=${stud.group.groupId}">
                ${stud.group.groupId}
            </a>
        </td>
    </tr>
    <tr>
        <td><spring:message code="stud.faculty" /></td>
        <td id="facultyTd">${stud.group.faculty.facultyName}</td>
    </tr>
    <tr>
        <td><spring:message code="stud.unName" /></td>
        <td id="universityTd">
            ${stud.group.faculty.university.unName}
            <c:if test="${cityLoc != null}">
                , ${cityLoc.translatedName}
            </c:if>
        </td>
    </tr>
</table>

<c:if test="${pageContext.request.userPrincipal.name.equals(stud.username)}">

    <script type="text/javascript">
        var csrfToken = '${_csrf.token}';

        var i18n = [];
        i18n["updated"] = "<spring:message code="studInfo.dataUpdated" />";
        i18n["fileSizeBig"] = "<spring:message code="studInfo.fileSizeBig" arguments="1" />";
    </script>
    <script type="text/javascript" src="/scripts/selectBox.js"></script>
    <script type="text/javascript" src="/scripts/stud_info.js"></script>

    <div class="button_small" style="margin-right: 7px;">
        <a href="javascript:showUpdateForm();">
            <spring:message code="stud.updateInfo" />
        </a>
    </div><br /><br />

    <form method="post" action="/info/student" hidden="hidden" id="updateForm">
        <table>
            <tr>
                <td><spring:message code="stud.firstName" /></td>
                <td><input type="text" id="firstName" value="${stud.firstName}"></td>
            </tr>
            <tr>
                <td><spring:message code="stud.lastName" /></td>
                <td><input type="text" id="lastName" value="${stud.lastName}"></td>
            </tr>
            <tr>
                <td><spring:message code="stud.email" /></td>
                <td><input type="text" id="email" value="${stud.email}"></td>
            </tr>
            <tr>
                <td><spring:message code="stud.enrollmentDate" /></td>
                <td><input type="text" id="enrollment" value="${stud.enrollmentDateAsString()}"></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="<spring:message code="stud.save" />">
    </form>

    <div class="button_small" style="margin-right: 7px;">
        <a href="javascript:showUploadAvatarForm();">
            <spring:message code="stud.uploadAvatar" />
        </a>
    </div>
    <c:if test="${fn:length(stud.personPhoto) == 1}">
        <!--This student has an avatar-->
        <div class="button_small" style="margin-right: 7px;">
            <a href="javascript:removeAvatar();">
                <spring:message code="stud.removeAvatar" />
            </a>
        </div>
    </c:if>
    <br /><br />
    <form hidden="hidden" id="removeAvatarForm" method="post" action="/info/removePic">
        <input type="hidden" name="username" value="${stud.username}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </form>
    <form hidden="hidden" id="avatarForm" method="post"
            action="/pictures/avatar?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        <spring:message code="stud.uploadImage" /> <input type="file" id="avatar" name="avatar"> <br />
        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>
        <input type="submit" value="Upload">
    </form>

    <span id="updateSpan" style="color : red;"></span>
</c:if>