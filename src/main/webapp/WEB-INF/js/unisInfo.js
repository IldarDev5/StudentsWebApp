/**
 * Created by Ildar on 05.02.2015.
 */

var canSubmit = true;

$(function() {
    setLoad(true, false, false);

    var citySelect = $('#cityId');

    if(unCityId != null) {
        citySelect.val(unCityId);
    }

    citySelect.change(function() {
        loadUnis("?cityId=" + $(this).val(), function(empty) {
            if(unId != null) {
                $('#uniSelect').val(unId);
                unId = null;
            }
            canSubmit = !empty;
        });
    });
    citySelect.trigger("change");

    $('#university').submit(function() {
        if(!canSubmit)
            alert(i18n["someDataAbsent"]);

        return canSubmit;
    });
});