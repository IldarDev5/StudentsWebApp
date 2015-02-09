/**
 * Created by Ildar on 09.02.2015.
 */

var token;
function setToken(t) { token = t; }

$(function() {
    function localizeCityLink(cityId) {
        return "<a href='/admin/cities/localize?cityId=" + cityId + "'>" +
            "<img src='/images/user_icons/localize_cities.png'" +
            " title='" + i18n["localize"] + "'></a>";
    }
    function removeCityLink(cityId) {
        return "<a href='javascript:removeCity(" + cityId + ")'>" +
            "<img src='/images/user_icons/remove.png'" +
            " title='" + i18n["removeCity"] + "'></a>";
    }

    var addCityForm = $("#addCityForm");
    addCityForm.submit(function(){
        var cityName = $('#cityName').val();
        var country = $('#country').val();
        $.ajax({
            url : addCityForm.attr('action'),
            type : 'post',
            contentType: 'application/json',
            data : JSON.stringify({
                cityName : cityName,
                country : country
            }),
            beforeSend: function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', token);
            },
            success: function(resp) {
                if(resp.status.localeCompare('OK') == 0) {
                    $('#infoSpan').html(i18n["addSuccessful"]);
                    $('#cityName').val('');
                    $('#country').val('');
                    $('#addCityForm').hide();
                    $('#citiesTable').append('<tr>' +
                    '<td>' + cityName + '</td>' +
                    '<td>' + country + '</td>' +
                    "<td>" + localizeCityLink(resp.cityId) +
                    removeCityLink(resp.cityId) +
                    "</td></tr>");
                }
            }
        });

        return false;
    });
});

function showAddCityForm() {
    $('#addCityForm').toggle();
    $('#cityName').focus();
}

function removeCity(cityId) {
    var choice = confirm(i18n["cityRemovalAttention"]);
    if(choice == false)
        return;

    $('#removeCityFormCityId').val(cityId);
    $('#removeCityForm').submit();
}