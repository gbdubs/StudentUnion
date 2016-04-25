<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor</title>
    </head>
    
    <body>
        <style>
            body {
                font-family: sans-serif;
        	}
        </style>
        
		<h1 class="centered">Student Union Website Editor</h1>
		
		<div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
		
		<h2>People</h2>
		
		<div>
			<a class="button" href="/self-manager">View and Manage My Personal Information</a>
		</div>
		<c:if test="${isAdmin}">
			<div>
				<a class="button" href="/users">View, Add and Remove Admins and Owners</a>
			</div>
		</c:if>
			
		<c:if test="${isAdmin}">	
			<h2>Groups</h2>
		</c:if>
		<c:if test="${isOwner}">
			<div>
				<a class="button" href="/group-creation">Create And Delete Groups</a>
			</div>
		</c:if>
		<c:if test="${isAdmin}">
			<div>
				<a class="button" href="/group-manager">Manage Group Membership</a>
			</div>
		</c:if>
		
		<c:if test="${isAdmin}">	
			<h2>Pages</h2>
			<div>
				<a class="button" href="/page-manager">Create, Edit, and Delete Pages</a>
			</div>
			<div>
				<a class="button" href="/page-manager">Change Page Permissions</a>
			</div>
		</c:if>
		
		<c:if test="${isAdmin}">	
			<h2>Petitions and News</h2>
			<div>
				<a class="button" href="/petition-manager">Create, Flag, Remove Petitions</a>
			</div>
			<div>
				<a class="button" href="/news-manager">Add News Story</a>
			</div>
			<div>
				<a class="button" href="/news-editor-manager">Edit News Story</a>
			</div>
		</c:if>
                
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>