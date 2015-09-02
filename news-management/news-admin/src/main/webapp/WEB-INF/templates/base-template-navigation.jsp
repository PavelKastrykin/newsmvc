<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <tiles:getAsString name="title" />
    </title>
</head>
<body style="width: 100%; height: 100%;">
	<table width="100%"">
		<tr>
			<td width="100%" colspan="2"><tiles:insertAttribute name="header" /></td>
		</tr>
		<tr>
			<td width="20%" style="vertical-align: baseline;"><tiles:insertAttribute name="navigation" /></td>
			<td width="80%"><tiles:insertAttribute name="content" /></td>	
		</tr>
		<tr>
			<td width="100%" colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr >
	</table>
</body>
</html>