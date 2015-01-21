<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form hidden="hidden" action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<script type="text/javascript">
    $(function() {
        if(currId)
            $('#' + currId).addClass('current');
    });
</script>

<header>
    <div id="strapline">

        <h4 style="display: inline; position: absolute; right: 10px; top: 10px;">
            <a href="?lang=en"><img height="25" src="/images/US.png" title="English language"></a>
            <a href="?lang=ru"><img height="25" src="/images/RU.png" tabindex="Russian language"></a>
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
                        <a><c:out value="${pageContext.request.userPrincipal.name}" /></a>
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
                <li id="startPageLi">
                    <a href="/startPage"><spring:message code="header.mainPage" /></a>
                </li>

                <sec:authorize access="hasRole('ROLE_STUDENT')">
                    <li id="studGradesLi">
                        <a href="/stud/grades"><spring:message code="header.grades" /></a>
                    </li>
                    <li id="studTeachersLi">
                        <a href="/stud/studTeachers"><spring:message code="header.teachers" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li id="adminUnisLi">
                        <a href="/admin/unis"><spring:message code="header.unisAndFacs" /></a>
                    </li>
                    <li id="adminSubjectsLi">
                        <a href="/admin/subjects"><spring:message code="header.subjects" /></a>
                    </li>
                    <li id="adminTeachersLi">
                        <a href="/admin/teachers"><spring:message code="header.teachers" /></a>
                    </li>
                    <li id="adminGroupsLi">
                        <a href="/admin/groups"><spring:message code="header.groups" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <li id="teacherGroupsLi">
                        <a href="/teacher/groups"><spring:message code="header.teacherGroups" /></a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <li id="loginPageLi">
                        <a href="/loginPage"><spring:message code="header.login" /></a>
                    </li>
                    <li id="registerStudentLi">
                        <a href="/register/student"><spring:message code="header.regAsStud" /></a>
                    </li>
                    <li id="registerTeacherLi">
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