<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section left-align">

				<h1 class="light center-align">List of Groups</h1>
		
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
					On this page, you can see the full list of groups, and go in to individually
					manage their settings. Depending on your role in the group, you may or may not
					have access to manage the group. If you want to be made a leader of a group
					(able to manage its membership and description) request it from one of the group
					leaders.
				</p>
				<br>
				<ul>
					<c:forEach items="${groups}" var="group">
						<li>
							<h5>
								<b>${group.name}</b>
								 - 
								<a href="/group-manager?groupId=${group.id}">
									View/Manage This Group
								</a>
							</h5>
							Description: ${group.description}
							<br>
							<br>
						</li>
					</c:forEach>
				</ul>
		

			</div>
		</div>
	</jsp:attribute>
</t:page>