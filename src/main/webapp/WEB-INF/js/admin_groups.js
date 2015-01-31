/**
 * Created by Ildar on 24.01.2015.
 */
function loadGroups() {
    var facId = $('#facSelect').val();
    var groupsTable = $('#groupsTable');

    //Load groups of the selected faculty
    $.getJSON('/admin/groups/OfFaculty?facId=' + facId, function(data) {
        if(data.length != 0) {
            groupsTable.show();
            groupsTable.empty();
            groupsTable.append(
                "<tr>" +
                    "<td>" + i18n["groupId"] + "</td>" +
                    "<td>" + i18n["studentsCount"] + "</td>" +
                    "<td>" + i18n["facultyName"] + "</td>" +
                "</tr>");
            $.each(data, function (idx, group) {
                var href = "href=\"/auth/studentGroup?groupId=" + group.groupId + "\"";
                groupsTable.append(
                    "<tr>" +
                        "<td><a " + href + ">" + group.groupId + "</a></td>" +
                        "<td>" + group.studentsCount + "</td>" +
                        "<td>" + group.faculty.facultyName + "</td>" +
                    "</tr>");
            });
        }
        else {
            alert(i18n["noDataFound"]);
        }
    });
}

//CSRF token that is set from the JSP
var token;
function setToken(t) { token = t; }

$(function() {
    setLoad(true, true, false);
    loadCities();

    var groupNameText = $('#groupNameText');

    $('#citySelect').change(function() {
        loadUnis("?cityId=" + $(this).val());
    });

    $('#uniSelect').change(function() {
        loadFaculties("?uniId=" + $(this).val());
    });

    $('#createGroup').click(function() {
        $('#createGroupDiv').toggle();
        groupNameText.focus();
    });

    //Group creation - send AJAX request about new group information
    $('#createGroupBtn').click(function() {
        var facSelect = $('#facSelect');
        var groupId = groupNameText.val();
        var facName = facSelect.find("option:selected").text();

        if(groupId.length == 0) {
            $('#groupAddErr').html(i18n["enterSomeValue"]);
            return;
        }

        groupNameText.empty();
        $('#createGroupDiv').toggle();

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
            success: function(resp) {
                //resp = JSON.parse(resp);  //Response arrives as a string; convert it to JSON
                if(resp.ok == 'true') {
                    //Add new group to the table
                    var groupsTable = $('#groupsTable');
                    groupsTable.show();
                    if(groupsTable.html().length == 0)
                        groupsTable.append(
                            "<tr>" +
                                "<td>" + i18n["groupId"] + "</td>" +
                                "<td>" + i18n["studentsCount"] + "</td>" +
                                "<td>" + i18n["facultyName"] + "</td>" +
                            "</tr>");

                    var href = "href=\"/auth/studentGroup?groupId=" + groupId + "\"";
                    groupsTable.append(
                        "<tr>" +
                            "<td><a " + href + ">" + groupId + "</a></td>" +
                            "<td>0</td>" +
                            "<td>" + facName +"</td>" +
                        "</tr>");

                    $('#groupAddErr').html();
                    groupNameText.val("");
                    $('#createGroupDiv').hide();
                }
                else {
                    if(resp.reason == 'EMPTY') {
                        $('#groupAddErr').html(i18n["enterSomeValue"]);
                    }
                    else if(resp.reason == 'DUPLICATE_NAME') {
                        $('#groupAddErr').html(i18n["groupExists"]);
                    }
                }
            }
        });
    });

    $('#citySelect, #uniSelect, #facSelect').change(function() {
        $('#groupsTable').empty();
    })
});