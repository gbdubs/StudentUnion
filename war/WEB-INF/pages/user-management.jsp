<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		

		<h1 class="center-align light">User Manager</h1>


		<h2> Owners</h2>
		<p>
			Owners are people who are able to do ANYTHING on the site.
			This includes adding and removing administrators and owners, and overseeing the forums.
			All owners are automatically made administrators, nobody can be an owner and not an administrator.
			Ownership should probably not be given to more than a dozen people, just to make sure that the organization
			maintains a strong sense of order and control.
			Note that most site functions (i.e. changing site pages) can be done by administrators, so only a few
			people need to be given ownership permissions.
		</p>
		<p>
			Worse comes to worse, if you cannot get an existing owner to add you (or perhaps you deleted yourself as an owner)
			reach out to Grady Ward, he can add you as an owner.
		</p>
		<p>
			In this section, site owners can remove other site owners (or themselves) by clicking on the button next to each.
			This action cannot be undone (though one could add the email as an owner again).
		</p>
		<ul class="no-bullet-list">
			<c:forEach items="${owners}" var="owner">
				<li>
					<h5>
						<b>${owner.nickname}</b> - 
						<a href="mailto:${owner.email}">${owner.email}</a>
					</h5>
					<c:if test="${isOwner}">
						<form action="/users" method="POST">
							<input type="hidden" name="emails" value="${owner.email}">
							<input type="hidden" name="addOrRemove" value="remove">
							<input type="hidden" name="adminOrOwnerOrCandidate" value="owner">
							<button class="btn bg-brandeis-yellow txt-brandeis-black">Remove As Owner (But Not as Administrator)</button>
						</form>
					</c:if>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${isOwner}">
			<br>
			<br>
			<h5>Add Owner</h5>
			<p>
				Add the email of the new Owner (or multiple emails, separated by commas) to the field below, and click submit.
				Note that changes can take up to a minute to be reflected on this page.
				All emails <b>must end in @brandeis.edu or @gmail.com</b>;
				any which do not fit this criteria will just be ignored.
			</p>
			<form action="/users" method="POST">
				<input type="text" name="emails" placeholder="emailaddress@brandeis.edu">
				<input type="hidden" name="addOrRemove" value="add">
				<input type="hidden" name="adminOrOwnerOrCandidate" value="owner">
				<button class="btn bg-brandeis-blue-2">Add Owner By Email</button>
			</form>
		</c:if>
		
		<br>
		
		<h2>Administrators</h2>
		<p> 
			Administrators are people who can edit site pages, and moderate the forums.
			Any administrator can modify any page on the site. However, remember that
			<b>any changes that you make are documented as yours</b> meaning, that if you
			post something that shouldn't be up, there WILL be a record of that.
			Administrators can be added by Owners. You can contact a site owner to request
			to be added.
		</p>
		<p>
			In this section, site owners can remove administrators by clicking on the button next to each.
			This action cannot be undone (though one could add the email as an administrator again).
		</p>
		<ul class="no-bullet-list">
			<c:forEach items="${admins}" var="admin">
				<li>
					<h5>
						<b>${admin.nickname}</b> - 
						<a href="mailto:${admin.email}">${admin.email}</a>
					</h5>
					<c:if test="${isOwner}">
						<form action="/users" method="POST">
							<input type="hidden" name="emails" value="${admin.email}">
							<input type="hidden" name="addOrRemove" value="remove">
							<input type="hidden" name="adminOrOwnerOrCandidate" value="admin">
							<button class="btn bg-brandeis-yellow txt-brandeis-black">Remove As Administrator</button>
						</form>
					</c:if>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${isOwner}">
			<br>
			<br>
			<h5>Add Administrator</h5>
			<p>
				Add the email of the new administrator(s) (or multiple emails, separated by commas) to the field below, and click submit.
				Note that changes can take up to a minute to be reflected on this page.
				All emails <b>must end in @brandeis.edu or @gmail.com</b>;
				any which do not fit this criteria will just be ignored.
			</p>
			<form action="/users" method="POST">
				<input type="text" name="emails" placeholder="emailaddress@brandeis.edu">
				<input type="hidden" name="addOrRemove" value="add">
				<input type="hidden" name="adminOrOwnerOrCandidate" value="admin">
				<button class="btn bg-brandeis-blue-2">Add Administrator By Email</button>
			</form>
		</c:if>
		
		
		<br>
		<br>
		<br>
		
		<h2>Candidates</h2>
		<p> 
			Candidates are potential Union Members. They are given the chance to create a 
			profile (with a name, class year, biography and photo), and can be displayed 
			on the elections page. Candidates can only be created or deleted by site owners.
			Candidates cannot edit Union Pages, they only have access to their own profile.
		</p>
		<p>
			In this section, site owners can remove candidates by clicking on the button next to each.
			This action cannot be undone (though one could add the email as a candidate again).
		</p>
		<ul class="no-bullet-list">
			<c:forEach items="${candidates}" var="candidate">
				<li>
					<h5>
						<b>${candidate.nickname}</b> - 
						<a href="mailto:${candidate.email}">${candidate.email}</a>
					</h5>
					<c:if test="${isOwner}">
						<form action="/users" method="POST">
							<input type="hidden" name="emails" value="${candidate.email}">
							<input type="hidden" name="addOrRemove" value="remove">
							<input type="hidden" name="adminOrOwnerOrCandidate" value="candidate">
							<button class="btn bg-brandeis-yellow txt-brandeis-black">Remove as Candidate</button>
						</form>
					</c:if>
				</li>
			</c:forEach>
		</ul>
		<c:if test="${isOwner}">
			<br>
			<br>
			<h5>Add Candidate</h5>
			<p>
				Add the email of the new candidates(s) (or multiple emails, separated by commas) to the field below, and click submit.
				Note that changes can take up to a minute to be reflected on this page.
				All emails <b>must end in @brandeis.edu or @gmail.com</b>;
				any which do not fit this criteria will just be ignored.
			</p>
			<form action="/users" method="POST">
				<input type="text" name="emails" placeholder="emailaddress@brandeis.edu">
				<input type="hidden" name="addOrRemove" value="add">
				<input type="hidden" name="adminOrOwnerOrCandidate" value="candidate">
				<button class="btn bg-brandeis-blue-2">Add Candidate By Email</button>
			</form>
		</c:if>
		<br>
		<br>
		<br>
		
	</jsp:attribute>
</t:page>
