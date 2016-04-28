<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Group Manager</title>
    </head>
    
    <body>
        <p>
    		Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
    	</p>
    	<p>
    		Click here to <a href="${logoutUrl}">Log Out</a> or to <a href="/console">return to the console</a>.
    	</p>
    	<br>
    	<br>
        
        
		<h1>Student Union Website Editor | Group Settings for "${group.name}"</h1>
		
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
		<p>
			On this page, you are editing the settings for the group "${group.name}".
		</p>
        <br>
		
		<h2>Basic Properties</h2>
		<p>
			The basic properties of your group are its name (which is what it is known as),
			its PageURL, which describes where the group page is found (and where its membership page is found)
			and its description (which is publicly available).  Page URLs should start with a backslash, and should represent the piece of the URL
			that comes after the .edu, i.e. if I was describing the EBoard page at www.union.brandeis.edu/eboard, I would
			simply write in "/eboard".
		</p>
		
		<form action="/group-manager" method="POST">
			<input type="hidden" value="nameOrPageUrlOrDescription" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<h4>Name</h4>
			<input type="text" value="${group.name}" name="name"/>
			<h4>PageURL</h4>
			<input type="text" value="${group.pageUrl}" name="pageUrl"/>
			<h4>Description</h4>
			<textarea name="description">${group.description}</textarea>
			<br>
			<button>Save All Values for Basic Properties</button>
		</form>		
		
		<br>
		<br>
		
		<h2>Manage Group Leaders</h2>
		<p>
			Group leaders have the ability to add and remove group members and group leaders.
			Additionally, they can change the roles/titles within the group, and they can edit
			the public description of the group.
			You can have as many group leaders as you would like, and (unlike with owners)
			there are no downsides to having a large number of group leaders.
		</p> 	
		<h4>Current Group Leaders</h4>
		<ul>
			<c:forEach items="${group.leaders}" var="leader">
				<li>${leader}</li>
			</c:forEach>
		</ul>
		<h4>Add New Leaders</h4>
		<p>
			To add new leaders, insert their email addresses, separated by commas.
			Don't worry about putting in duplicates, we filter that out.
		</p>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="add" name="addOrRemove"/>
			<input type="hidden" value="leader" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Add Group Leaders</button>
		</form>	
		<h4>Remove Leaders</h4>
		<p>
			To remove leaders, submit their email addresses, separated by commas.
			Don't worry about duplicates/non-leaders, we filter those out.
		</p>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="remove" name="addOrRemove"/>
			<input type="hidden" value="leader" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Remove Group Leaders</button>
		</form>		
		
		<br>
		<br>
		
		<h2>Manage Group Members</h2>
		<p>
			Group members are shown on the Membership page of the group page.
			You can have as many members as you would like.
		</p>
		<h4>Current Group Members</h4>
		<ul>
			<c:forEach items="${group.members}" var="member">
				<li>${member}</li>
			</c:forEach>
		</ul>
		<h4>Add New Members</h4>
		<p>
			To add new members, insert their email addresses, separated by commas.
			Don't worry about putting in duplicates, we filter that out.
		</p>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="add" name="addOrRemove"/>
			<input type="hidden" value="member" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Add Group Members</button>
		</form>	
		<h4>Remove Members</h4>
		<p>
			To remove members, insert their email addresses, separated by commas.
			Don't worry about duplicates/non-leaders, we filter those out.
		</p>
		<form action="/group-manager" method="POST">
			<input type="hidden" value="members" name="manage"/>
			<input type="hidden" value="${group.id}" name="groupId"/>
			<input type="hidden" value="remove" name="addOrRemove"/>
			<input type="hidden" value="member" name="leaderOrMember"/>
			<input type="text" name="emails">
			<button>Remove Group Members</button>
		</form>
		
		
		<h2>Manage Roles</h2>
		<p>
			Different people play different roles in each of your groups.
			Roles might be official titles, like "President" or "Aboard Chairperson", 
			but they could also be less structured/formal, like "Guest", or "Press Liason"
		</p>
		<p>
			In this section you can assign each member of your group a title/role,
			which will show up in the membership page associated with your group.
		</p>
		<p>
			Multiple people can have the same role, and if you don't want roles assigned,
			you can leave them blank. Only Group Leaders have the power to change group members' roles.
		</p>
		
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
			<button>Update Roles</button>
		</form>
		
        <p>Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>