<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div class="section left-align">
			
				<h1 class="light center-align">Page Mangager/Editor</h1>
				
				<br>
				
				<p>
					Pages are everything.
					Below is a list of every page on the Student Union Website.
					You can edit each one as you please, in a (if I do say so) beautiful editor by clicking on the link next to its URL.
				</p>
				<p>
					All administrators and owners have the ability to edit any and every page on the Student Union Website.
					However, all changes are LOGGED AND VERSION CONTROLLED, meaning that if you plan on doing anything shady,
					we will have a complete record of every change made to the website (which is a public log, by the way).
					Make careful changes, and don't put up anything you wouldn't want tied to you.
				</p>
				<p>
					If two people happen to edit the same page at the same time, the last one will overwrite the previous one.
				</p>
				<p>
					Site Owners (not administrators) can create a new page at the bottom of this page.
					If you want a new page, simply request (from one of the owners) to have them create a new page
					(which can happen immediately).
				</p>
				
				<br>
		
				<h2>Edit Pages</h2>
				<p>
					Click on the URL of the page you want to edit to be taken to that page to edit it.
				</p>
				
				<c:forEach items="${pages}" var="page">
					<h5><a href="/edit${page}">${page}</a></h5>
				</c:forEach>
			
				<br>
				
				<c:if test="${isOwner}">
					<br>
					<h2>Delete Pages</h2>
					<ul>
						<c:forEach items="${pages}" var="page">
							<li>
								<h5><a href="/edit${page}">${page}</a></h5>
								<c:if test="${isOwner}">
									<form method="POST" action="/page-manager">
										<input type="hidden" name="path" value="${page}"/>
										<input type="hidden" name="addOrDelete" value="delete"/>
										<button class="btn bg-brandeis-yellow txt-brandeis-black">Delete This Page</button>
									</form>
								</c:if>
								<br>
							</li>
						</c:forEach>
					</ul>
					
					<br>
				
					<h2>Create New Page</h2>
					<p>
						You can create a new page here by specifying a URL Pattern that you would like the page to go to.
						The only valid characters in a URL pattern are backslashes (/), dashes (-) and lower case letters.
						All URL patterns should start with a backslash, and should not contain file suffixes (i.e. should not end in .docx, .pdf, .html, etc).
						Some valid URL Patterns:
					</p>
					<ol>
						<li>/new/page/goes/here</li>
						<li>/treasury/allocation-rules</li>
						<li>/documents/constitution</li>
					</ol>
					<p>
						Some Invalid URL Patterns:
					</p>
					<ol>
						<li>new/page/goes/here</li>
						<li>/treasury/allocation-rules#1</li>
						<li>/documents/constitution.pdf</li>
					</ol>
					<form method="POST" action="/page-manager">
						<input type="text" name="path" placeholder="/new/page/name"/>
						<input type="hidden" name="addOrDelete" value="add"/>
						<button class="btn bg-brandeis-blue-2">Create a New Page</button>
					</form>
				</c:if>
		
			</div>
		</div>
	</jsp:attribute>
</t:page>
