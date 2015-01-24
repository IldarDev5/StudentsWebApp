/**
 * Created by Ildar on 24.01.2015.
 */
$(function() {
    setLoad(true, true, true);
    triggerUniChange = true;

    loadCities();

    $('#citySelect').change(function() {
        var city = $(this).val();
        loadUnis("?cityId=" + city);
    });

    $('#uniSelect').change(function() {
        var uniId = $(this).val();
        loadFaculties("?uniId=" + uniId);

        var teacher = $('#teacherSelect');
        $.getJSON('/admin/teachers/get', { uniId : uniId }, function(data) {
            teacher.empty();
            $.each(data, function(index, val) {
                teacher.append("<option value=\"" + val.username + "\">"
                + val.firstName + ", " + val.lastName + " (" + val.username + ")" + "</option>");
            });
        });
    });

    $('#facSelect').change(function() {
        var fac = $(this).val();
        loadGroups("?facId=" + fac);
    });
});