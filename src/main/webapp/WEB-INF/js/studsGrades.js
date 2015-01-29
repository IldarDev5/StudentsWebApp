var token;
function setToken(t) { token = t; }

function removeGrade(gradeId) {
    $('#gradeId').val(gradeId);
    $('#tGroupsId').val(${tGroup.id});
    $('#removeGradeForm').submit();
}

function updateGrade(username, semester, subjectName) {
    var grade = prompt(i18n["enterGrade"], '80');
    if(grade == null)
        return;
    if(isNaN(grade) || parseInt(grade) < 0 || parseInt(grade) > 100) {
        alert(i18n["gradeBetween"]);
        return;
    }

    $.ajax({
        url: '/teacher/grades/update',
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify({
            studentSelect : username,
            semester : semester,
            subject : subjectName,
            gradeValue : grade
        }),
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', token);
        },
        success: function(ok) {
            if(ok) {
                $('#' + username + 'Td').html(grade);
                $('#msgSpan').html(i18n["valueUpdated"]);
            }
        }
    });
}