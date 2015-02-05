/**
 * Created by Ildar on 05.02.2015.
 */

var currNewsPage = 1;

function loadNews(pageNumber) {
    $.getJSON('/news/' + pageNumber, { }, function(news) {
        var newsDiv = $('#newsDiv');
        newsDiv.empty();
        $.each(news, function(index, newsObj) {
            newsDiv.append("<div class='sidebar'>" +
                                "<div class='sidebar_item'>" +
                                    "<h3>" + newsObj.publishDateAsString + "</h3>" +
                                    "<p>" + newsObj.briefDescription + "</p>" +
                                    "<a href='/news/view?newsId=" + newsObj.newsId + "'>" +
                                        i18n["moreNews"] +
                                    "</a>" +
                                "</div>" +
                            "</div>");
        });

        $('#number' + pageNumber).html("<b>" + pageNumber + "</b>");
        $('#number' + currNewsPage).html("<a href='javascript:loadNews(" + currNewsPage +
                                         ")'>" + currNewsPage + "</a>");
        currNewsPage = pageNumber;
    });
}

function removeNews(newsId) {
    $('#layoutNewsId').val(newsId);
    $('#newsDeleteForm').submit();
}