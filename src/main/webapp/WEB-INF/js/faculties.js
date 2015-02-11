/**
 * Created by Ildar on 11.02.2015.
 */

var token;
function setToken(t) { token = t; }

function removeFaculty(facultyId) {
    $.ajax({
        url : '/admin/faculties/remove?facultyId=' + facultyId,
        type : 'post',
        contentType : "application/json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token);
        },
        success : function(resp) {
            if(resp.removed === true)
                $('#faculty' + facultyId).remove();
        }
    });
}