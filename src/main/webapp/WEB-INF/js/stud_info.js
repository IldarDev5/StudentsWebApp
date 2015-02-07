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

    $("#avatarForm").submit(function() {
        try {
            var fileSize = 0.0;
            //for FF, Safari, Opera and Others
            fileSize = $("#avatar")[0].files[0].size / 1048576.0; //size in MBs
        }
        catch (e) {
            alert("Error is :" + e);
            return false;
        }

        if(fileSize > 1) {
            //File size is more than 1 MB
            alert(i18n["fileSizeBig"]);
            return false;
        }

        return true;
    });
});