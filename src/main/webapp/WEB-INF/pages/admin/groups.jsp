<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="group.groups" /></h1>

<script type="text/javascript" src="/scripts/selectBox.js"></script>
<script type="text/javascript" src="/scripts/admin_groups.js"></script>
<script type="text/javascript">
    $(function() {
        setToken('${_csrf.token}');
    });

    var i18n = [];
    i18n["groupId"] = "<spring:message code="groups.groupId" />";
    i18n["studentsCount"] = "<spring:message code="groups.studentsCount" />";
    i18n["facultyName"] = "<spring:message code="groups.facultyName" />";
    i18n["noDataFound"] = "<spring:message code="groups.noDataFound" />";
    i18n["enterSomeValue"] = "<spring:message code="groups.enterSomeValue" />";
    i18n["groupExists"] = "<spring:message code="groups.groupExists" />";
</script>

<table>
    <tr>
        <td><spring:message code="group.selectCity" /></td>
        <td><select id="citySelect"></select></td>
    </tr>
    <tr>
        <td><spring:message code="group.selectUni" /></td>
        <td><select id="uniSelect"></select></td>
    </tr>
    <tr>
        <td><spring:message code="group.selectFac" /></td>
        <td><select id="facSelect"></select></td>
    </tr>
</table>
<input type="button" value="<spring:message code="group.load" />" onclick="javascript:loadGroups();">
<br />

<table id="groupsTable" class="prettyTable" style="display: none;">

</table>

<input type="button" id="createGroup" value="<spring:message code="group.create" />">
<div id="createGroupDiv" style="display: none;">
    <spring:message code="group.enterID" /> <input type="text" id="groupNameText">
    <input type="button" value="<spring:message code="group.submitCreate" />" id="createGroupBtn"> <br />
    <span style="color: red" id="groupAddErr"></span>
</div>

