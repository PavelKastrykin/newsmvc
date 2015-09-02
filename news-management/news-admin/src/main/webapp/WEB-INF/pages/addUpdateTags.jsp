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
			document.getElementById("editTag" + id).disabled=false;
	  		
		} else {
			document.getElementById("edit" + id).style.visibility='visible';
			document.getElementById("editActions" + id).style.visibility='hidden';
			document.getElementById("editTag" + id).disabled=true;
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
	</style>
</head>
<body>
	<div style="margin-left: 3%; margin-right: 3%">
		<tiles:insertDefinition name="base-template-navigation">
			<tiles:putAttribute name="content">
				<c:forEach var="tag" items="${tagList}">
					<form:form id="updateTagForm${tag.tagId}" method="POST" modelAttribute="tagDTO" action="/news-admin/tagUpdate">
						<form:input path="tagId" type="hidden" value="${tag.tagId }"/>
						<div style="margin-bottom: 2%">
							<table>
								<tr>
									<td><b><spring:message code="label.addUpdateTag.tag"/></b></td>
									<td>
										<form:input id="editTag${tag.tagId}" path="tagName" type="text" value="${tag.tagName}" disabled="true" style="width: 250px; display: inline-block;"/>
										<div id="edit${tag.tagId}" style="visibility: visible; display: inline-block;"><a href="javascript:doEdit(${tag.tagId});"><spring:message code="label.addUpdateTag.edit" /></a></div>
										<div id="editActions${tag.tagId}" style="visibility: hidden; display: inline-block;">
											<a href="javascript:document.getElementById('updateTagForm${tag.tagId}').submit();"><spring:message code="label.addUpdateTag.update" /></a>
											<a href="/news-admin/deleteTag?tagId=${tag.tagId }" onclick="return confirm_delete()"><spring:message code="label.addUpdateTag.delete" /></a>
											<a href="javascript:doEdit(${tag.tagId});" ><spring:message code="label.addUpdateTag.cancel" /></a>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form:form>
				</c:forEach>
				<form:form id="addTagForm" modelAttribute="tagDTO" action="/news-admin/tagAdd">
					<b><spring:message code="label.addUpdateTag.addTag" /></b>
					<form:input path="tagName" type="text" />
					<a href="javascript:document.getElementById('addTagForm').submit();"><spring:message code="label.addUpdateTag.save" /></a>
					<form:errors path="tagName" cssClass="error"/>
				</form:form>
			</tiles:putAttribute>
		</tiles:insertDefinition>
	</div>
</body>
</html>