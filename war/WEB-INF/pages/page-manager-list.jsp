<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Page Manager</title>
    </head>
    
    <body>
        <style> body {font-family: sans-serif; }</style>
        
        <div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
        
		<h1 class="centered">Student Union Website Editor Page Manager</h1>
		
		<h2>All Pages</h2>
		<ul>
			<c:forEach items="${pages}" var="page">
				<li class="page-wrapper">
					${page}
					<a href="/edit${page}">Edit Now</a>
					<c:if test="${isOwner}">
						<form method="POST" action="/page-manager">
							<input type="hidden" name="path" value="${page}"/>
							<input type="hidden" name="addOrDelete" value="delete"/>
							<button>Delete</button>
						</form>
					</c:if>
				</li>
			</c:forEach>
		</ul>
		<h2>Create Page</h2>
		<c:if test="${isOwner}">
			<form method="POST" action="/page-manager">
				<input type="text" name="path" placeholder="/new/page/name"/>
				<input type="hidden" name="addOrDelete" value="add"/>
				<button>Submit</button>
			</form>
		</c:if>
		
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>
