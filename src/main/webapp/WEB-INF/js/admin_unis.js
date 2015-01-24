/**
 * Created by Ildar on 24.01.2015.
 */
function removeUn(unId) {
    $("#unIdHidden").val(unId);
    $('#removeUnForm').submit();
}

var universityId;
function uploadImageForUni(unId) {
    universityId = unId;
    $('#uniPic').click();
}

$(function() {
    $('#uniPic').change(function() {
        $('#uploadUnId').val(universityId);
        $('#uploadUniPicForm').submit();
    });
});