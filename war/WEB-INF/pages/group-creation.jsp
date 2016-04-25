<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Group Creation</title>
    </head>
    
    <body>
        <style>
            body {
                font-family: sans-serif;
        	}
        </style>
        
		<h1 class="centered">Student Union Website Editor Group Creation</h1>
		
		<p>
			Groups are the way that the Student Union is organized.
			You can have a group for the full Student Union, you could have one for student union members from
			each class year, you should have one for the A-Board, Judiciary, Senate, Treasury and E-Board,
			but can also have groups for your individual committees and working groups.
		</p>
		<p>
			Groups are composed of Leaders and Members.
			Leaders have the permission to edit group settings and edit the group page.
			Members do not have either of these permissions, but are still listed on the membership page for the group.
			Each Person is assigned a role (which can be as specific or as vague as desired, but needs to be defined).
			For example, in the senate, roles could be "Class of 2016 Senator" or "Vice President".
			Roles are functions of the group, not of the person.
			Therefore, the same person can have two different roles in two different groups.
		</p>
		
		<div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
		
		<h2>Create New Group</h2>
		
		<p>You can create a new group on this page. To do so, simply enter the name for the proposed group, and you will be taken to a group editor for that group.</p>
		<form method="POST" action="/group-creation">
			<input type="hidden" name="createOrDelete" value="create"/>
			<input type="text" name="groupName" placeholder="New Group Name Here"/>
			<button>Submit</button>
		</form>
		
		<h2>Delete Existing Group</h2>
		
		<p>You can also delete an existing group. Be careful, this action cannot be undone.</p>
		
		<c:forEach items="${groups}" var="group">
			<div class="group-delete-wrapper">
				${group.name}
				<form method="POST" action="/group-creation">
					<input type="hidden" name="createOrDelete" value="delete"/>
					<input type="hidden" name="groupId" value="${group.id}"/>
					<button>Delete</button>
				</form>
				<a href="/group-manager?groupId=${group.id}">Manage</a>
			</div>
		</c:forEach>
		       
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>