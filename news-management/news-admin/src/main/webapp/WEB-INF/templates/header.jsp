<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div id="header" style="border: solid black 3px; margin-bottom: 3%" >
	<div class="row" style="float: left; margin-left: 3%"><h1 style="color: blue; margin-left: 4%" >
		<a href="/news-admin/welcome"><spring:message code="label.header.home" /></a></h1>
	</div>
	<div class="row" style="float: right; margin-right: 3%">
		<security:authorize access="hasRole('ROLE_ADMIN')">
			<c:url value="/j_spring_security_logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<script>
				function formSubmit() {
					document.getElementById("logoutForm").submit();
				}
			</script>
	
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h2>
					<spring:message code="label.header.user" /> : ${pageContext.request.userPrincipal.name} | <a
						href="javascript:formSubmit()"> <spring:message code="label.header.logout" /></a>
				</h2>
			</c:if>
		</security:authorize>
	</div>
	<div style="clear:both; margin-right: 6%; size: 150%"><span style="float: right">
    	<a href="?lang=en">en</a> 
    	| 
    	<a href="?lang=ru">ru</a>
    </div>
    <div style="clear: both;"></div>
</span>
</div>