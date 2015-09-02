<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<body>
	<div style="margin-left: 3%; margin-right: 3%">
		<tiles:insertDefinition name="base-template-navigation">
			<tiles:putAttribute name="content">
				<h1>Error!</h1>
				<h1>${exception.message}</h1>
			</tiles:putAttribute>
		</tiles:insertDefinition>
	</div>
</body>
</html>