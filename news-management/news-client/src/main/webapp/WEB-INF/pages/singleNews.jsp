<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<div style="clear: both;"></div>
		<div class="row" style="float: left;"><strong><a href="back">BACK</a></strong></div>
		<div style="margin-left: 6%">
			<div style="clear: both;"></div>
			<div style="margin-top: 4%"></div>	
			<div style="margin-right: 6%">
				<div class="row" style="float: left;"><strong>${singleNews.title}</strong>&#160;&#160;&#160;
				(by ${singleNews.author.authorName})</div>
		  		<div class="row" style="float: right; margin-bottom: 2%">${singleNews.creationDate}</div>
		  		<div style="margin-right: 15%">
		  			<div class="row" style="clear: both; text-align: justify; margin-bottom: 3%;">
		  			${singleNews.fullText }</div>
		  		</div>
		  		<c:forEach var="comment" items="${singleNews.comments }">
		  			<div class="row">${comment.creationDate }</div>
		  			<div class="row" style="text-align: justify; margin-bottom: 1%; background-color: #F2F2F2; margin-right: 25%">
		  			${comment.commentText }</div>
		  		</c:forEach>
		  		<div>
		  			<textarea name="commentInput" rows="10" style="width: 75%;"></textarea>
		  			<div class="button-group" role="group">
		  				<button type="button" class="btn btn-default" style="float: right; margin-right: 25%">Post comment</button>
		  			</div>
		  		</div>
		  	</div>
		  	
		</div>
		<div style="clear: both;">
			<div class="row" style="float: left;"><strong><a href="">PREVIOUS</a></strong></div>
		  	<div class="row" style="float: right;"><strong><a href="">NEXT</a></strong></div>
		</div>
	</div>
</body>
</html>