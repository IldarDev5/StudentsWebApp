/**
 * Created by Ildar on 24.01.2015.
 */
var allowSubmit = [ false ];

$(function() {
    $('#subjectName').change(function() {
        var subjectName = $(this).text();
        $.getJSON('/admin/subjects/checkName?name=' + subjectName, function(data) {
            if(data.exists === true) {
                allowSubmit[0] = false;
                $('#subjectNameErrSpan').text('Subject with such name already exists.');
            }
            else {
                $('#subjectNameErrSpan').text('');
                allowSubmit[0] = true;
            }
        });
    });

    $('#addForm').submit(function() {
        for(allow in allowSubmit) {
            if(allow === false) {
                alert('Please correct all errors first.');
                return false;
            }
        }

        return true;
    });
});