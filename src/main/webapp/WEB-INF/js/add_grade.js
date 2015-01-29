/**
 * Created by Ildar on 24.01.2015.
 */

var subject;
var semester;

/** Set necessary variables */
function setVars(subj, sem) {
    subject = subj;
    semester = sem;
}

$(function() {
    var warningSpan = $('#warningSpan');
    var studentSelect = $('#studentSelect');
    studentSelect.change(function() {
        var username = $(this).val();
        $.getJSON('/teacher/grades/checkStudentGrade',
            {
                subject: subject,
                semester: semester,
                username: username
            },
            function(grade) {
                if(grade >= 0 && grade <= 100) {
                    warningSpan.html(i18n["alreadyHasGrade"]);
                }
                else {
                    warningSpan.html('');
                }
            });
    });

    if(studentSelect.val())
        studentSelect.trigger("change");

    $('#addForm').submit(function() {
        var val = parseInt($('#gradeValue').val());
        if(isNaN(val) || val < 0 || val > 100) {
            alert(i18n["gradeBetween"]);
            return false;
        }

        return true;
    });
});