/**
 * Created by Ildar on 08.01.15.
 */

//Load cities, universities and faculties to register student
$(function() {
    setLoad(true, true, true);

    var citySelect = $('#citySelect');
    citySelect.change(function() {
        loadUnis("?cityId=" + $(this).val());
    });

    $('#uniSelect').change(function() {
        loadFaculties("?uniId=" + $(this).val());
    });

    $('#facSelect').change(function() {
        loadGroups("?facId=" + $(this).val());
    });

    citySelect.trigger("change");
});