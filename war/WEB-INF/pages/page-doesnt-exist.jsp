<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section left-align">
			
				<h2 class="center-align">The page you were trying to edit has not yet been created.</h2>
		
				<h5>
					To fix this problem, have a site owner create this page, so that you are able to edit it.
					The list of current site owners is available at the 
					<a href="/users">user management page</a>.
				</h5>
				<h5>
					It is possible that you are trying to edit another page, check out the
					<a href="/page-manager">page list</a> 
					to look through the full list of student union pages that you can edit.
				</h5>
				<h5>
					If you are a site owner, you can create the page by going to the 
					<a href="/page-manager">page manager</a> and going to the bottom of the
					page.
				</h5>
				<h5>
					Please contact Grady Ward if this problem persists.
				</h5>
				
			</div>
		</div>
	</jsp:attribute>
</t:page>