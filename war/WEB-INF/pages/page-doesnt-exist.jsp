<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Page Doesn't Exist</title>
    </head>
    
    <body>
        <style> body {font-family: sans-serif; }</style>
        
        <div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
        
		<h1 class="centered">The page you are trying to edit has not yet been approved.</h1>
		
		<h2>To fix this problem, have a site owner create this page, so that you are able to edit it.</h2>
		
		<h3>You can either <a href="/page-manager">Return to Page Manager/Editor</a></h3>
		<h3>Or, if you are a site owner</h3>
		<h3>You can create the page at the <a href="/page-creation">Page Creation Page</a>.</h3>
		
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>
