/**
 * Created by Ildar on 24.01.2015.
 */

var token;
function setToken(t) { token = t; }

function removeTGroup(tGroupId) {
    $.ajax({
        url: '/admin/teachers/groups/remove?tGroupId=' + tGroupId,
        contentType: 'application/json',
        type: 'post',
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token);
        },
        success: function(data) {
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
                teacher.append("<option value=\"" + val.username + "\">"
                + val.firstName + ", " + val.lastName + " (" + val.username + ")" + "</option>");
            });
        });
    });

    loadUnis("?cityId=" + citySelect.val());

    citySelect.change(function() {
        loadUnis("?cityId=" + $(this).val());
    });
});