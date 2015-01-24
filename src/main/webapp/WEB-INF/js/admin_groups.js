/**
 * Created by Ildar on 24.01.2015.
 */
function loadGroups() {
    var facId = $('#facSelect').val();
    var groupsTable = $('#groupsTable');

    $.getJSON('/admin/groups/OfFaculty?facId=' + facId, function(data) {
        if(data.length != 0) {
            groupsTable.empty();
            groupsTable.append("<tr><th>Group ID</th><th>Students Count</th><th>Faculty name</th></tr>");
            $.each(data, function (idx, group) {
                var href = "href=\"/auth/studentGroup?groupId=" + group.groupId + "\"";
                groupsTable.append("<tr>" +
                "<td><a " + href + ">" + group.groupId + "</a></td>" +
                "<td>" + group.studentsCount + "</td>" +
                "<td>" + group.faculty.facultyName + "</td>" +
                "</tr>");
            });
        }
        else {
            alert('No data found.');
        }
    });
}

var token;
function setToken(t) { token = t; }

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
                xhr.setRequestHeader("X-CSRF-TOKEN", token);
            },
            success: function(ok) {
                if(ok) {
                    var groupsTable = $('#groupsTable');
                    if(groupsTable.html().length == 0)
                        groupsTable.append("<tr><th>Group ID</th><th>Students Count</th><th>Faculty name</th></tr>");

                    var href = "href=\"/auth/studentGroup?groupId=" + groupId + "\"";
                    groupsTable.append("<tr>" +
                    "<td><a " + href + ">" + groupId + "</a></td>" +
                    "<td>0</td>" +
                    "<td>" + facName +"</td>" +
                    "</tr>");
                }
            }
        });
        $('#createGroupDiv').toggle();
    });

    $('#citySelect, #uniSelect, #facSelect').change(function() {
        $('#groupsTable').empty();
    })
});