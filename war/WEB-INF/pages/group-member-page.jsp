<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
			
		<h1 class="light">
			${group.name} Members
		</h1>
		
		<h5 class="light">
			These are the members of the ${group.name}.
			To see what they are up to, check out their 
			<a href="${group.pageUrl}">page</a>.
		</h5>
		
		<c:forEach var="person" items="${people}">
			<br>
			<div class="profile-box">
				<div class="profile-image bg-brandeis-blue-1">
					<img src="${person.imageUrl}">
				</div>
				<div class="profile-name bg-brandeis-blue-0 txt-brandeis-white">
					<h5><b>${person.nickname}</b>
					 - 
					<span>${roles[person.email]}</span>
					 - 
					<i class="light">Class of ${person.classYear}</i></h5>
				</div>
				<div class="profile-email bg-brandeis-blue-2 txt-brandeis-white">
					<h6 class="light">${person.email}</h6>
				</div>
				<p class="profile-job-description">
					asdfasdfasdfasdfas
				</p>
				<p class="personal-statement">
					${person.biography}
				</p>
			</div>
			<br>
		</c:forEach>
		
		<br>
		<br>
				
	</jsp:attribute>
</t:page>
