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
	<style type="text/css">
		<%@ include file="/resources/css/checkbox-dropdown.css"%>
	</style>
	
	<script type="text/javascript" src="/news-client/resources/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/news-client/resources/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/news-client/resources/js/checkbox-dropdown.js"></script>	
	<script type="text/javascript">
	function showList() {
		if(document.getElementById("check_boxes").style.visibility =='hidden') {
			document.getElementById("check_boxes").style.visibility='visible';
			/*document.getElementById("drop_tag").disabled = "true";*/
	  
		} else {
			document.getElementById("check_boxes").style.visibility='hidden';
		}
	}
	</script>
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
			                <td><input type="submit" class="btn btn-default" name="submit" value="${submitButton}"></td>
			                <td><input type="reset" class="btn btn-default" name="reset" value="${resetButton}"></td>
			                <td>
				                <form:select class="btn btn-default dropdown-toggle" path="authorId">
				                    <form:option value="0" ><spring:message code="label.newsIndex.select" /></form:option>
				                    <c:forEach items="${authorList}" var="author">
			    						<form:option value="${author.authorId}">${author.authorName}</form:option>
									</c:forEach>
			                	</form:select>
			                </td>
			                <td>
			                	<spring:message code="label.newsIndex.selectTags" var="selectTagButton"/>
			                	<input id="drop_tag" class="btn btn-default dropdown-toggle" name="name" type="text" value="${selectTagButton }"
    							onClick="showList();" readonly="readonly">
   								<input type="hidden" name="buttonNumber" value="1">    
   								<div id="check_boxes" class="btn btn-default dropdown-toggle" style="position: relative; visibility: hidden;"> 
	    							<c:forEach var="tag" items="${tagList}">
	     								<div class="check_box" align="left" style=" z-index: 5">
	      									<form:checkbox path="tagIdList" value="${tag.tagId}"/><c:out value="${tag.tagName}" />
	     								</div>
	    							</c:forEach>
   								</div>
			                </td>
			            </tr>
			        </table>
			    </form:form>
<!-- 			    <dl class="dropdown">  -->
<!-- 				    <dt> -->
<!-- 				    <a href="#"> -->
<!-- 				      <span class="hida">Select</span>     -->
<!-- 				      <p class="multiSel"></p>   -->
<!-- 				    </a> -->
<!-- 				    </dt> -->
<!-- 				    <dd> -->
<!-- 				        <div class="mutliSelect"> -->
<!-- 				            <ul> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="Apple" />Apple</li> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="Blackberry" />Blackberry</li> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="HTC" />HTC</li> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="Sony Ericson" />Sony Ericson</li> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="Motorola" />Motorola</li> -->
<!-- 				                <li> -->
<!-- 				                    <input type="checkbox" value="Nokia" />Nokia</li> -->
<!-- 				            </ul> -->
<!-- 				        </div> -->
<!-- 				    </dd>		  			 -->
<!-- 				</dl> -->
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
	        <div align="center">    
	            <table class="table" style="width: auto !important;">
	                <tr>
		                <td>
		                	<c:if test="${pageNumber gt 1}">
		                		<div class="btn btn-default" >
		                			<a href="view?page=${pageNumber - 1}"><spring:message code="label.newsIndex.previous"/></a>
		                		</div>
				            </c:if>
				        </td>
	                    <c:forEach begin="1" end="${fn:substringBefore(criteria.searchSize/7, '.') + 1}" var="i">
	                        <c:choose>
	                            <c:when test="${pageNumber eq i}">
	                                <td>
	                                	<div class="btn btn-default" >${i}</div>
	                                </td>
	                            </c:when>
	                            <c:otherwise>
	                                <td>
	                                	<div class="btn btn-default">
	                                		<a href="view?page=${i}">${i}</a>
	                                	</div> 
	                                </td>
	                            </c:otherwise>
	                        </c:choose>
	                    </c:forEach>
	                	<td>
		                	<c:if test="${pageNumber lt fn:substringBefore(criteria.searchSize/7, '.') + 1}">
		                		<div class="btn btn-default" ">
		                			<a href="view?page=${pageNumber + 1}"><spring:message code="label.newsIndex.next"/></a>
		                		</div>
				            </c:if>
			            </td>
		            </tr>
	            </table>
	        </div>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>