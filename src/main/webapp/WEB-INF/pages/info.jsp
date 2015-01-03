<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%--@elvariable id="user" type="ru.ildar.database.entities.Person"--%>
<h1>Information about <c:out value="${user.username}" /></h1>
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
        function showUpdateForm() {
            $('#updateForm').show();
        }

        $(function() {
            var updForm = $('#updateForm');

            updForm.submit(function() {
                var firstName = $('#firstName').val();
                var lastName = $('#lastName').val();
                var email = $('#email').val();
                var title = $('#title') ? $('#title').val() : null;
                var enrollment = $('#enrollment').val();
                var city = $('#citySelect').val();
                var university = $('#universitySelect').val();
                var facultyId = $('#facultySelect').val();

                $.ajax({
                    contentType : 'application/json',
                    url : updForm.attr('action'),
                    data : JSON.stringify({
                        firstName : firstName,
                        lastName : lastName,
                        email : email,
                        title : title,
                        enrollment : enrollment,
                        facultyId : facultyId
                    }),
                    type : 'post',
                    beforeSend : function(xhr) {
                        xhr.setRequestHeader('${_csrf.headerName}', '${_csrf.token}');
                    },
                    success : function(data) {
                        university = $('#universitySelect').text();
                        city = $('#citySelect').text();
                        var faculty = $('#facultySelect').text();

                        if(data) {
                            $('#firstNameTd').html(firstName);
                            $('#lastNameTd').html(lastName);
                            $('#emailTd').html(email);
                            if($('#titleTd'))
                                $('#titleTd').html(title);
                            $('#enrollmentTd').html(enrollment);
                            $('#universityTd').html(university + ", " + city);
                            $('#facultyTd').html(faculty);

                            $('#updateSpan').html('Data is successfully updated.');
                        }
                    }

                });

                return false;
            });

            function loadDataForSelect(path, elemId, data, callback) {
                $.getJSON(path + data, function(elems) {
                    $.each(elems, function(index, elem) {
                        $(elemId).append("<option value=\"" + elem.id
                                + "\">" + elem.value + "</option>");
                    });

                    if(callback)
                        callback();
                });
            }

            var facultyId = ${user.details.faculty.facultyId};
            var universityId = ${user.details.faculty.university.unId};
            var cityId = ${user.details.faculty.university.city.id};

            loadDataForSelect('/auth/cities', '#citySelect', "");
            loadDataForSelect('/auth/universities', '#universitySelect', "?cityId=" + cityId);
            loadDataForSelect('/auth/faculties', '#facultySelect', "?universityId=" + universityId);

            var citySelect = $('#citySelect');
            var uniSelect = $('#universitySelect');
            var facultySelect = $('#facultySelect');

            facultySelect.val(facultyId);
            uniSelect.val(universityId);
            citySelect.val(cityId);

            citySelect.change(function() {
                var cityId = $(this).val();
                loadDataForSelect('/auth/universities', '#universitySelect', "?cityId=" + cityId,
                        function() {
                            var firstUniId = uniSelect.find("option:first").val();
                            loadDataForSelect('/auth/faculties',
                                    '#facultySelect', "?universityId=" + firstUniId);
                        });

                uniSelect.val(uniSelect.find("option:first").val());
                facultySelect.val(facultySelect.find("option:first").val());
            });

            uniSelect.change(function() {
                var uniId = $(this).val();
                loadDataForSelect('/auth/faculties', '#facultySelect', "?universityId=" + uniId);

                facultySelect.val(facultySelect.find("option:first").val());
            });
        });
    </script>

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
    <span id="updateSpan" style="color : red;"></span>
</c:if>