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
	function showList() {
		if(document.getElementById("check_boxes").style.visibility =='hidden') {
			document.getElementById("check_boxes").style.visibility='visible';
	  
		} else {
			document.getElementById("check_boxes").style.visibility='hidden';
		}
	}
	
	function confirm_delete() {
	  return confirm('are you sure?');
	}
	</script>
	<style>
		html { overflow-x:  hidden; }
		.error {
		 color: #ff0000;
		}
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
	<div style="margin-left: 3%; margin-right: 3%">
		<tiles:insertDefinition name="base-template-navigation">
			<tiles:putAttribute name="content">
				<c:choose >
					<c:when test="${lockError ne ''}">
						<p style="color: red;"><spring:message code="error.lock"/></p>
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
				<form:form method="POST" modelAttribute="newsDTO" action="/news-admin/newsEdited">
					<table>
						<form:hidden path="newsId"/>
						<form:hidden path="version"/>
						<tr>
							<td style="vertical-align: 0"><b><spring:message code="label.addNews.title" /></b></td>
							<td colspan="2"><form:input path="title" style="width: 400px" /></td>
							<td><form:errors path="title" cssClass="error"/></td>
						</tr>
						<tr>
							<td style="vertical-align: 0"><b><spring:message code="label.addNews.shortText" /></b></td>
							<td colspan="2"><form:textarea path="shortText" rows="3" style="width: 500px"></form:textarea></td>
							<td><form:errors path="shortText" cssClass="error"></form:errors></td>
						</tr>
						<tr>
							<td style="vertical-align: 0"><b><spring:message code="label.addNews.fullText" /></b></td>
							<td colspan="2"><form:textarea path="fullText" rows="10" style="width: 500px"></form:textarea></td>
							<td><form:errors path="fullText" cssClass="error"></form:errors></td>
						</tr>
						<form:hidden path="creationDate"/>
						<tr>
							<td/>
							<td style="vertical-align: 0">
								<form:select path="author.authorId" >
				                    <form:option value="0" ><spring:message code="label.newsIndex.select" /></form:option>
				                    <c:forEach items="${authorList}" var="authorDTO">
			    						<form:option value="${authorDTO.authorId}">${authorDTO.authorName}</form:option>
									</c:forEach>
			                	</form:select>
							</td>
							<td style="vertical-align: 0">
								<spring:message code="label.newsIndex.selectTags" var="selectTagButton"/>
			                	<input id="drop_tag" name="name" type="text" value="${selectTagButton }"
    							onClick="showList();" readonly="readonly">
   								<input type="hidden" name="buttonNumber" value="1">    
   								<div id="check_boxes" class="check_boxes"> 
	    							<c:forEach var="tag" items="${tagList}">
	     								<div class="check_box" align="left" style=" z-index: 5">
	      									<form:checkbox path="tagIdList" value="${tag.tagId}" /><c:out value="${tag.tagName}" />
	     								</div>
	    							</c:forEach>
   								</div>
							</td>
						</tr>
						<tr>
							<td><button type="submit" name="submitNews"><spring:message code="label.newsEdit.submit" /></button></td>
						</tr>
					</table>
				</form:form>
				<c:forEach var="comment" items="${newsDTO.comments}">
					<div style="margin-bottom: 1%; margin-right: 25%; margin-top: 1%">
						<div class="row">
							<fmt:formatDate value="${comment.creationDate}" type="both" dateStyle="short" timeStyle="short"/>
						</div>
						<div style="float: right;">
							<a href="/news-admin/edit/${newsDTO.newsId }/deleteComment/${comment.commentId}" onclick="return confirm_delete()">X</a>	  					
		  				</div>
			  			<div class="row" style="text-align: justify; margin-bottom: 1%; background-color: #F2F2F2;">
				  			${comment.commentText }
			  			</div>
			  		</div>
				</c:forEach>
				<div>
		  			<form:form method="POST" commandName="commentDTO" action="/news-admin/addComment">
			  			<input type="hidden" value="${newsDTO.newsId}" name="newsId" />
			  			<form:textarea path="commentText" rows="10" style="width: 75%;" ></form:textarea>
			  			<div class="button-group" role="group">
			  				<button type="submit" name="submitComment" onstyle="float: right; margin-right: 25%">
			  					<spring:message code="label.singleNews.postComment" />
			  				</button>
			  				
			  			</div>
			  			<form:errors path="commentText" cssClass="error"/>	
			  		</form:form>
		  		</div>
			</tiles:putAttribute>
		</tiles:insertDefinition>
	</div>
</body>
</html>