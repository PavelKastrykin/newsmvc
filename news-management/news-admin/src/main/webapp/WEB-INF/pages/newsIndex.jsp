<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>Insert title here</title>
	<script type="text/javascript">
		function confirm_delete() {
		  return confirm('are you sure?');
		}
		function showList() {
			if(document.getElementById("check_boxes").style.visibility =='hidden') {
				document.getElementById("check_boxes").style.visibility='visible';
				/*document.getElementById("drop_tag").disabled = "true";*/
		  
			} else {
				document.getElementById("check_boxes").style.visibility='hidden';
			}
		}
	</script>
	<style>
		html { overflow:  hidden; }
		.check_boxes {
			 border: solid gray 1px;
			 margin-top: 0%;
			 position: relative;
			 left: 0%;
			 top: 0%;
			 visibility:hidden;
			 background-color: white;
			 height: 100px;
			 overflow-y: auto;
			}
	</style>
</head>
<body>
	<div style="margin-left: 3%; margin-right: 3%; position: relative;" >
		<tiles:insertDefinition name="base-template-navigation">
			<tiles:putAttribute name="content">
				<div style="margin-left: 1%; margin-right: 1%">
					<form:form id="filterForm" method="POST" modelAttribute="criteria" style="margin-left: 20%">
				        <table>
				            <tr>
			                	<spring:message code="label.newsIndex.submit" var="submitButton"/>
			                	<spring:message code="label.newsIndex.reset" var="resetButton"/>
				                <td style="vertical-align: 0">
				                	<input type="submit" name="submit" value="${submitButton}">
				                </td>
				                <td style="vertical-align: 0">
					                <form:select path="authorId">
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
	   								<div id="check_boxes" class="check_boxes" style="position: relative; visibility: hidden;"> 
		    							<c:forEach var="tag" items="${tagList}">
		     								<div class="check_box" align="left" style=" z-index: 5">
		      									<form:checkbox path="tagIdList" value="${tag.tagId}"/><c:out value="${tag.tagName}" />
		     								</div>
		    							</c:forEach>
	   								</div>
				                </td>
				                <td style="vertical-align: 0">
				                	<input type="button" name="reset" value="${resetButton}" onclick="location.href='/news-admin/reset'"/>
				                </td>
				            </tr>
				        </table>
			        </form:form>
					<form:form method="POST" modelAttribute="criteria" action="/news-admin/deleteNews">
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
								<a href="edit/${news.newsId}/"><spring:message code="label.newsIndex.edit"/></a>
								<form:checkbox path="deleteNewsList" value="${news.newsId}"/>
							</div>
							<div style="margin-bottom: 4%"></div>
						</c:forEach>
						<div style="float: right;">
							<button type="submit" name="deleteNewsList" onclick="return confirm_delete()">
								<spring:message code="label.newsIndex.delete" />
							</button>
						</div>
					</form:form>
				</div>
				<c:set var="pageNumber" value="${fn:substringBefore(criteria.startWith/7, '.') + 1}" />
		        <div align="center">    
		            <table class="table" style="width: auto !important;">
		                <tr>
			                <td>
			                	<c:if test="${pageNumber gt 1}">
			                		<button >
			                			<a href="view?page=${pageNumber - 1}"><spring:message code="label.newsIndex.previous"/></a>
			                		</button>
					            </c:if>
					        </td>
		                    <c:forEach begin="1" end="${fn:substringBefore(criteria.searchSize/7, '.') + 1}" var="i">
		                        <c:choose>
		                            <c:when test="${pageNumber eq i}">
		                                <td>
		                                	<button ><b>${i}</b></button>
		                                </td>
		                            </c:when>
		                            <c:otherwise>
		                                <td>
		                                	<button>
		                                		<a href="view?page=${i}">${i}</a>
		                                	</button> 
		                                </td>
		                            </c:otherwise>
		                        </c:choose>
		                    </c:forEach>
		                	<td>
			                	<c:if test="${pageNumber lt fn:substringBefore(criteria.searchSize/7, '.') + 1}">
			                		<button>
			                			<a href="view?page=${pageNumber + 1}"><spring:message code="label.newsIndex.next"/></a>
			                		</button>
					            </c:if>
				            </td>
			            </tr>
		            </table>
		        </div>
			</tiles:putAttribute>
		</tiles:insertDefinition>
	</div>
</body>
</html>