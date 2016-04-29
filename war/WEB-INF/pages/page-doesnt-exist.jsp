<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section">
			
			
				<p>
					Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
				</p>
				<p>
					Click here to <a href="${logoutUrl}">Log Out</a> or to <a href="/console">return to the console</a>.
				</p>
				<br>
		
				<h1>The page you are trying to edit has not yet been created.</h1>
		
				<h2>To fix this problem, have a site owner create this page, so that you are able to edit it.</h2>
		
				<h3>You can either <a href="/page-manager">Return to Page Manager/Editor</a> to learn about the way pages are set up, or to edit a different page</h3>
				<h3>or, if you are a site owner</h3>
				<h3>You can create the page at the <a href="/page-manager">Page Creation Page</a>.</h3>
		

			</div>
		</div>
	</jsp:attribute>
</t:page>