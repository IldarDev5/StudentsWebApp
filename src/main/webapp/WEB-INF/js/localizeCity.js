/**
 * Created by Ildar on 09.02.2015.
 */

var cityId;

$(function() {
    $('#language').change(function() {
        var lang = $(this).val();
        $.getJSON('/admin/cities/localize/get', { cityId : cityId, language: lang },
            function(resp) {
                if(resp.translation) {
                    $('#cityTranslation').val(resp.translation);
                    $('#locCityId').val(resp.locCityId);
                }
                else {
                    $('#cityTranslation').val('');
                    $('#locCityId').val('');
                }
            })
    });
});