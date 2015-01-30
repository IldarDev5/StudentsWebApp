/**
 * Created by Ildar on 24.01.2015.
 */
//var allowSubmit = [ false ];

$(function() {
    $('#subjectName').change(function() {
        var subjectName = $(this).val();
        $.getJSON('/admin/subjects/checkName?name=' + subjectName, function(data) {
            if(data.exists === true) {
                $('#inputErr').html(i18n["subjectExists"]);
            }
            else {
                $('#inputErr').html('');
            }
        });
    });

    //$('#addForm').submit(function() {
    //    for(allow in allowSubmit) {
    //        if(allow === false) {
    //            alert(i18n["correctErrors"]);
    //            return false;
    //        }
    //    }
    //
    //    return true;
    //});
});