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
	function doEdit(id) {
		if(document.getElementById("editActions" + id).style.visibility =='hidden') {
			
			document.getElementById("edit" + id).style.visibility='hidden';
			document.getElementById("editActions" + id).style.visibility='visible';
			document.getElementById("editAuthor" + id).disabled=false;
	  		
		} else {
			document.getElementById("edit" + id).style.visibility='visible';
			document.getElementById("editActions" + id).style.visibility='hidden';
			document.getElementById("editAuthor" + id).disabled=true;
		}
	}
	
	function confirm_delete() {
	  return confirm('are you sure?');
	}
	</script>
	<style>
		html { overflow-x:  hidden; }
		.error {
		 color: #ff1722;
		}
	</style>
</head>
<body>
	<div style="margin-left: 3%; margin-right: 3%">
		<tiles:insertDefinition name="base-template-navigation">
			<tiles:putAttribute name="content">
				<c:forEach var="author" items="${authorList}">
					<form:form id="updateAuthorForm${author.authorId}" method="POST" modelAttribute="authorDTO" action="/news-admin/authorUpdate">
						<form:input path="authorId" type="hidden" value="${author.authorId }"/>
						<div style="margin-bottom: 2%">
							<table>
								<tr>
									<td><b><spring:message code="label.addUpdateAuthor.author"/></b></td>
									<td>
										<form:input id="editAuthor${author.authorId}" path="authorName" type="text" value="${author.authorName}" disabled = "true" style="width: 250px; display: inline-block;"/>
										<div id="edit${author.authorId}" style="visibility: visible; display: inline-block;"><a href="javascript:doEdit(${author.authorId});"><spring:message code="label.addUpdateAuthor.edit" /></a></div>
										<div id="editActions${author.authorId}" style="visibility: hidden; display: inline-block;">
											<a href="javascript:document.getElementById('updateAuthorForm${author.authorId}').submit();"><spring:message code="label.addUpdateAuthor.update" /></a>
											<a href="/news-admin/expire?authorId=${author.authorId }" onclick="return confirm_delete()"><spring:message code="label.addUpdateAuthor.expire" /></a>
											<a href="javascript:doEdit(${author.authorId});" ><spring:message code="label.addUpdateAuthor.cancel" /></a>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form:form>
				</c:forEach>
				<form:form id="addAuthorForm" modelAttribute="authorDTO" action="/news-admin/authorAdd">
					<b><spring:message code="label.addUpdateAuthor.addAuthor" /></b>
					<form:input path="authorName" type="text" />
					<a href="javascript:document.getElementById('addAuthorForm').submit();"><spring:message code="label.addUpdateAuthor.save" /></a>
					<form:errors path="authorName" cssClass="error"/>
				</form:form>
			</tiles:putAttribute>
		</tiles:insertDefinition>
	</div>
</body>
</html>