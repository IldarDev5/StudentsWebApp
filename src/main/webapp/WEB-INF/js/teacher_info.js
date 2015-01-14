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
        var title = $('#title').val();
        var workStart = $('#workStart').val();
        var city = $('#citySelect').val();
        var university = $('#universitySelect').val();

        $.ajax({
            contentType : 'application/json',
            url : updForm.attr('action'),
            data : JSON.stringify({
                firstName : firstName,
                lastName : lastName,
                email : email,
                title : title,
                workStart : workStart,
                unId : university
            }),
            type : 'post',
            beforeSend : function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
            success : function(data) {
                    university = $('#universitySelect').text();
                    city = $('#citySelect').text();

                    if(data) {
                    $('#firstNameTd').html(firstName);
                    $('#lastNameTd').html(lastName);
                    $('#emailTd').html(email);
                    $('#titleTd').html(title);
                    $('#workStartTd').html(workStart);
                    $('#universityTd').html(university + ", " + city);

                    $('#updateSpan').html('Data is successfully updated.');
                }
            }
        });

        return false;
    });

    loadDataForSelect('/ajax/cities', '#citySelect', "");
    loadDataForSelect('/ajax/universities', '#universitySelect', "?cityId=" + cityId);

    var citySelect = $('#citySelect');
    var uniSelect = $('#universitySelect');

    //facultySelect.val(facultyId);
    //uniSelect.val(universityId);
    //citySelect.val(cityId);

    citySelect.change(function() {
            var cityId = $(this).val();
            loadDataForSelect('/ajax/universities', '#universitySelect', "?cityId=" + cityId,
                function() {
                    uniSelect.val(uniSelect.find("option:first").val());
                });
    });
});