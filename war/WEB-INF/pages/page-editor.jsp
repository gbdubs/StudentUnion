<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	
	<jsp:attribute name="css">
		${cssContent}
	</jsp:attribute>
	
	<jsp:attribute name="content">	
		${htmlContent}
	</jsp:attribute>
	
	<jsp:attribute name="js">
		${javascriptContent}
	</jsp:attribute>
	
</t:page>