<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="header" style="border: solid black 3px; margin-bottom: 3%" >
	<div><h1 style="color: blue; margin-left: 4%" >
		<a href="/news-client/home"><spring:message code="label.tiles.newsportal"/></a></h1>
	</div>
	<div style="margin-right: 6%; size: 150%"><span style="float: right">
    	<a href="?lang=en">en</a> 
    	| 
    	<a href="?lang=ru">ru</a>
    </div>
    <div style="clear: both;"></div>
</span>
</div>