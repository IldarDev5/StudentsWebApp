<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="teacher" type="ru.ildar.database.entities.Teacher"--%>
<h1>Information about <c:out value="${teacher.username}" /></h1>
<img src="/pictures/avatar?username=${teacher.username}" height="200" />
<table>
    <tr>
        <td>First name:</td>
        <td id="firstNameTd">${teacher.firstName}</td>
    </tr>
    <tr>
        <td>Last name:</td>
        <td id="lastNameTd">${teacher.lastName}</td>
    </tr>
    <tr>
        <td>E-Mail:</td>
        <td id="emailTd">${teacher.email}</td>
    </tr>
    <tr>
        <td>Title:</td>
        <td id="titleTd">${teacher.title}</td>
    </tr>
    <tr>
        <td>Work start date:</td>
        <td id="workStartTd">${teacher.workStartDateAsString()}</td>
    </tr>
    <tr>
        <td>University:</td>
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

    <a href="javascript:showUpdateForm();">Update Info</a>
    <form method="post" action="/teacher/info" hidden="hidden" id="updateForm">
        <table>
            <tr>
                <td><label for="firstName">First name:</label></td>
                <td><input type="text" id="firstName" value="${teacher.firstName}"></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name:</label></td>
                <td><input type="text" id="lastName" value="${teacher.lastName}"></td>
            </tr>
            <tr>
                <td><label for="email">E-Mail:</label></td>
                <td><input type="text" id="email" value="${teacher.email}"></td>
            </tr>
            <tr>
                <td><label for="title">Your title:</label></td>
                <td><input type="text" id="title" value="${teacher.title}"></td>
            </tr>
            <tr>
                <td><label for="workStart">Work start date:</label></td>
                <td><input type="text" id="workStart" value="${teacher.workStartDateAsString()}"></td>
            </tr>
            <tr>
                <td><label for="citySelect">City:</label></td>
                <td><select id="citySelect"></select></td>
            </tr>
            <tr>
                <td><label for="universitySelect">University:</label></td>
                <td><select id="universitySelect"></select></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Save">
    </form>

    <a href="javascript:showUploadAvatarForm();">Upload new avatar</a>
    <form hidden="hidden" id="avatarForm" method="post"
            action="/auth/avatar?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        Upload new image: <input type="file" name="avatar"> <br />
        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>
        <input type="submit" value="Upload">
    </form>

    <span id="updateSpan" style="color : red;"></span>
</c:if>