<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="navigation" style="border-bottom: solid gray 10px; border-left: solid gray 10px; border-right: solid gray 10px;">
	<div class="row" style="margin-left: 5%; margin-bottom: 7%">
		&#10000;<a href="/news-admin/view"> <spring:message code="label.navigation.newslist" /></a>
	</div>
	<div class="row" style="margin-left: 5%; margin-bottom: 7%">
		&#10000; <a href="/news-admin/addNews"> <spring:message code="label.navigation.addnews" /> </a>
	</div>
	<div class="row" style="margin-left: 5%; margin-bottom: 7%">
		&#10000; <a href="/news-admin/addUpdateAuthors"><spring:message code="label.navigation.addupdateauthor" /></a>
	</div>
	<div class="row" style="margin-left: 5%; margin-bottom: 7%">
		&#10000; <a href="/news-admin/addUpdateTags"><spring:message code="label.navigation.addupdatetag" /></a>
	</div>
</div>