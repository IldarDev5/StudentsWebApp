<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="teacher" type="ru.ildar.database.entities.Teacher"--%>
<h1><spring:message code="teach.info.infoAbout" arguments="${teacher.username}" /></h1>
<img src="/pictures/avatar?username=${teacher.username}" height="200" />
<table>
    <tr>
        <td><spring:message code="teach.firstName" /></td>
        <td id="firstNameTd">${teacher.firstName}</td>
    </tr>
    <tr>
        <td><spring:message code="teach.lastName" /></td>
        <td id="lastNameTd">${teacher.lastName}</td>
    </tr>
    <tr>
        <td><spring:message code="teach.email" /></td>
        <td id="emailTd">${teacher.email}</td>
    </tr>
    <tr>
        <td><spring:message code="teach.title" /></td>
        <td id="titleTd">${teacher.title}</td>
    </tr>
    <tr>
        <td><spring:message code="teach.workStartDate" /></td>
        <td id="workStartTd">${teacher.workStartDateAsString()}</td>
    </tr>
    <tr>
        <td><spring:message code="teach.university" /></td>
        <td id="facultyTd">${teacher.university.unName}, ${teacher.university.city.cityName}</td>
    </tr>
</table>

<c:if test="${pageContext.request.userPrincipal.name.equals(teacher.username)}">

    <script type="text/javascript">
        //Necessary variables that are initialized with model values
        var universityId = ${teacher.university.unId};
        var cityId = ${teacher.university.city.id};

        var csrfToken = '${_csrf.token}';
    </script>
    <script type="text/javascript" src="/scripts/selectBox.js"></script>
    <script type="text/javascript" src="/scripts/teacher_info.js"></script>

    <a href="javascript:showUpdateForm();">
        <spring:message code="teach.updateInfo" />
    </a>
    <form method="post" action="/info/teacher" hidden="hidden" id="updateForm">
        <table>
            <tr>
                <td>
                    <label for="firstName"><spring:message code="teach.firstName" /></label>
                </td>
                <td><input type="text" id="firstName" value="${teacher.firstName}"></td>
            </tr>
            <tr>
                <td>
                    <label for="lastName"><spring:message code="teach.lastName" /></label>
                </td>
                <td><input type="text" id="lastName" value="${teacher.lastName}"></td>
            </tr>
            <tr>
                <td>
                    <label for="email"><spring:message code="teach.email" /></label>
                </td>
                <td><input type="text" id="email" value="${teacher.email}"></td>
            </tr>
            <tr>
                <td>
                    <label for="title"><spring:message code="teach.yourTitle" /></label>
                </td>
                <td><input type="text" id="title" value="${teacher.title}"></td>
            </tr>
            <tr>
                <td>
                    <label for="workStart"><spring:message code="teach.workStartDate" /></label>
                </td>
                <td><input type="text" id="workStart" value="${teacher.workStartDateAsString()}"></td>
            </tr>
            <tr>
                <td>
                    <label for="citySelect"><spring:message code="teach.city" /></label>
                </td>
                <td><select id="citySelect"></select></td>
            </tr>
            <tr>
                <td>
                    <label for="universitySelect"><spring:message code="teach.university" /></label>
                </td>
                <td><select id="universitySelect"></select></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="<spring:message code="teach.save" />">
    </form>

    <a href="javascript:showUploadAvatarForm();">
        <spring:message code="teach.uploadAvatar" />
    </a>
    <form hidden="hidden" id="avatarForm" method="post"
            action="/auth/avatar?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        <spring:message code="teach.uploadImage" /> <input type="file" name="avatar"> <br />
        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>
        <input type="submit" value="<spring:message code="teach.upload" />">
    </form>

    <span id="updateSpan" style="color : red;"></span>
</c:if>