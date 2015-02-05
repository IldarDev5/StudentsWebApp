<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<h1><spring:message code="news.viewNews" /></h1>

<h3><%--@elvariable id="newsObj" type="ru.ildar.database.entities.News"--%>
<c:out value="${newsObj.briefDescription}" /></h3>
<table>
    <tr>
        <td><spring:message code="news.author" />:</td>
        <td><c:out value="${newsObj.author.username}" /></td>
    </tr>
    <tr>
        <td><spring:message code="news.publishDate" />:</td>
        <td><c:out value="${newsObj.publishDateAsString}" /></td>
    </tr>
</table> <br />
<c:out value="${newsObj.fullDescription}" />