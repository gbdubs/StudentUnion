<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	<div class="center-align">
	
		<h1 class="light">
			Student Union Website Editor
		</h1>
		
		<h5 class="light">
			To access the website editing tools, login with an approved Google/Brandeis email address
		</h5>
		
		<br>
		<br>
		
		
			<a href="${loginUrl}" class="btn btn-large bg-brandeis-blue-1">Login Here</a></p>
		
		<br>

		<h5 class="light">
			You will only be able to login if you have been made an owner, administrator or candidate by a current site owner.
		</h5>
		
		<br>
		<br>
		
		<h5>
			Current Owners:
		</h5>
		
		<ul>
			<c:forEach items="${owners}" var="owner">
				<li>
					<b>${owner.nickname}</b> - 
					<a href="mailto:${owner.email}">${owner.email}</a>
				</li>
			</c:forEach>
		</ul>
		
		<br>
		
		<h5>
			Current Administrators:
		</h5>
		
		<ul>
			<c:forEach items="${admins}" var="admin">
				<li>
					<b>${admin.nickname}</b> - 
					<a href="mailto:${admin.email}">${admin.email}</a>
				</li>
			</c:forEach>
		</ul>
		
		<br>
		
		<h5>
			Current Candidates:
		</h5>
		
		<ul>
			<c:forEach items="${candidates}" var="candidate">
				<li>
					<b>${candidate.nickname}</b> - 
					<a href="mailto:${candidate.email}">${candidate.email}</a>
				</li>
			</c:forEach>
		</ul>
		
		<br>
		<h5 class="light">	
			If you are looking for the regular login (for petitions, or non-Union students) click <a href="/login">here</a>.
		</h5>
	</div>		
	</jsp:attribute>
</t:page>