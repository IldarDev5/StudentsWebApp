/**
 * Created by Ildar on 24.01.2015.
 */
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