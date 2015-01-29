/**
 * Created by Ildar on 08.01.15.
 */

function showUpdateForm() {
    $('#updateForm').toggle();
}

function showUploadAvatarForm() {
    $('#avatarForm').toggle();
}

function removeAvatar() {
    $('#removeAvatarForm').submit();
}

$(function() {
    var updForm = $('#updateForm');

    updForm.submit(function() {
        var firstName = $('#firstName').val();
        var lastName = $('#lastName').val();
        var email = $('#email').val();
        var enrollment = $('#enrollment').val();

        //Update information about student - first name, last name, e-mail and enrollment
        $.ajax({
            contentType : 'application/json',
            url : updForm.attr('action'),
            data : JSON.stringify({
                firstName : firstName,
                lastName : lastName,
                email : email,
                enrollment : enrollment
            }),
            type : 'post',
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
            success : function(data) {
                if(data) {
                    $('#firstNameTd').html(firstName);
                    $('#lastNameTd').html(lastName);
                    $('#emailTd').html(email);
                    $('#enrollmentTd').html(enrollment);

                    $('#updateSpan').html(i18n["updated"]);
                }
            }
        });

        return false;
    });
});