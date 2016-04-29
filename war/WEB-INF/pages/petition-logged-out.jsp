<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">

	<div class="content card bg-brandeis-white">
		<div data-editable class="section content-tools-editable">
			
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
				You are not logged in.
				In order to ensure accountability, if you want to sign this petition you will need to log in with a
				Brandeis Student Gmail Account. You can login <a href="${loginUrl}">here</a>.
				As you are not currently logged in as a Brandeis student, you cannot see who has signed each petition,
				that information is limited to Brandeisians.
				If you need to log out of another gmail account to access this page, you can do so here
				<a href="${logoutUrl}">here</a>.
			</p>
			<p class="left-align">
				For more information about the petitioning process, please visit the <a href="/petitions">petitions page</a>
				or contact a member of the Brandeis Student Union.
			</p>
			
			<br>
			
			<div class="card bg-brandeis-blue-0">
				<h2 class="txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
					${petition.name}
				</h2>
				<div class="card margin-r-10 margin-l-10">
					<h6 class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
						Written by ${petition.creatorName} (${petition.creatorEmail}) on ${petition.createdAt}
					</h6>
					<p class="petition-body bg-brandeis-white left-align pad-t-10 pad-b-10 pad-l-10 pad-r-10 margin-t-0">${petition.description}</p>
				</div>
				<div class="right-align">
					<span class="card txt-brandeis-white bg-brandeis-blue-1 margin-r-10 margin-b-10 pad-t-10 pad-b-10 pad-l-15 pad-r-15">
						${peopleForNum} For
					</span>
					
					<span class="card txt-brandeis-white bg-brandeis-blue-1 margin-r-10 margin-b-10 pad-t-10 pad-b-10 pad-l-15 pad-r-15">
						${peopleAgainstNum} Against
					</span>
				</div>
				<div class="right-align pad-t-15 pad-b-10 pad-r-10">
					<a href="${loginUrl}" class="btn bg-brandeis-blue-2 txt-brandeis-white">Login with a Brandeis Account To Sign For or Against</a>
				</div>
				
				
				
			</div>
		</div>
	</div>
	
	
	</jsp:attribute>
</t:page>