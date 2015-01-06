<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="user" type="ru.ildar.database.entities.Person"--%>
<h1>Information about <c:out value="${user.username}" /></h1>
<img src="/pictures/avatar?username=${user.username}" height="200" />
<table>
    <tr>
        <td>First name:</td>
        <td id="firstNameTd">${user.details.firstName}</td>
    </tr>
    <tr>
        <td>Last name:</td>
        <td id="lastNameTd">${user.details.lastName}</td>
    </tr>
    <tr>
        <td>E-Mail:</td>
        <td id="emailTd">${user.details.email}</td>
    </tr>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <tr>
            <td>Title:</td>
            <td id="titleTd">${user.details.title}</td>
        </tr>
    </sec:authorize>
    <tr>
        <td>Enrollment/work start date:</td>
        <td id="enrollmentTd">${user.details.enrollmentDateAsString()}</td>
    </tr>
    <tr>
        <td>Faculty:</td>
        <td id="facultyTd">${user.details.faculty.facultyName}</td>
    </tr>
    <tr>
        <td>University:</td>
        <td id="universityTd">${user.details.faculty.university.unName}, ${user.details.faculty.university.city.cityName}</td>
    </tr>
</table>

<c:if test="${pageContext.request.userPrincipal.name.equals(user.username)}">

    <script type="text/javascript">
        //Necessary variables that are initialized with model values
        var facultyId = ${user.details.faculty.facultyId};
        var universityId = ${user.details.faculty.university.unId};
        var cityId = ${user.details.faculty.university.city.id};

        var csrfToken = '${_csrf.token}';
    </script>
    <script type="text/javascript" src="/scripts/selectBox.js"></script>
    <script type="text/javascript" src="/scripts/info.js"></script>

    <a href="javascript:showUpdateForm();">Update Info</a>
    <form method="post" action="/auth/info" hidden="hidden" id="updateForm">
        <table>
            <tr>
                <td><label for="firstName">First name:</label></td>
                <td><input type="text" id="firstName" value="${user.details.firstName}"></td>
            </tr>
            <tr>
                <td><label for="lastName">Last name:</label></td>
                <td><input type="text" id="lastName" value="${user.details.lastName}"></td>
            </tr>
            <tr>
                <td><label for="email">E-Mail:</label></td>
                <td><input type="text" id="email" value="${user.details.email}"></td>
            </tr>
            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <tr>
                    <td><label for="title">Your title:</label></td>
                    <td><input type="text" id="title" value="${user.details.title}"></td>
                </tr>
            </sec:authorize>
            <tr>
                <td><label for="enrollment">Enrollment/work start date:</label></td>
                <td><input type="text" id="enrollment" value="${user.details.enrollmentDateAsString()}"></td>
            </tr>
            <tr>
                <td><label for="citySelect">City:</label></td>
                <td><select id="citySelect"></select></td>
            </tr>
            <tr>
                <td><label for="universitySelect">University:</label></td>
                <td><select id="universitySelect"></select></td>
            </tr>
            <tr>
                <td><label for="facultySelect">Faculty:</label></td>
                <td><select id="facultySelect"></select></td>
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