/**
 * Created by Ildar on 24.01.2015.
 */
$(function() {

    var semesterBox = $('#semesterBox');
    semesterBox.change(function() {
        var semester = $(this).val();
        //Load grades of the students in the selected semester
        $.getJSON("/stud/semesterGrades?sem=" + semester, function(grades) {
            var table = $('#gradesTable');
            table.empty();
            insertHeadRow(table);
            for(var i = 0;i < grades.length;i++) {
                var g = grades[i];
                var elem = "<tr><td>" + g.semester + "</td><td>" + g.gradeValue + "</td>"
                    + "<td>" + g.teacher.username + "</td><td>" + g.subjectName + "</td></tr>";
                table.append(elem);
            }
        });
    });

    function insertHeadRow(table) {
        var elem = "<tr>" +
                        "<th>" + i18n["semester"] + "</th>" +
                        "<th>" + i18n["grade"] + "</th>" +
                        "<th>" + i18n["teacher"] + "</th>" +
                        "<th>" + i18n["subject"] + "</th>" +
                    "</tr>";
        table.append(elem);
    }

    semesterBox.trigger("change");
});