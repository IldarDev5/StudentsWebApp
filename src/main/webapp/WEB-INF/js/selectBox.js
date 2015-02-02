/**
 * Created by Ildar on 04.01.15.
 */

//Variables that indicate what is needed to load
var loadUniversities = true;
var loadFacs = true;
var loadGrs = true;

var submittingAllowed = false;

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
        function(empty) {
            if(!loadUniversities)
                submittingAllowed = !empty;

            if(loadUniversities) {
                var cityId = selectedCity ? selectedCity : $('#citySelect').val();
                if(cityId)
                    loadUnis("?cityId=" + cityId);
            }
        });
}

function loadUnis(param) {
    loadDataForSelect('/ajax/universities', '#uniSelect', param,
        function(empty) {
            if(empty) {
                var facSelect = $('#facSelect');
                var groupSelect = $('#groupSelect');
                if(facSelect)
                    facSelect.empty();
                if(groupSelect)
                    groupSelect.empty();
                submittingAllowed = false;
                return;
            }
            if(!loadFacs)
                submittingAllowed = !empty;

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
        function(empty) {
            if(empty) {
                var groupSelect = $('#groupSelect');
                if(groupSelect)
                    groupSelect.empty();
                submittingAllowed = false;
                return;
            }
            if(!loadGrs)
                submittingAllowed = !empty;

            if(loadGrs === true) {
                var facId = $('#facSelect').val();
                if(facId)
                    loadGroups("?facId=" + facId);
            }
        });
}

function loadGroups(param) {
    loadDataForSelect('/ajax/groups', '#groupSelect', param, function(empty) {
        submittingAllowed = !empty;
    });
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
            callback(elems.length == 0);
    });
}