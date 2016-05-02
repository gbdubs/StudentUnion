<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	
		<c:if test="${story == null}">
			<h1 class="text-center">
				Create a News Story
			</h1>
		</c:if>
		<c:if test="${story != null}">
			<h1 class="text-center">
				Edit this News Story
			</h1>
		</c:if>
		
		<form class="card bg-brandeis-blue-0" action="/news" method="POST">
			
			<input class="align-center font-30 bg-brandeis-blue-0 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0" name="storyTitle" type="text" value="${story.title}" placeholder="Title Your Story Here"/>
			
			<c:if test="${story == null}">
				<input type="hidden" name="action" value="create"/>
			</c:if>
			<c:if test="${story != null}">
				<input type="hidden" name="action" value="update"/>
				<input type="hidden" name="storyId" value="${story.storyId}"/>
			</c:if>
			<div class="card margin-r-10 margin-l-10">
				<c:if test="${story == null}">
					<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						Posted by YOUR NAME HERE (YOUREMAIL@brandeis.edu) on TODAYS DATE AND TIME
					</h6>
				</c:if>
				<c:if test="${story != null}">
					<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						Posted by ${story.authorName} (${story.authorEmail}) on ${story.postedAt}
					</h6>
				</c:if>	
				<c:if test="${story.edited}"> 
					<h6 class="bg-brandeis-blue-1 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						Last edited by ${story.lastEditorName} (${story.lastEditorEmail}) on ${story.editedAt}
					</h6>
				</c:if>
				<textarea name="storyBody" style="min-height:400px" class="petition-body bg-brandeis-white left-align pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-t-0">${story.body}</textarea>
			</div>
			<c:if test="${story != null}">
				<div class="right-align  pad-b-10 pad-r-10">
					<a href="/news" class="btn bg-brandeis-blue-1 txt-brandeis-white">Discard Your Changes</a>
					<button class="btn bg-brandeis-blue-2 txt-brandeis-white">Save Your Changes</button>
				</div>
			</c:if>
			<c:if test="${story == null}">
				<div class="right-align  pad-b-10 pad-r-10">
					<a href="/news" class="btn bg-brandeis-blue-1 txt-brandeis-white">Cancel, Don't Post</a>
					<button class="btn bg-brandeis-blue-2 txt-brandeis-white">Post New Story</button>
				</div>
			</c:if>
		</form>
		<br>
		<c:if test="${story != null}">
			<form action="/news" method="POST">
				<input type="hidden" name="action" value="delete"/>
				<input type="hidden" name="storyId" value="${story.storyId}"/>
				<button class="btn bg-brandeis-yellow txt-brandeis-black">Delete This Story</button>
			</form>
		</c:if>

	</jsp:attribute>
</t:page>