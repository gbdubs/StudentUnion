<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website | User Manager</title>
    </head>
    <body>
    	Currently logged in as ${currentUser.nickname} (${currentUser.email}). <a href="${logoutUrl}">Log Out</a>
    	<div>
			<h1> Owners</h1>
			<ul>
				<c:forEach items="${owners}" var="owner">
					<li>
						<b>${owner.nickname}</b> - 
						<a href="mailto:${owner.email}">${owner.email}</a>
						<c:if test="${isOwner}">
							<form action="/users" method="POST">
								<input type="hidden" name="email" value="${owner.email}">
								<input type="hidden" name="addOrRemove" value="remove">
								<input type="hidden" name="adminOrOwnerOrCandidate" value="owner">
								<button>Delete</button>
							</form>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${isOwner}">
				<h3>Add Owner</h3>
				<h5>Note that changes can take up to a minute to be reflected on this page. If prompted, do not send the form again, rather, reload the page without using the back buttons.</h5>
				<form action="/users" method="POST">
					<input type="text" name="email" placeholder="Email Address Here">
					<input type="hidden" name="addOrRemove" value="add">
					<input type="hidden" name="adminOrOwnerOrCandidate" value="owner">
					<button>Submit</button>
				</form>
			</c:if>
		</div>
		<div>
			<h1>Administrators</h1>
			<ul>
				<c:forEach items="${admins}" var="admin">
					<li>
						<b>${admin.nickname}</b> - 
						<a href="mailto:${admin.email}">${admin.email}</a>
						<c:if test="${isOwner}">
							<form action="/users" method="POST">
								<input type="hidden" name="email" value="${admin.email}">
								<input type="hidden" name="addOrRemove" value="remove">
								<input type="hidden" name="adminOrOwnerOrCandidate" value="admin">
								<button>Delete</button>
							</form>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${isOwner}">
				<h3>Add Admin</h3>
				<h5>Note that changes can take up to a minute to be reflected on this page. If prompted, do not send the form again, rather, reload the page without using the back buttons.</h5>
				<form action="/users" method="POST">
					<input type="text" name="email" placeholder="Email Address Here">
					<input type="hidden" name="addOrRemove" value="add">
					<input type="hidden" name="adminOrOwnerOrCandidate" value="admin">
					<button>Submit</button>
				</form>
			</c:if>
		</div>
		<div>
			<h1>Candidates</h1>
			<ul>
				<c:forEach items="${candidates}" var="candidate">
					<li>
						<b>${candidate.nickname}</b> - 
						<a href="mailto:${candidate.email}">${candidate.email}</a>
						<c:if test="${isOwner}">
							<form action="/users" method="POST">
								<input type="hidden" name="email" value="${candidate.email}">
								<input type="hidden" name="addOrRemove" value="remove">
								<input type="hidden" name="adminOrOwnerOrCandidate" value="candidate">
								<button>Delete</button>
							</form>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			<c:if test="${isOwner}">
				<h3>Add Candidate</h3>
				<h5>Note that changes can take up to a minute to be reflected on this page. If prompted, do not send the form again, rather, reload the page without using the back buttons.</h5>
				<form action="/users" method="POST">
					<input type="text" name="email" placeholder="Email Address Here">
					<input type="hidden" name="addOrRemove" value="add">
					<input type="hidden" name="adminOrOwnerOrCandidate" value="candidate">
					<button>Submit</button>
				</form>
			</c:if>
		</div>
	</body>
</html>