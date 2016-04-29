<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head id="head">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
	<title>Brandeis University Student Union</title>
	<link href="../static/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
	<link href="../static/css/style.css" type="text/css" rel="stylesheet" media="screen,projection">
	<link href="../static/css/content-tools-properties.css" type="text/css" rel="stylesheet">
</head>
<body id="body" class="bg-brandeis-grey" data-name="menu">
	<ul id="nav-mobile" class="side-nav" style="left: -250px;">
		<li class="bg-brandeis-blue-0">
			<a class="menu-header" href="../">
				<img class="menu-seal" src="../static/img/edited-seal.png">
				<h6 class="first txt-brandeis-white">Brandeis University</h6>
				<h6 class="txt-brandeis-white">Student Union</h6>
			</a>
		</li>
		<li><a href="../">Home</a></li>
		<li><a href="../e-board">Executive Board</a></li>
		<li><a href="../senate">Senate</a></li>
		<li><a href="../a-board">Allocations Board</a></li>
		<li><a href="../judiciary">Judiciary</a></li>
		<li><a href="../treasury">Treasury</a></li>
		<li><a href="../documents">Constitution</a></li>
		<li><a href="../documents">Bylaws</a></li>
		<li><a href="../committees">Committees</a></li>
		<li><a href="../news">News</a></li>
		<li><a href="../elections">Elections</a></li>
		<li><a href="../contact">Contact</a></li>
		<li><a href="../pages">Pages</a></li>
	</ul>
	<nav class="bg-brandeis-blue-0 canopy" role="navigation">
		<div class="menu-ribbon"></div>
		<div class="nav-wrapper container">
			<a id="logo-container" href="../" class="brand-logo">
			<img class="menu-seal" src="../static/img/edited-seal.png">
			<span>Brandeis <span class="droppable">University </span>Student Union</span>
			</a>
			<ul class="menu hide-on-med-and-down">
				<li><a href="../">Home</a></li>
				<li>
					<a class="dropdown-button" href="#!" data-activates="about-dropdown">Organization<i class="mdi-navigation-arrow-drop-down right"></i></a>
					<ul id="about-dropdown" class="dropdown-content txt-brandeis-blue-0">
						<li><a href="../e-board">Executive Board</a></li>
						<li><a href="../senate">The Senate</a></li>
						<li><a href="../a-board">Allocations Board</a></li>
						<li><a href="../judiciary">Judiciary</a></li>
						<li><a href="../treasury">Treasury</a></li>
						<li class="divider"></li>
						<li><a href="../documents">Constitution</a></li>
						<li><a href="../documents">Bylaws</a></li>
					</ul>
				</li>
				<li><a href="../pages">Pages</a></li>
				<li><a href="../news">News</a></li>
				<li><a href="../contact">Contact</a></li>
				<li><a href="../elections">Elections</a></li>
			</ul>
			<a href="#" data-activates="nav-mobile" class="button-collapse"><i class="mdi-navigation-menu"></i></a>
		</div>
	</nav>
	
	<!-- CONTENT BELOW HERE --> 
	
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
						<h2 class="txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
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
	
	
	<!-- CONTENT ABOVE HERE -->    
	<footer class="page-footer footer-canopy bg-brandeis-blue-0">
		<div class="container">
			<div class="row">
				<div class="col m6 s12">
					<h5 class="white-text">The Union Office</h5>
					<p class="light grey-text text-lighten-4">
						Feel free to visit us on the third floor of the Shapiro Campus Center! 
						Our office is a great place to come study, ask questions, and talk about your vision for Brandeis. 
						Check out the <a class="blue-text text-lighten-3" href="../office-hours">full list of office hours</a> to see when you can drop by!
					</p>
				</div>
				<div class="col m6 s12">
					<h5 class="white-text">Stay Connected</h5>
					<ul class="light">
						<li><a class="blue-text text-lighten-3" href="http://www.facebook.com/deisunion">Student Union Facebook</a></li>
						<li><a class="blue-text text-lighten-3" href="http://www.twitter.com">Student Union Twitter</a></li>
						<li><a class="blue-text text-lighten-3" href="https://www.brandeis.edu/now/">Brandeis Now</a></li>
						<li><a class="blue-text text-lighten-3" href="http://www.thejustice.org">The Justice</a></li>
						<li><a class="blue-text text-lighten-3" href="http://www.brandeishoot.com">The Hoot</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="footer-copyright bg-brandeis-blue-1 txt-brandeis-white">
			<div class="container">
				Made by Grady Ward '16, using 
				<a class="blue-text text-lighten-3" href="http://materializecss.com">MaterializeCSS</a>, 
				<a class="blue-text text-lighten-3" href="http://getcontenttools.com">Content Tools</a>, 
				<a class="blue-text text-lighten-3" href="https://pages.github.com">GitHub Pages</a> and 
				<a class="blue-text text-lighten-3" href="https://cloud.google.com/appengine/">Google App Engine</a>. 
				<a class="blue-text text-lighten-3" href="mailto:grady.b.ward@gmail.com">Comments?</a>
			</div>
		</div>
	</footer>
	<script src="../static/js/jquery.min.js"></script>
	<script src="../static/js/materialize.js"></script>
	<script src="../static/js/init.js"></script>
</body>
</html>