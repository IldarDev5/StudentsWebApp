/**
 * Created by Ildar on 09.02.2015.
 */

var token;
function setToken(t) { token = t; }

function removeSubject(subjectName) {
    var choice = confirm(i18n["confirmRemoval"]);
    if(choice == false)
        return;

    $.ajax({
        type: 'post',
        url: '/admin/subjects/remove?subjectName=' + subjectName,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token);
        },
        success: function(data) {
            $('#' + subjectName.replace(" ", "") + 'Tr').remove();
        }
    });
}