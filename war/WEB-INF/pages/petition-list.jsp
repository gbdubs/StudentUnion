<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	
	<div class="content card bg-brandeis-white">
		<div data-editable class="section content-tools-editable">
			<h1 class="text-center">
				Student Union Petitions
			</h1>
			<h2 class="light">
				<i>Your Voices. Heard.</i>
			</h2>
			
			<p class="left-align">
				The Student Union is always looking for better ways to hear your voice, on issues big and small.
				Petitions are our newest attempt to do just that. Though the views expressed on this page do not
				reflect the views of the Student Union, nor of Brandeis University, we want a way for students to
				easily express themselves without the formality of resolutions and referendums.
			</p>
			<p class="left-align">
				In desigining petitions, we wanted to make them accountable.  A Petition author must sign the petition
				with their email open to the public, to make sure that the individual is willing to stand behind their
				idea or comment. Additional signatories (both for and against the petition) will have to log in with a
				valid Brandeis Student Email in order to sign, and will have their email public within the Brandeis
				community, to allow like-minded people to find one another and work together.
			</p>
			<p class="left-align">
				To sign any of the petitions below, you can click on their title, which will take you to a new page
				where you can sign them (or sign against them).  To create a petition, click on the link below.
			</p>
			<a href="/petition/new" class="btn bg-brandeis-blue-2 txt-brandeis-white">Create a New Petition</a>
			
			<p class="left-align">
				For more information about the petitioning process, or to ask questions about current petitions,
				please contact leaders of the Brandeis Student Union.
			</p>
			
			<br>
			
			
			<c:forEach items="${petitions}" var="petition">
				<div class="card bg-brandeis-blue-0">
					<a href="/petition?petitionId=${petition.petitionId}">
						<c:if test="${petition.flagged || petition.deleted}">
							<h2 class="txt-brandeis-yellow pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						</c:if>
						<c:if test="${!petition.flagged && !petition.deleted}">
							<h2 class="txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						</c:if>
							${petition.name}
						</h2>
					</a>
					<div class="card margin-r-10 margin-l-10">
						<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
							Written by ${petition.creatorName} (${petition.creatorEmail}) on ${petition.createdAt}
						</h6>
						<p class="petition-body bg-brandeis-white left-align pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-t-0">${petition.description}</p>
					</div>
					<div class="right-align  pad-b-10 pad-r-10">
						<a href="/petition?petitionId=${petition.petitionId}" class="btn bg-brandeis-blue-2 txt-brandeis-white">View Details or Sign For/Against</a>
					</div>
				</div>
				<br>
				<br>
				<br>
			</c:forEach>
		</div>
	</div>
	
	</jsp:attribute>
</t:page>