/**
 * Created by Ildar on 04.01.15.
 */

var loadUniversities = true;
var loadFacs = true;
var loadGrs = true;

function setLoad(unis, facs, groups) {
    loadUniversities = unis;
    loadFacs = facs;
    loadGrs = groups;
}

function loadCities() {
    loadDataForSelect('/ajax/cities', '#citySelect', "",
        function() {
            if(loadUniversities === true) {
                var cityId = $('#citySelect').val();
                if(cityId)
                    loadUnis("?cityId=" + cityId);
            }
        });
}

function loadUnis(param) {
    loadDataForSelect('/ajax/universities', '#uniSelect', param,
        function() {
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