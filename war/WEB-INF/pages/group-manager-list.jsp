<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Group Manager</title>
    </head>
    
    <body>
        <style> body {font-family: sans-serif; }</style>
        
        <div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
        
		<h1 class="centered">Student Union Website Editor Group Manager</h1>
		
		<h2>All Groups</h2>
		<ul>
			<c:forEach items="${groups}" var="group">
				<li class="group-wrapper">
					${group.name}
					<a href="/group-manager?groupId=${group.id}">Manage</a>
				</li>
			</c:forEach>
		</ul>
		
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>
