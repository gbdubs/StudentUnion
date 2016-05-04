<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	<div class="center-align">
		<h1 class="light">
			Student Union Website Login
		</h1>
		
		<h5 class="light">
			To access the petitions site, please login with your Brandeis email address
		</h5>
		
		<br>
		
		<p class="left-align">
			The Student Union Website uses open authentication provided by Google
			(which is the company that Brandeis uses to power your email).
			When you click the link below, you will be taken to a Google page to
			login with your credentials there.
			Make sure to use your email address that ends in @brandeis.edu.
			The Student Union is only able to access your email and your nickname, and will retain both on our servers.
			All other elements of your identity remain with Google.
		</p>
		
		<br>
		<a href="${loginUrl}" class="btn btn-large bg-brandeis-blue-1">Login Here</a></p>
		<br>

		<p class="left-align">
			We require a login to vote on petitions or to author them. You are still
			free to use any other features of this site if you opt not to log in.
		</p>

		<p class="left-align">
			If you have questions about open authentication, please feel free to
			contact a member of the Student Union, who can direct you to the right
			person to answer your question.
		</p>
		
		<p class="left-align">
			If you are looking for the website manager tools (for administrators and owners
			of the site, and members of the Student Union), please click <a href="/login-admin">here</a>.
		</p>

	</div>
	</jsp:attribute>
</t:page>
