<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>

<head>
    <title>
        <c:set var="titleKey"><tiles:getAsString name="title" /></c:set>
        <spring:message code="${titleKey}" />
    </title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="/css/style.css" />
    <!-- modernizr enables HTML5 elements and feature detects -->
    <script type="text/javascript" src="/scripts/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/scripts/modernizr-1.5.min.js"></script>
</head>

<script type="text/javascript">
    var currId = '<tiles:insertAttribute name="currentId" />';
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
                                            "<spring:message code="news.more" />" +
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
</script>

<body>
<div id="main">

    <tiles:insertAttribute name="header" />

    <div id="site_content">

        <div class="sidebar_container">
            <h2><spring:message code="news.news" /></h2>
            <%--@elvariable id="news" type="java.util.List<ru.ildar.database.entities.News>"--%>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="/admin/news/add"><spring:message code="news.addNews" /></a>
            </sec:authorize>
            <div id="newsDiv">
                <c:forEach items="${news}" var="newsObj">
                    <div class="sidebar">
                        <div class="sidebar_item">
                            <h3><c:out value="${newsObj.publishDateAsString}" /></h3>
                            <p><c:out value="${newsObj.briefDescription}" /></p>
                            <a href="/news/view?newsId=${newsObj.newsId}">
                                <spring:message code="news.more" />
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="sidebar">
                <div class="sidebar_item">
                    <%--@elvariable id="newsPagesCount" type="java.lang.Integer"--%>
                    <c:forEach begin="1" end="${newsPagesCount}" step="1" var="idx">
                        <c:if test="${idx == 1}">
                            <span id="number${idx}">
                                <b><c:out value="${idx}" /></b>
                            </span>
                        </c:if>
                        <c:if test="${idx != 1}">
                            <span id="number${idx}">
                                <a href="javascript:loadNews(${idx});"><c:out value="${idx}" /></a>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="sidebar">
                <div class="sidebar_item">
                    <h2><spring:message code="contact.contact" /></h2>
                    <p><spring:message code="contact.phone" />: +79377790467</p>
                    <p><spring:message code="contact.email" />:
                        <a href="mailto:ildars1995@gmail.com">ildars1995@gmail.com</a>
                    </p>
                </div><!--close sidebar_item-->
            </div><!--close sidebar-->
        </div><!--close sidebar_container-->

        <div id="content">
            <div class="content_item">
                <tiles:insertAttribute name="body" />
            </div><!--close content_item-->
        </div><!--close content-->
    </div><!--close site_content-->

    <tiles:insertAttribute name="footer" />

</div><!--close main-->

</body>
</html>

<%--<h1>Welcome To Your Website</h1>--%>
<%--<p>This standards compliant, simple, fixed width website template is released as an 'open source' design (under the Creative Commons Attribution 3.0 Licence), which means that you are free to download and use it for anything you want (including modifying and amending it). If you wish to remove the &ldquo;website template by Free HTML5 Templates&rdquo;, all I ask is for a donation of &pound;20.00 GBP. Please feel free to contact me with any questions you may have about my free HTML5 website templates or bespoke work.</p>--%>
<%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi elit sapien, tempus sit amet hendrerit volutpat, euismod vitae risus. Etiam consequat, sem et vulputate dapibus, diam enim tristique est, vitae porta eros mauris ut orci. Praesent sed velit odio. Ut massa arcu, suscipit viverra molestie at, aliquet a metus. Nullam sit amet tellus dui, ut tincidunt justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec iaculis egestas laoreet. Nunc non ipsum metus, non laoreet urna. Vestibulum quis risus quis diam mattis tempus. Vestibulum suscipit pretium tempor. </p>--%>

<%--<div class="content_container">--%>
    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque cursus tempor enim. Aliquam facilisis neque non nunc posuere eget volutpat metus tincidunt.</p>--%>
    <%--<div class="button_small">--%>
        <%--<a href="#">Read more</a>--%>
    <%--</div><!--close button_small-->--%>
<%--</div><!--close content_container-->--%>
<%--<div class="content_container">--%>
    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque cursus tempor enim. Aliquam facilisis neque non nunc posuere eget volutpat metus tincidunt.</p>--%>
    <%--<div class="button_small">--%>
        <%--<a href="#">Read more</a>--%>
    <%--</div><!--close button_small-->--%>
<%--</div><!--close content_container-->--%>