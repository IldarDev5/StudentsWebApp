<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1>Groups</h1>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript">
    function loadGroups() {
        var facId = $('#facSelect').val();
        var groupsTable = $('#groupsTable');

        $.getJSON('/admin/groupsOfFaculty?facId=' + facId, function(data) {
            groupsTable.empty();
            groupsTable.append("<tr><th>Group ID</th><th>Students Count</th><th>Faculty name</th></tr>");
            $.each(data, function(idx, group) {
                var href = "href=\"/auth/studentGroup?groupId=" + group.groupId + "\"";
                groupsTable.append("<tr>" +
                        "<td><a " + href + ">" + group.groupId + "</a></td>" +
                        "<td>" + group.studentsCount + "</td>" +
                        "<td>" + group.faculty.facultyName +"</td>" +
                        "</tr>");
            });
        });
    }

    $(function() {
        setLoad(true, true, false);
        loadCities();

        $('#citySelect').change(function() {
            loadUnis("?cityId=" + $(this).val());
        });

        $('#uniSelect').change(function() {
            loadFaculties("?uniId=" + $(this).val());
        });

        $('#createGroup').click(function() {
            $('#createGroupDiv').toggle();
        });

        $('#createGroupBtn').click(function() {
            var facSelect = $('#facSelect');
            var groupId = $('#groupNameText').val();
            var facName = facSelect.find("option:selected").text();
            $.ajax({
                url: '/admin/groups/add',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({
                    facId: facSelect.val(),
                    groupId: groupId
                }),
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", '${_csrf.token}');
                },
                success: function(ok) {
                    if(ok) {
                        var href = "href=\"/auth/studentGroup?groupId=" + groupId + "\"";
                        $('#groupsTable').append("<tr>" +
                                "<td><a " + href + ">" + groupId + "</a></td>" +
                                "<td>0</td>" +
                                "<td>" + facName +"</td>" +
                                "</tr>");
                    }
                }
            });
            $('#createGroupDiv').toggle();
        });
    });
</script>

<table>
    <tr>
        <td>Select the city:</td>
        <td><select id="citySelect"></select></td>
    </tr>
    <tr>
        <td>Select the university:</td>
        <td><select id="uniSelect"></select></td>
    </tr>
    <tr>
        <td>Select the faculty:</td>
        <td><select id="facSelect"></select></td>
    </tr>
</table>
<input type="button" value="Load" onclick="javascript:loadGroups();">

<table id="groupsTable" border="1">

</table>

<input type="button" id="createGroup" value="Create new group">
<div id="createGroupDiv" style="display: none;">
    Enter the group ID: <input type="text" id="groupNameText">
    <input type="button" value="Create" id="createGroupBtn">
</div>

