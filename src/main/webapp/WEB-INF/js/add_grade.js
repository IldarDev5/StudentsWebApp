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
                    warningSpan.html('This student already has a grade ' + grade + ' on this subject ' +
                    'in this semester. The grade will be updated.');
                }
                else {
                    warningSpan.html('');
                }
            });
    });

    if(studentSelect.val())
        studentSelect.trigger("change");

    $('#addForm').submit(function() {
        //var val = parseInt($('#gradeValue').val());
        //if(isNaN(val) || val < 0 || val > 100) {
        //    alert('Please enter the correct grade value(between 0 and 100).');
        //    return false;
        //}

        return true;
    });
});