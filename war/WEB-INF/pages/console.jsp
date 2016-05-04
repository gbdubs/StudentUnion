<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">

		<h1 class="center-align light">Student Union Website Manager</h1>
	
		<p>
			Welcome to the Student Union Website Manager!
			You have been added as an administrator to the Student Union Website. Congratulations!
			On this page are links to all of the different things that you can change on the site.
			If you need to ever get back to this site, you can find a link in the menu which is called
			'Site Manager', or, if you are on the main site, you will find a link at the bottom of the
			page (in orange) which will say 'Site Manager'.
		</p>
		<p>
			You will always need to log in with a valid account to access this page, or any of the
			tools that are provided to manage the Brandeis Student Union Website.
			You are currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			Any changes you make to this site (or any page) will be signed with your email
			address. You can change your display name/nickname <a href="/self-manager">here</a>.
		</p>

		

		<h2 class="light">People</h2>
	
		<a href="/self-manager"><h5>
			View and Manage My Personal Information
		</h5></a>
		<p>
			On the Self Manager, you can change your nickname, change your biography, change your profile
			picture, and change your graduation year. You are the only person who can modify these fields,
			so it is your responsibility to keep them up to date!
		</p>
		
		<c:if test="${isAdmin}">
			<a href="/users"><h5>
				View, Add and Remove Admins, Owners and Candidates
			</h5></a>
			<p>
				In the user manager, you can view the current site owners and administrators.  Administrators
				are site users (members of the Union) who can modify any student union page, as well as report
				or flag any petition.  Owners have even
				more extensive powers: they can create and destroy groups, they can create new pages, they can
				delete petitions permanently, and they can add and remove other owners and administrators.
				You can see the current list of owners and administrators on this page, and if you are an owner,
				you can add new admins/owners or remove them.
			</p>
			<p>
				Candidates are a slightly separate case. They only have access to edit their own personal information,
				they do not have any other powers on the site. This allows candidates to create a biography and
				photo, and publish it on the Union Website prior to an upcoming election.
				However, you can also make someone a candidate if you want to have them be included in a group, but
				do not want to give them access to edit the website (for example, if they are a non-senate committee
				chair, you can add them as a candidate).
			</p>
		</c:if>
	
		<c:if test="${isAdmin}">	
			<h2 class="light">Groups</h2>
			
			<a href="/group-manager"><h5>
				Manage Group Membership + Description</h5>
			</a>
			<p>
				Groups are the way that the Student Union organizes itself. Groups can be big or small, but simply 
				represent groups of individuals whose information needs to be collectively available.
				On this page, you can view the list of groups, and manage group membership and descriptions.
				Groups automatically produce group pages, which are essentially contact pages for the group
				members.
			</p>
			<p>
				Group leaders can add new members and change the description of their groups. 
				Make sure to have all group members and leaders added as administrators, owners
				(or candidates) so that they can create profiles for themselves. Anyone who has not yet logged in
				will not be displayed on a group contact page. 
			</p>
			<c:if test="${isOwner}">
				
				<a href="/group-creation"><h5>
					Create And Delete Groups
				</h5></a>
				<p>
					You are an owner, so you can create and delete groups. The Student Union can have as many
					groups as you think is necessary, but make sure to have group members added as administrators, owners
					(or candidates) so that they can create profiles for themselves. Anyone who has not yet logged in
					will not be displayed on a group page. 
				</p>
			</c:if>
		</c:if>
		
		
		<c:if test="${isAdmin}">	
			<h2 class="light">Pages</h2>
			
			<a href="/page-manager"><h5>
				Create, Edit, and Delete Pages
			</h5></a>
			<p>
				The heart of the matter! On this page, you will see a list of all pages on the Student Union Website,
				and a link which will enable you to easily edit each one. The editor itself is incredibly simple to use.
				Click on the little blue pen in the upper left hand corner, and begin editing.  It should be noted that
				the editor does not work on old versions of internet explorer (before IE9), and that you should probably
				not try to edit pages from a smartphone/small screen.
			</p>
			<p>
				A full record of all page edits is saved, and any time you save the page, it will be updated to show
				the time and date that it was updated along with the person who last updated it (you). For these reasons,
				don't do anything stupid. Always be professional, cautious and respectful.
			</p>
			<p>
				You can drag elements around the page (arrows will show you where they will be dropped), and you can
				simply click on an element and you will immediately be able to edit it.
				Image uploads work, but are a little bit sketchy.  Don't worry if it takes up to of 30 seconds to upload
				an image, and if the image uploader is not working, simply reload the page.
			</p>
			<p>
				When you are finished editing your page, simply press the green check mark in the upper left hand corner
				of the screen, and the page will save.  This process may take up to a minute, but fear not, it will take
				you to the new page once it is published. 
			</p>
			<p>
				Site owners can create new pages on this page, and delete existing pages.
				A full version control of the website is available 
				<a href="https://github.com/subrandeis/subrandeis.github.com">on Github</a>.
				Once a page has been deleted, you can retrieve it (but not without hassle) by looking at Github's VCS.
				Have a CS major do it for you!
			</p>
			<p>
				Any questions or consistent errors, please contact Grady Ward.
			</p>
		</c:if>

		<c:if test="${isAdmin}">	
			<h2 class="light">Petitions</h2>
			<a href="/petitions"><h5>
				Create, Flag, Remove Petitions
			</h5></a>
			<p>
				This will redirect you to the petitions page.
				Petitions are student submitted, and are posted immediately.
				For obvious reasons we needed a method to flag petitions and take them out of the public eye
				when they are misused.
				You are that safeguard.
			</p>
			<p>
				To flag a petition, click on the petition header (this will take you to the petition page),
				then, scroll to the bottom of the page and hit "flag petition". 
				To unflag a petition, follow the same steps, but instead hit "unflag petition".
				Flagged petitions will be shown in yellow.
				Site Administrators and owners can still see flagged petitions, but the general public cannot.
				Once a site Owner deletes a petition, it can no longer be seen by administrators, nor can
				it be flagged or unflagged. It is still visible to site owners alone.
			</p>
			<p>
				You can still submit and vote on petitions even if you are a site administrator or owner!
			</p>
		</c:if>
		
		<c:if test="${isAdmin}">	
			<h2 class="light">News Stories</h2>
			<a href="/news"><h5>
				Create or Delete News Stories
			</h5></a>
			<p>
				
			</p>
		</c:if>
		
			
	</jsp:attribute>
</t:page>
