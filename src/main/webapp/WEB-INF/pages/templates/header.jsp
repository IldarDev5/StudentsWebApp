<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form hidden="hidden" action="/logout" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>

<header>
    <div id="strapline">

        <sec:authorize access="isFullyAuthenticated()">
            <div style="position: absolute; top: 10px; left: 10px;">
                <h3 style="display: inline">
                    <a href="/auth/info"><c:out value="${pageContext.request.userPrincipal.name}" /></a>
                </h3>
                <h3 style="display: inline">
                    <a href="javascript:$('#logoutForm').submit();">Log Out</a>
                </h3>
            </div>
        </sec:authorize>

        <div id="welcome_slogan">
            <h3><span>Students And Teachers</span> Web Application</h3>
        </div><!--close welcome_slogan-->
    </div><!--close strapline-->
    <nav>
        <div id="menubar">
            <ul id="nav">
                <li class="current"><a href="/startPage">Main Page</a></li>

                <sec:authorize access="hasRole('ROLE_STUDENT')">
                    <li class="current"><a href="/stud/grades">Grades</a></li>
                    <li class="current"><a href="/stud/studTeachers">Teachers</a></li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="current"><a href="/admin/unis">Universities and Faculties</a></li>
                    <li class="current"><a href="/admin/subjects">Subjects</a></li>
                    <li class="current"><a href="/admin/teachers">Teachers</a></li>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <li class="current"><a href=""></a></li>
                    <li class="current"><a href=""></a></li>

                </sec:authorize>

                <sec:authorize access="isAnonymous()">
                    <li class="current"><a href="/loginPage">Log In</a></li>
                    <li class="current"><a href="/registerPage?reg=stud">Register as a student</a></li>
                    <li class="current"><a href="/registerPage?reg=teach">Register as a teacher</a></li>
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