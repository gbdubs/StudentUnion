<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
			
		<h1 class="light">Directory</h1>

		<h4 class="light">
			This page lists all of the pages on the Brandeis Student Union Website. 
			Some pages might be harder to find than others, so this page lists them all.
		</h4>
		
		<c:forEach items="${pages}" var="page">		
		<br>
			<a href="${page}">
				<h5 class="light">
					${page}
				</h5>
			</a>
		<br>
		</c:forEach>	
		<br>
		<br>
		<br>
		<br>
			
	</jsp:attribute>
</t:page>