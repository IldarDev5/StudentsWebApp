/**
 * Created by Ildar on 05.02.2015.
 */

var currNewsPage = 1;

function operations(newsObj) {
    var result = "";
    if(isAdmin === true) {
        result += "<a href='javascript:removeNews(" + newsObj.newsId + ")' " +
                    "style='text-decoration:none;'>" +
                    "<img src='/images/user_icons/remove.png' title='" + i18n["newsRemove"] + "'>" +
                  "</a>";
        if(newsObj.author.username.localeCompare(username) == 0) {
            result += "<a href='/admin/news/edit?newsId=" + newsObj.newsId + "'>" +
                        "<img src='/images/user_icons/update.png' title='" + i18n["newsEdit"] + "'>" +
                      "</a>";
        }
    }

    return result;
}

function loadNews(pageNumber) {
    $.getJSON('/news/' + pageNumber, { }, function(news) {
        var newsDiv = $('#newsDiv');
        newsDiv.empty();
        $.each(news, function(index, newsObj) {
            newsDiv.append("<div class='sidebar'>" +
                                "<div class='sidebar_item'>" +
                                    "<h3>" + newsObj.publishDateAsString + "</h3>" +
                                    "<p>" + newsObj.briefDescription + operations(newsObj) + "</p>" +
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