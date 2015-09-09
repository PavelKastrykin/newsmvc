<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>News List</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">		
	<script type="text/javascript" src="/news-client/resources/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/news-client/resources/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/news-client/resources/js/checkbox-dropdown.js"></script>	
	<script type="text/javascript">
	function showList() {
		if(document.getElementById("check_boxes").style.visibility =='hidden') {
			document.getElementById("check_boxes").style.visibility='visible';
	  
		} else {
			document.getElementById("check_boxes").style.visibility='hidden';
		}
	}
	</script>
	<style>
		html { overflow-x:  hidden; }
		.check_boxes {
			 border: solid gray 1px;
			 margin-top: 0%;
			 position: relative;
			 left: 0%;
			 top: 0%;
			 visibility:hidden;
			 background-color: white;
			 height: 100px;
			 width: 200px;
			 overflow-y: auto;
		}
	</style>	
</head>
<body>
	<tiles:insertDefinition name="base-template">
		<tiles:putAttribute name="content">
			<div class="container">
				<form:form method="POST" modelAttribute="criteria" >
			        <table>
			            <tr>
			                <spring:message code="label.newsIndex.submit" var="submitButton"/>
			                <spring:message code="label.newsIndex.reset" var="resetButton"/>
			                <td style="vertical-align: 0">
			                	<input type="submit" class="btn btn-default" name="submit" value="${submitButton}">
		                	</td>
			                <td style="vertical-align: 0">
				                <form:select class="btn btn-default dropdown-toggle" path="authorId">
				                    <form:option value="0" ><spring:message code="label.newsIndex.select" /></form:option>
				                    <c:forEach items="${authorList}" var="author">
			    						<form:option value="${author.authorId}">${author.authorName}</form:option>
									</c:forEach>
			                	</form:select>
			                </td>
			                <td style="vertical-align: 0">
			                	<spring:message code="label.newsIndex.selectTags" var="selectTagButton"/>
			                	<input id="drop_tag" class="btn btn-default dropdown-toggle" name="name" type="text" value="${selectTagButton }"
    							onClick="showList();" readonly="readonly">
   								<input type="hidden" name="buttonNumber" value="1">    
   								<div id="check_boxes" class="check_boxes"> 
	    							<c:forEach var="tag" items="${tagList}">
	     								<div class="check_box" align="left">
	      									<form:checkbox path="tagIdList" value="${tag.tagId}"/><c:out value="${tag.tagName}" />
	     								</div>
	    							</c:forEach>
   								</div>
			                </td>
			                <td style="vertical-align: 0">
			                	<input type="button" name="reset" class="btn btn-default" value="${resetButton}" onclick="location.href='/news-client/reset'"/>
		                	</td>
			            </tr>
			        </table>
			    </form:form>
				<c:forEach var="news" items="${newsListShort}">
					<div style="clear: both;"></div>
					<div class="row" style="float: left;"><strong>${news.title}</strong>&#160;&#160;&#160;(by ${news.author.authorName})</div>
				  	<div class="row" style="float: right;">
				  		<fmt:formatDate value="${news.creationDate}" type="both" dateStyle="short" timeStyle="short"/> 
				  	</div>
					<div class="row" style="clear: both;">${news.shortText}</div>
					<div class="row" style="float: right">
						<c:forEach var="tag" items="${news.tags}">
							<em>${tag.tagName}</em>
						</c:forEach>
						<spring:message code="label.newsIndex.comments" var="comments"/>
						<span style="color: red;">&#160;&#160;&#160; ${comments}&#160;</span>
						<span style="color: red;">${fn:length(news.comments)}&#160;&#160;&#160;</span>
						<a href="news/${news.newsId}"><spring:message code="label.newsIndex.view"/></a>
					</div>
					
					<div style="margin-bottom: 4%"></div>
				</c:forEach>
			</div>
			<c:set var="pageNumber" value="${fn:substringBefore(criteria.startWith/7, '.') + 1}" />
	        <spring:message code="label.newsIndex.previous" var="prevPage"/>
	        <spring:message code="label.newsIndex.next" var="nextPage"/>
	        <div align="center">
	        	<fmt:formatNumber value="${criteria.searchSize/7 + ((criteria.searchSize / 7) % 1 == 0 ? 0 : 0.5)}" type="number" pattern="#" var="noOfPages"/>
	        	<c:if test="${noOfPages gt 1}">    
		            <table class="table" style="width: auto !important;">
		                <tr>
			                <td>
			                	<c:if test="${pageNumber gt 1}">
			                		<input type="button" class="btn btn-default" value="${prevPage}" onclick="location.href='view?page=${pageNumber - 1}'" />
					            </c:if>
					        </td>
		                    <c:forEach begin="1" end="${noOfPages}" var="i">
		                        <c:choose>
		                            <c:when test="${pageNumber eq i}">
		                                <td>
		                                	<input type="button" class="btn btn-default" value="${i}" style="font-weight: bold;"/>
		                                </td>
		                            </c:when>
		                            <c:otherwise>
		                                <td>
		                                	<input type="button" class="btn btn-default" value="${i}" onclick="location.href='view?page=${i}'" />
		                                </td>
		                            </c:otherwise>
		                        </c:choose>
		                    </c:forEach>
		                	<td>
			                	<c:if test="${pageNumber lt noOfPages}">
			                		<input type="button" class="btn btn-default" value="${nextPage}" onclick="location.href='view?page=${pageNumber + 1}'" />
					            </c:if>
				            </td>
			            </tr>
		            </table>
	            </c:if>
	        </div>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>