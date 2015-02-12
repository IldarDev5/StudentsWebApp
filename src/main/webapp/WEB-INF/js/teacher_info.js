/**
 * Created by Ildar on 04.01.15.
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
    var citySelect = $('#citySelect');
    var uniSelect = $('#universitySelect');
    var updForm = $('#updateForm');

    updForm.submit(function() {
        var firstName = $('#firstName').val();
        var lastName = $('#lastName').val();
        var email = $('#email').val();
        var title = $('#title').val();
        var workStart = $('#workStart').val();
        var city = citySelect.val();
        var university = uniSelect.val();

        if(city == null || university == null) {
            alert(i18n["someDataAbsent"]);
            return false;
        }

        //Update information about teacher - first name, last name, e-mail, title,
        // work start date and university
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
            success : function(resp) {
                if(resp.updated === false) {
                    //Wasn't updated due to some error
                    $('#updateSpan').html(i18n["dataNotUpdated"]);
                    return;
                }

                university = uniSelect.find("option[value='" + university + "']").text();
                city = citySelect.find("option[value='" + city + "']").text();

                if(resp.updated === true) {
                    $('#firstNameTd').html(firstName);
                    $('#lastNameTd').html(lastName);
                    $('#emailTd').html(email);
                    $('#titleTd').html(title);
                    $('#workStartTd').html(workStart);
                    $('#universityTd').html(university + ", " + city);

                    $('#updateSpan').html(i18n["dataUpdated"]);
                }
            }
        });

        return false;
    });

    loadDataForSelect('/ajax/cities', '#citySelect', "",
                        function(empty) {
                            if(cityId != null) {
                                //If there's no city defined for this teacher's university,
                                //Get first city from the loaded list. Otherwise set the current
                                //city as selected
                                citySelect.val(cityId);
                            }
                            else {
                                cityId = citySelect.val();
                            }
                            loadDataForSelect('/ajax/universities', '#universitySelect',
                                "?cityId=" + cityId, function(empty) {
                                    if(universityId != null)
                                        uniSelect.val(universityId);
                                    else
                                        uniSelect.val(uniSelect.find("option:first").val());
                                });
                        });



    citySelect.change(function() {
            var cityId = $(this).val();
            loadDataForSelect('/ajax/universities', '#universitySelect', "?cityId=" + cityId,
                function() {
                    uniSelect.val(uniSelect.find("option:first").val());
                });
    });
});