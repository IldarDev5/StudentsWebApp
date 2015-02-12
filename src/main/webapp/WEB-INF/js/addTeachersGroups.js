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
                var firstName = val.firstName != null ? val.firstName : "";
                var lastName = val.lastName != null ? val.lastName : "";
                var name = firstName + (firstName == "" || lastName == "" ? "" : ", ") + lastName;
                teacher.append("<option value=\"" + val.username + "\">"
                            + name + " (" + val.username + ")" + "</option>");
            });
        });
    });

    $('#facSelect').change(function() {
        var fac = $(this).val();
        loadGroups("?facId=" + fac);
    });
});