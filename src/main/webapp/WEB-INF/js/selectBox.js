/**
 * Created by Ildar on 04.01.15.
 */

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