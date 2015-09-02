<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<style>
		html { overflow-x:  hidden; }
		.error {
		 color: #ff0000;
		}
	</style>
</head>
<body>
	<tiles:insertDefinition name="singleNews">
		<tiles:putAttribute name="content">
			<div id="content">
				<div class="container">
					<div style="clear: both;"></div>
					<div class="row" style="float: left;"><strong><a href="/news-client/back">
						<spring:message code="label.singleNews.back"/></a></strong>
					</div>
					<div style="margin-left: 6%">
						<div style="clear: both;"></div>
						<div style="margin-top: 4%"></div>	
						<div style="margin-right: 6%">
							<div class="row" style="float: left; size: em;"><strong>${singleNews.title}</strong>&#160;&#160;&#160;
							(by ${singleNews.author.authorName})</div>
					  		<div class="row" style="float: right; margin-bottom: 2%">
								<fmt:formatDate value="${singleNews.creationDate}" type="both" dateStyle="short" timeStyle="short"/>
							</div>
					  		<div style="margin-right: 15%">
					  			<div class="row" style="clear: both; text-align: justify; margin-bottom: 3%;">
					  			<strong>${singleNews.shortText }</strong></div>
					  			<div class="row" style="clear: both; text-align: justify; margin-bottom: 3%;">
					  			${singleNews.fullText }</div>
					  		</div>
					  		<c:forEach var="comment" items="${singleNews.comments }">
					  			<div class="row">
									<fmt:formatDate value="${comment.creationDate}" type="both" dateStyle="short" timeStyle="short"/>
								</div>
					  			<div class="row" style="text-align: justify; margin-bottom: 1%; background-color: #F2F2F2; margin-right: 25%">
					  			${comment.commentText }</div>
					  		</c:forEach>
					  		<div>
					  			<form:form method="POST" commandName="commentDTO" action="/news-client/addComment">
						  			<input type="hidden" value="${singleNews.newsId}" name="newsId" />
						  			<form:textarea path="commentText" rows="10" style="width: 75%;" ></form:textarea>
						  			<div class="button-group" role="group">
						  				<button type="submit" name="submitComment" class="btn btn-default" onstyle="float: right; margin-right: 25%">
						  					<spring:message code="label.singleNews.postComment" />
						  				</button>
						  				
						  			</div>
						  			<form:errors path="commentText" cssClass="error"/>	
						  		</form:form>
					  		</div>
					  	</div>
					  	
					</div>
					<div style="clear: both;">
						<div class="row" style="float: left;">
							<c:choose>
								<c:when test="${(criteria.prevNewsId ne criteria.currentNewsId) and (criteria.prevNewsId ne 0)}">
									<strong><a href="/news-client/news/${criteria.prevNewsId}">
										<spring:message code="label.singleNews.previous"/></a>
									</strong>
								</c:when>
								<c:otherwise>
									<strong><spring:message code="label.singleNews.previous"/></strong>
								</c:otherwise>
							</c:choose>
						</div>
					  	<div class="row" style="float: right;">
					  		<c:choose>
								<c:when test="${(criteria.nextNewsId ne criteria.currentNewsId) and (criteria.nextNewsId ne 0) }">
									<strong><a href="/news-client/news/${criteria.nextNewsId}">
										<spring:message code="label.singleNews.next"/></a>
									</strong>
								</c:when>
								<c:otherwise>
									<strong><spring:message code="label.singleNews.next"/></strong>
								</c:otherwise>
							</c:choose>
					  	</div>
					</div>
				</div>
			</div>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>