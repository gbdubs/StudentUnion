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
		<h1>Managing Group ${group.name}</h1>
		
		<h2>Basic Properties</h2>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="nameOrPageUrlOrDescription" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<h4>Name</h4>
			<input type="text" value="${group.name}" name="name"/>
			<h4>PageURL</h4>
			<input type="text" value="${group.pageUrl}" name="pageUrl"/>
			<h4>Description</h4>
			<textarea name="description">${group.description}</textarea>
			<button>Submit</button>
		</form>		
		
		<h2>Manage Leaders</h2>
		<h4>Current Leaders</h4>
		<ul>
			<c:forEach items="${group.leaders}" var="leader">
				<li>${leader}</li>
			</c:forEach>
		</ul>
		<h4>Add New Leaders</h4>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="add" name="addOrRemove"/>
			<input type="hidden" value="leader" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Submit</button>
		</form>	
		<h4>Remove Leaders</h4>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="remove" name="addOrRemove"/>
			<input type="hidden" value="leader" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Submit</button>
		</form>		
		
		
		<h2>Manage Members</h2>
		<h4>Current Members</h4>
		<ul>
			<c:forEach items="${group.members}" var="member">
				<li>${member}</li>
			</c:forEach>
		</ul>
		<h4>Add New Members</h4>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="add" name="addOrRemove"/>
			<input type="hidden" value="member" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Submit</button>
		</form>	
		<h4>Remove Members</h4>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="remove" name="addOrRemove"/>
			<input type="hidden" value="member" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Submit</button>
		</form>
		
		
		<h2>Manage Roles</h2>
		<form action="/group-manager" method="POST">
			<input type="hidden" name="manage" value="roles"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<ul>
				<c:forEach items="${group.leaders}" var="leader" varStatus="loop">
					<li>
						<input type="hidden" name="email${loop.index}" value="${leader}"/>
						${leader} - 
						<input type="text" name="role${loop.index}" value="${group.roles[leader]}"/>
					</li>
					<c:set var="lastIndex" value="${loop.index + 1}"/>
				</c:forEach>
				<c:forEach items="${group.members}" var="member" varStatus="loop">
					<li>
						<input type="hidden" name="email${loop.index + lastIndex}" value="${member}"/>
						${member} - 
						<input type="text" name="role${loop.index + lastIndex}" value="${group.roles[member]}"/>
					</li>
				</c:forEach>
			</ul>
			<button>Submit</button>
		</form>
		
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>