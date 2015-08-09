<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<div class="container">
<%-- 		<form:form method="POST" commandName="searchForm"> --%>
<!-- 			<table> -->
<!-- 				<tr> -->
<!-- 					<td> -->
<%-- 						<form:select path="authorId"> --%>
<%-- 							<form:option value="NONE" label="--- Select ---" /> --%>
<%-- 							<form:options items="${authorList}" /> --%>
<%-- 					    </form:select> --%>
<!--                     </td> -->
<!--                     <td> -->
<%--                     	<form:select path="tagIdList" items="${javaSkillsList}" multiple="true" /> --%>
<!--                     </td> -->
<!--                     <td colspan="3"><input type="submit" /></td> -->
<!-- 				</tr> -->
<!-- 			</table> -->
<%-- 		</form:form> --%>
		<c:forEach var="news" items="${newsListShort}">
			
			<div style="clear: both;"></div>
			<div class="row" style="float: left;"><strong>${news.title}</strong>&#160;&#160;&#160;(by ${news.author.authorName})</div>
		  	<div class="row" style="float: right;">${news.creationDate}</div>
			<div class="row" style="clear: both;">${news.shortText}</div>
			<div class="row" style="float: right">
				<c:forEach var="tag" items="${news.tags}">
					<em>${tag.tagName}</em>
				</c:forEach>&#160;&#160;&#160;
				Comments ${fn:length(news.comments)}&#160;&#160;&#160;
				<a href="news?id=${news.newsId}">View</a>
			</div>
			
			<div style="margin-bottom: 4%"></div>
		</c:forEach>
	</div>
</body>
</html>