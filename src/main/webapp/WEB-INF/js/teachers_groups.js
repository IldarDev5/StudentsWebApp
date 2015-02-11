/**
 * Created by Ildar on 24.01.2015.
 */

var token;
function setToken(t) { token = t; }

var selectedUni = null;
var selectedTeacher = null;

function removeTGroup(tGroupId) {
    $.ajax({
        url: '/admin/teachers/groups/remove?tGroupId=' + tGroupId,
        contentType: 'application/json',
        type: 'post',
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token);
        },
        success: function(resp) {
            if(resp.removed === true)
                $('#tGroup' + tGroupId + 'Tr').remove();
        }
    });
}

$(function() {
    setLoad(true, false, false);
    triggerUniChange = true;

    var uniSelect = $('#uniSelect');
    var citySelect = $('#citySelect');

    uniSelect.change(function() {
        var uniId = $(this).val();
        var teacher = $('#teacherSelect');
        $.getJSON('/admin/teachers/get', { uniId : uniId }, function(data) {
            teacher.empty();
            $.each(data, function(index, val) {
                var firstName = val.firstName != null ? val.firstName : "";
                var lastName = val.lastName != null ? val.lastName : "";
                var name = firstName +
                    (val.firstName == null || val.lastName == null ? "" : ", ") + lastName;
                teacher.append("<option value=\"" + val.username + "\">"
                            + name + " (" + val.username + ")" + "</option>");
            });

            if(selectedTeacher != null) {
                teacher.val(selectedTeacher);
                selectedTeacher = null;
            }
        });
    });

    loadUnis("?cityId=" + citySelect.val(), function(empty) {
        if(selectedUni != null) {
            $('#uniSelect').val(selectedUni);
        }
    });

    citySelect.change(function() {
        loadUnis("?cityId=" + $(this).val());
    });
});