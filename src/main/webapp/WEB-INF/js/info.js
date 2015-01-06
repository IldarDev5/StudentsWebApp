/**
 * Created by Ildar on 04.01.15.
 */

function showUpdateForm() {
    $('#updateForm').toggle();
}

function showUploadAvatarForm() {
    $('#avatarForm').toggle();
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
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
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

    loadDataForSelect('/auth/cities', '#citySelect', "");
    loadDataForSelect('/auth/universities', '#universitySelect', "?cityId=" + cityId);
    loadDataForSelect('/auth/faculties', '#facultySelect', "?universityId=" + universityId);

    var citySelect = $('#citySelect');
    var uniSelect = $('#universitySelect');
    var facultySelect = $('#facultySelect');

    //facultySelect.val(facultyId);
    //uniSelect.val(universityId);
    //citySelect.val(cityId);

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