/**
 * Created by Ildar on 04.02.2015.
 */

$(function() {
    var subjectName = $('#subjectName');
    $('#languageAbbrev').change(function() {
        $('#subjectTranslation').val('');
        var subjName = subjectName.val();
        var language = $(this).val();

        $.getJSON('/admin/subjects/getLocalization', { name : subjName, lang : language },
            function(tr) {
                if(tr.translation) {
                    //There is already a translation of this subject in this locale
                    $('#subjectTranslation').val(tr.translation);
                    $('#id').val(tr.id);
                }
                else
                    $('#id').val('');
            });
    });
});