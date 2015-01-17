<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
    $(function() {

        var semesterBox = $('#semesterBox');
        semesterBox.change(function() {
            var semester = $(this).val();
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
            var elem = "<tr><th>Semester</th><th>Grade</th><th>Teacher</th><th>Subject</th></tr>";
            table.append(elem);
        }

        semesterBox.trigger("change");
    });
</script>

<h1><spring:message code="stud.grades" /></h1>
<%--@elvariable id="semesters" type="java.util.List<java.lang.Long>"--%>
<div style="display: inline">
    <h3><spring:message code="stud.selectSemester" /></h3>
    <select id="semesterBox">
        <c:forEach items="${semesters}" var="semester">
            <option value="${semester}">${semester}</option>
        </c:forEach>
    </select>
</div>
<table id="gradesTable">
</table>