<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="stud" type="ru.ildar.database.entities.Student"--%>
<h1>Information about <c:out value="${stud.username}" /></h1>
<img src="/pictures/avatar?username=${stud.username}" height="200" />
<table>
    <tr>
        <td>First name:</td>
        <td id="firstNameTd">${stud.firstName}</td>
    </tr>
    <tr>
        <td>Last name:</td>
        <td id="lastNameTd">${stud.lastName}</td>
    </tr>
    <tr>
        <td>E-Mail:</td>
        <td id="emailTd">${stud.email}</td>
    </tr>
    <tr>
        <td>Enrollment date:</td>
        <td id="enrollmentTd">${stud.enrollmentDateAsString()}</td>
    </tr>
    <tr>
        <td>Group:</td>
        <td id="groupTd">
            <a href="/stud/group?groupId=${student.group.groupId}">
                ${stud.group.groupId}
            </a>
        </td>
    </tr>
    <tr>
        <td>Faculty:</td>
        <td id="facultyTd">${stud.group.faculty.facultyName}</td>
    </tr>
    <tr>
        <td>University:</td>
        <td id="universityTd">${stud.group.faculty.university.unName},
        ${stud.group.faculty.university.city.cityName}</td>
    </tr>
</table>

<c:if test="${pageContext.request.userPrincipal.name.equals(stud.username)}">

    <script type="text/javascript">
        var csrfToken = '${_csrf.token}';
    </script>
    <script type="text/javascript" src="/scripts/selectBox.js"></script>
    <script type="text/javascript" src="/scripts/stud_info.js"></script>

    <a href="javascript:showUpdateForm();">Update Info</a>
    <form method="post" action="/stud/info" hidden="hidden" id="updateForm">
        <table>
            <tr>
                <td><label for="firstName">First name:</label></td>
                <td><input type="text" id="firstName" value="${stud.firstName}"></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name:</label></td>
                <td><input type="text" id="lastName" value="${stud.lastName}"></td>
            </tr>
            <tr>
                <td><label for="email">E-Mail:</label></td>
                <td><input type="text" id="email" value="${stud.email}"></td>
            </tr>
            <tr>
                <td><label for="enrollment">Enrollment date:</label></td>
                <td><input type="text" id="enrollment" value="${stud.enrollmentDateAsString()}"></td>
            </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="submit" value="Save">
    </form>

    <a href="javascript:showUploadAvatarForm();">Upload new avatar</a>
    <form hidden="hidden" id="avatarForm" method="post"
            action="/pictures/avatar?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        Upload new image: <input type="file" name="avatar"> <br />
        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>
        <input type="submit" value="Upload">
    </form>

    <span id="updateSpan" style="color : red;"></span>
</c:if>