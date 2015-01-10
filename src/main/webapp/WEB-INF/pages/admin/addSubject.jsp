<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<script type="text/javascript">
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
</script>

<h1>Add new subject</h1>
<%--@elvariable id="subjectTypes" type="java.util.List<java.lang.String>"--%>
<form:form id="addForm" method="post" action="/admin/subjects/add" commandName="subject">
    <table>
        <tr>
            <td><form:label path="subjectName">Subject name:</form:label></td>
            <td><form:input path="subjectName" /></td>
            <td><span id="subjectNameErrSpan" style="color: red;"></span></td>
        </tr>
        <tr>
            <td><form:label path="subjectType">Subject type:</form:label></td>
            <td><form:select path="subjectType" items="${subjectTypes}" /></td>
        </tr>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="Submit">
</form:form>