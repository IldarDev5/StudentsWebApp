/**
 * Created by Ildar on 04.01.15.
 */

//Variables that indicate what is needed to load
var loadUniversities = true;
var loadFacs = true;
var loadGrs = true;

//If true, after universities loading change event on select will be triggered
var triggerUniChange = false;

/** Set necessary variables */
function setLoad(unis, facs, groups) {
    loadUniversities = unis;
    loadFacs = facs;
    loadGrs = groups;
}

function loadCities(selectedCity) {
    loadDataForSelect('/ajax/cities', '#citySelect', "",
        function() {
            if(loadUniversities === true) {
                var cityId = selectedCity ? selectedCity : $('#citySelect').val();
                if(cityId)
                    loadUnis("?cityId=" + cityId);
            }
        });
}

function loadUnis(param) {
    loadDataForSelect('/ajax/universities', '#uniSelect', param,
        function() {
            if(triggerUniChange === true)
                $('#uniSelect').trigger("change");
            if(loadFacs === true) {
                var uniId = $('#uniSelect').val();
                if(uniId)
                    loadFaculties("?uniId=" + uniId);
            }
        });
}

function loadFaculties(param) {
    loadDataForSelect('/ajax/faculties', '#facSelect', param,
        function() {
            if(loadGrs === true) {
                var facId = $('#facSelect').val();
                if(facId)
                    loadGroups("?facId=" + facId);
            }
        });
}

function loadGroups(param) {
    loadDataForSelect('/ajax/groups', '#groupSelect', param);
}

/** Sends AJAX GET query and fills the specified element with arrived list data */
function loadDataForSelect(path, elemId, data, callback) {
    $.getJSON(path + data, function(elems) {
        $(elemId).empty();
        $.each(elems, function(index, elem) {
            $(elemId).append("<option value=\"" + elem.id
                + "\">" + elem.value + "</option>");
        });

        if(callback)
            callback();
    });
}