<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form hidden="hidden" action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<header>
    <div id="strapline">

        <h4 style="display: inline">
            <a href="?lang=en">English</a>
            <a href="?lang=ru">Russian</a>
        </h4>

        <sec:authorize access="isFullyAuthenticated()">
            <div style="position: absolute; top: 10px; left: 10px;">
                <h3 style="display: inline">
                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <a href="/info/teacher"><c:out value="${pageContext.request.userPrincipal.name}" /></a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <a href="/info/student"><c:out value="${pageContext.request.userPrincipal.name}" /></a>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a><spring:message code="header.admin" /></a>
                    </sec:authorize>
                </h3>
                <h3 style="display: inline">
                    <a href="javascript:$('#logoutForm').submit();">
                        <spring:message code="header.logout" />
                    </a>
                </h3>
            </div>
        </sec:authorize>

        <div id="welcome_slogan">
            <h3>
                <span><spring:message code="header.appName" /></span>
                <spring:message code="header.webApp" />
            </h3>
        </div><!--close welcome_slogan-->
    </div><!--close strapline-->
    <nav>
        <div id="menubar">
            <ul id="nav">
                <li class="current">
                    <a href="/startPage"><spring:message code="header.mainPage" /></a>
                </li>

                <sec:authorize access="hasRole('ROLE_STUDENT')">
                    <li class="current">
                        <a href="/stud/grades"><spring:message code="header.grades" /></a>
                    </li>
                    <li class="current">
                        <a href="/stud/studTeachers"><spring:message code="header.teachers" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="current">
                        <a href="/admin/unis"><spring:message code="header.unisAndFacs" /></a>
                    </li>
                    <li class="current">
                        <a href="/admin/subjects"><spring:message code="header.subjects" /></a>
                    </li>
                    <li class="current">
                        <a href="/admin/teachers"><spring:message code="header.teachers" /></a>
                    </li>
                    <li class="current">
                        <a href="/admin/groups"><spring:message code="header.groups" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <li class="current">
                        <a href="/teacher/groups"><spring:message code="header.teacherGroups" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <li class="current">
                        <a href="/loginPage"><spring:message code="header.login" /></a>
                    </li>
                    <li class="current">
                        <a href="/register/student"><spring:message code="header.regAsStud" /></a>
                    </li>
                    <li class="current">
                        <a href="/register/teacher"><spring:message code="header.regAsTeacher" /></a>
                    </li>
                </sec:authorize>
            </ul>
        </div><!--close menubar-->
    </nav>
</header>

<div id="slideshow_container">
    <div class="slideshow">
        <ul class="slideshow">
            <li class="show"><img width="940" height="250" src="/images/home_1.jpg" alt="&quot;Enter your caption here&quot;" /></li>
            <li><img width="940" height="250" src="/images/home_2.jpg" alt="&quot;Enter your caption here&quot;" /></li>
        </ul>
    </div><!--close slideshow-->
</div><!--close slideshow_container-->