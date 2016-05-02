<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	
		<h1 class="text-center">
			News
		</h1>
		<h2 class="light">
			<i>How are your representatives serving you today?</i>
		</h2>
		
		<br>
		
		<c:if test="${isAdmin}">
			<a href="/news?action=edit" class="btn btn-large bg-brandeis-blue-1">Create New Story Now</a>
		</c:if>
		
		<c:forEach items="${stories}" var="story">
			<div class="card bg-brandeis-blue-0">
				<c:if test="${isAdmin}">
					<a href="/news?action=edit&storyId=${story.storyId}">
				</c:if>
					<h2 class="txt-brandeis-white">
						${story.title}
					</h2>
				<c:if test="${isAdmin}">
					</a>
				</c:if>
				<div class="card margin-r-10 margin-l-10">
					<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						Posted by ${story.authorName} (${story.authorEmail}) on ${story.postedAt}
					</h6>
					<c:if test="${story.edited}">
						<h6 class="bg-brandeis-blue-1 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
							Last edited by ${story.lastEditorName} (${story.lastEditorEmail}) on ${story.editedAt}
						</h6>
					</c:if>
					<p class="petition-body bg-brandeis-white left-align pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-t-0">${story.body}</p>
				</div>
				<c:if test="${isAdmin}">
					<div class="right-align  pad-b-10 pad-r-10">
						<a href="/news?action=edit&storyId=${story.storyId}" class="btn bg-brandeis-blue-2 txt-brandeis-white">Edit Now</a>
					</div>
				</c:if>
			</div>
			<br>
			<br>
			<br>
		</c:forEach>

	</jsp:attribute>
</t:page>