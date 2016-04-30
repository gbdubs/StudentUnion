<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section left-align">
			
				<h1 class="center-align light">Group Creation and Deletion</h1>
		
				<br>
		
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
					On this page, you can create or delete groups.
					Only site owners have this ability.  Be careful with it! When a group is deleted, 
					it cannot be un-deleted.
					You can have as many groups as you would like, so don't feel you need to be
					stingy with them.
				</p>
		
				<br>
				
				<h2>
					Create New Group
				</h2>
		
				<p>
					You can create a new group on this page. 
					To do so, simply enter the name for the proposed group, and you will be taken to a group editor for that group.
				</p>
		
				<form method="POST" action="/group-creation">
					<input type="hidden" name="createOrDelete" value="create"/>
					<input type="text" name="groupName" placeholder="New Group Name Here"/>
					<button class="btn bg-brandeis-blue-2">Create New Group</button>
				</form>
		
				<br>
				<br>
		
				<h2>
					Manage/Delete Existing Group
				</h2>
		
				<p>
					You can also delete an existing group. Be careful, this action cannot be undone.
				</p>
		
				<ul>
					<c:forEach items="${groups}" var="group">
						<li>
							<h5>${group.name}</h5>
							<a class="btn bg-brandeis-blue-2" href="/group-manager?groupId=${group.id}">
								Manage This Group
							</a>
							<form method="POST" action="/group-creation">
								<input type="hidden" name="createOrDelete" value="delete"/>
								<input type="hidden" name="groupId" value="${group.id}"/>
								<button class="btn bg-brandeis-yellow txt-brandeis-black">
									Delete This Group
								</button>
							</form>
						</li>
					</c:forEach>
				</ul>
		
			</div>
		</div>
	</jsp:attribute>
</t:page>