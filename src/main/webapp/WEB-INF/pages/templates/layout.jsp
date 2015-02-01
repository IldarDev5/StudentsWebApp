<!DOCTYPE html>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>

<head>
    <title><tiles:insertAttribute name="title" /></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" type="text/css" href="/css/style.css" />
    <!-- modernizr enables HTML5 elements and feature detects -->
    <script type="text/javascript" src="/scripts/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/scripts/modernizr-1.5.min.js"></script>
</head>

<script type="text/javascript">
    var currId = '<tiles:insertAttribute name="currentId" />';
</script>

<body>
<div id="main">

    <tiles:insertAttribute name="header" />

    <div id="site_content">

        <div class="sidebar_container">
            <div class="sidebar">
                <div class="sidebar_item">
                    <h2>New Website</h2>
                    <p>Welcome to our new website. Please have a look around, any feedback is much appreciated.</p>
                </div><!--close sidebar_item-->
            </div><!--close sidebar-->
            <div class="sidebar">
                <div class="sidebar_item">
                    <h2>Latest Update</h2>
                    <h3>March 2013</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque cursus tempor enim.</p>
                </div><!--close sidebar_item-->
            </div><!--close sidebar-->
            <div class="sidebar">
                <div class="sidebar_item">
                    <h3>February 2013</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque cursus tempor enim.</p>
                </div><!--close sidebar_item-->
            </div><!--close sidebar-->
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