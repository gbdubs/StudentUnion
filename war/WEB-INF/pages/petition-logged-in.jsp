<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">

		<p class="left-align">
			This is a student submitted petition, a way that the Student Union can engage its constituents.
			To learn more about the petition process, or to submit your own, check out our 
			<a href="/petitions">petitions page</a>.
		</p>
		<p class="left-align">
			The views expressed on this page do not reflect the views of the Student Union, nor of Brandeis University.
			This is simply meant to be a forum through which Students can converse with each other and bring
			opinions, ideas and concerns forward to the attention of the Student Union.
		</p>
		<p class="left-align">
			You are currently logged in as ${person.nickname} (${person.email}).
			If you choose to sign the petition for or against, your email will be recorded, and will be public
			to other Brandeis Students only. As a Brandeis student, you can see who has signed each petition,
			but that information is limited to Brandeisians. If you abstain from the vote, your email will not
			be recorded.
			You can opt to logout <a href="${logoutUrl}">here</a>, but some of the features of this page will be
			disabled.
		</p>
		<br>
		
		<c:if test="${isFlagged || isDeleted}">
			<div class="card bg-brandeis-yellow">
		</c:if>
		
		<c:if test="${!isFlagged && !isDeleted}">
			<div class="card bg-brandeis-blue-0">
		</c:if>
			<c:if test="${isFlagged && !isDeleted}">
				<h2 class="txt-brandeis-black pad-t-10 pad-b-10 margin-t-0 margin-b-0">
					This Petition Has Been Flagged
				</h2>
			</c:if>
			<c:if test="${isDeleted}">
				<h2 class="txt-brandeis-black pad-t-10 pad-l-10 pad-b-10 margin-t-0 margin-b-0">
					This Petition Has Been Deleted
				</h2>
			</c:if>
			
			<h2 class="txt-brandeis-white pad-t-10 pad-l-10 pad-b-10 margin-t-0 margin-b-0">
				${petition.name}
			</h2>
			<div class="card margin-r-10 margin-l-10">
				<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-l-10 pad-b-10 margin-t-0 margin-b-0">
					Written by ${petition.creatorName} (${petition.creatorEmail}) on ${petition.createdAt}
				</h6>
				<p class="petition-body bg-brandeis-white left-align pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-t-0">${petition.description}</p>
			</div>
			<div class="right-align">
				<button class="btn txt-brandeis-white bg-brandeis-blue-1 margin-r-10 margin-b-10" id="see-emails-toggle-for">
					${peopleForNum} For
				</button>
				<div id="petition-list-of-names-for" class="card no-height left-align bg-brandeis-blue-1 txt-brandeis-white pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-l-10 margin-r-10 margin-b-20">
					<c:forEach items="${peopleFor}" var="email">
						<span>${email}</span>,  
					</c:forEach>
				</div>
				
				<button class="btn txt-brandeis-white bg-brandeis-blue-1 margin-r-10 margin-b-10" id="see-emails-toggle-against">
					${peopleAgainstNum} Against
				</button>
				<div id="petition-list-of-names-against" class="card no-height left-align bg-brandeis-blue-1 txt-brandeis-white pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-l-10 margin-r-10 margin-b-20">
					<c:forEach items="${peopleAgainst}" var="email">
						<span>${email}</span>,  
					</c:forEach>
				</div>
			</div>
			
			<div class="txt-brandeis-white left-align pad-l-10 pad-r-10">
				<p>
					If you choose to sign the petition for or against, your email address will be publically visible to
					other Brandeis students on this page. Your email will not be made public if you are neutral on the issue (which is selected by default).
				</p>
				<p>
					Please allow up to five minutes for your vote to be tallied and recorded along with the other email
					addresses. You can change your vote at any point in time, but there exists a private log of who has voted
					for what and at what point in time. 					
				</p>
				<p>
					Remember that supporting hateful messages (even by petition) is deplorable.
					Remain respectful, even in disagreement, and if you have concerns about something on this forum,
					contact a member of the Student Union to discuss your concern.
				</p>
			</div>
			
			<div class="pad-b-15 pad-t-15 center-align petition-vote-btns">
				<c:if test="${!personFor}">
					<form method="POST" action="/petition">
						<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
						<input name="action" value="vote" type="hidden"/>
						<input name="forOrAgainstOrNeutral" value="for" type="hidden"/>
						<button class="btn bg-brandeis-blue-2 margin-r-10 txt-brandeis-white">
							Vote For
						</button>
					</form>
				</c:if>
				<c:if test="${personFor}">
					<form method="POST" action="/petition">
						<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
						<input name="action" value="vote" type="hidden"/>
						<input name="forOrAgainstOrNeutral" value="neutral" type="hidden"/>
						<button class="btn bg-brandeis-yellow margin-r-10 txt-brandeis-black">
							Vote For
						</button>
					</form>
				</c:if>
				
				<c:if test="${!personAbstain}">
					<form method="POST" action="/petition">
						<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
						<input name="action" value="vote" type="hidden"/>
						<input name="forOrAgainstOrNeutral" value="neutral" type="hidden"/>
						<button class="btn bg-brandeis-blue-2 margin-r-10 txt-brandeis-white">
							Neutral
						</button>
					</form>
				</c:if>
				<c:if test="${personAbstain}">
					<button class="btn bg-brandeis-yellow margin-r-10 txt-brandeis-black">
						Neutral
					</button>
				</c:if>
					
				<c:if test="${!personAgainst}">
					<form method="POST" action="/petition">
						<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
						<input name="action" value="vote" type="hidden"/>
						<input name="forOrAgainstOrNeutral" value="against" type="hidden"/>
						<button class="btn bg-brandeis-blue-2 margin-r-10 txt-brandeis-white">
							Vote Against
						</button>
					</form>
				</c:if>
				<c:if test="${personAgainst}">
					<form method="POST" action="/petition">
						<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
						<input name="action" value="vote" type="hidden"/>
						<input name="forOrAgainstOrNeutral" value="neutral" type="hidden"/>
						<button class="btn bg-brandeis-yellow margin-r-10 txt-brandeis-black">
							Vote Against
						</button>
					</form>
				</c:if>
			</div>
		</div>
		<c:if test="${!isFlagged && isAdministrator && !isDeleted}">
			<form method="POST" action="/petition">
				<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
				<input name="action" value="flag" type="hidden"/>
				<button class="btn bg-brandeis-yellow margin-r-10 margin-l-10 margin-t-10 txt-brandeis-black">
					Flag Petition
				</button>
			</form>
		</c:if>
		<c:if test="${isFlagged && isAdministrator && !isDeleted}">
			<form method="POST" action="/petition">
				<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
				<input name="action" value="unflag" type="hidden"/>
				<button class="btn bg-brandeis-yellow margin-r-10 margin-l-10 margin-t-10 txt-brandeis-black">
					Unflag Petition
				</button>
			</form>
		</c:if>
		<c:if test="${!isDeleted && isOwner}">
			<form method="POST" action="/petition">
				<input name="petitionId" value="${petition.petitionId}" type="hidden"/>
				<input name="action" value="delete" type="hidden"/>
				<button class="btn bg-brandeis-yellow margin-r-10 margin-l-10 margin-t-10 txt-brandeis-black">
					Delete Petition
				</button>
			</form>
		</c:if>

	</jsp:attribute>

	<jsp:attribute name="js">
		<script>
			$(function(){
				var petitionFor = $("#petition-list-of-names-for");
				var petitionAgainst = $("#petition-list-of-names-against");
		
				$("#see-emails-toggle-for").click(function(){
					$(petitionFor).toggleClass("no-height");
				});
			
				$("#see-emails-toggle-against").click(function(){
					$(petitionAgainst).toggleClass("no-height");
				});
			});
		</script>	
	</jsp:attribute>
</t:page>