<%@tag description="Student Union Generic Page" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="content" fragment="true" %>
<%@attribute name="js" fragment="true" %>
<%@attribute name="css" fragment="true" %>
<%@taglib uri="loginutils" prefix="f" %>

<c:if test="${production == null}">
	<c:set var="production" value="false"/>
</c:if>

<c:set var="resourcelocation" value="http://union.brandeis.io"/>
<c:set var="staticpagelocation" value="http://union.brandeis.io"/>
<c:if test="${production}">
	<c:set var="webapplocation" value="http://admin.brandeis.io"/>
</c:if>
<c:if test="${!production}">
 	<c:set var="resourcelocation" value=""/>
	<c:set var="webapplocation" value=""/>
</c:if>

<c:set var="afterLogoutAdmin" value="/login-admin" />
<c:set var="afterLogoutRegular" value="/petitions" />
<c:set var="afterLogin" value="/petitions" />

<html lang="en">
    <head id="head">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
		<title>Brandeis University Student Union</title>
		<link href="${resourceLocation}/static/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
		<link href="${resourceLocation}/static/css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
		<link href="${resourceLocation}/static/css/content-tools-properties.css" type="text/css" rel="stylesheet">
		<jsp:invoke fragment="css"/>
    </head>
    <body id="body" class="bg-brandeis-grey" data-name="menu">
		<ul class="no-bullet-list dropdown-content txt-brandeis-blue-0" id="about-dropdown" >
			<li><a href="${staticpagelocation}/e-board">Executive Board</a></li>
			<li><a href="${staticpagelocation}/senate">The Senate</a></li>
			<li><a href="${staticpagelocation}/a-board">Allocations Board</a></li>
			<li><a href="${staticpagelocation}/judiciary">Judiciary</a></li>
			<li><a href="${staticpagelocation}/treasury">Treasury</a></li>
			<li class="divider"></li>
			<li><a href="${staticpagelocation}/groups">Committees + Groups</a>
			<li class="divider"></li>
			<li><a href="${staticpagelocation}/documents">Constitution</a></li>
			<li><a href="${staticpagelocation}/documents">Bylaws</a></li>
		</ul>
		<ul class="no-bullet-list side-nav" id="nav-mobile">
			<li class="bg-brandeis-blue-0">
				<a class="menu-header" href="${staticpagelocation}/">
					<img class="menu-seal" src="${resourceLocation}/static/img/edited-seal.png"/>
					<h6 class="first txt-brandeis-white">Brandeis University</h6>
					<h6 class="txt-brandeis-white">Student Union</h6>
				</a>
			</li>
			<li><a href="${staticpagelocation}/">Home</a></li>
			<li class="divider"></li>
			<li><a href="${staticpagelocation}/e-board">Executive Board</a></li>
			<li><a href="${staticpagelocation}/senate">Senate</a></li>
			<li><a href="${staticpagelocation}/a-board">Allocations Board</a></li>
			<li><a href="${staticpagelocation}/judiciary">Judiciary</a></li>
			<li><a href="${staticpagelocation}/treasury">Treasury</a></li>
			<li class="divider"></li>
			<li><a href="${staticpagelocation}/groups">Committees + Groups</a>
			<li class="divider"></li>
			<c:if test="${production || !f:loggedIn() || !f:admin()}">
				<li><a href="${staticpagelocation}/news">News</a></li>
			</c:if>
			<c:if test="${!production && f:loggedIn() && f:admin()}">
				<li><a href="${webapplocation}/news">News</a></li>
			</c:if>
			<li><a href="${webapplocation}/petitions">Petitions</a></li>
			<li><a href="${staticpagelocation}/elections">Elections</a></li>
			<li><a href="${staticpagelocation}/directory">Directory</a></li>
			<li class="divider"></li>
			<li><a href="${staticpagelocation}/documents">Constitution</a></li>
			<li><a href="${staticpagelocation}/documents">Bylaws</a></li>
			<li class="divider"></li>
			<c:if test="${!production}"> 
				<c:if test="${f:loggedIn()}">
					<c:if test="${f:admin()}">
						<li><a href="${f:logoutUrl(afterLogoutAdmin)}">Log Out</a></li>
					</c:if>
					<c:if test="${! f:admin()}">
						<li><a href="${f:logoutUrl(afterLogoutRegular)}">Log Out</a></li>
					</c:if>
				</c:if>
				<c:if test="${! f:loggedIn()}">
					<li><a href="${f:loginUrl(afterLogin)}">Log In</a></li>
				</c:if>
				<c:if test="${f:admin()}">
					<li class="txt-brandeis-blue-1"><a href="/console">Site Manager</a></li>
				</c:if>
			</c:if>
		</ul>
		<nav class="bg-brandeis-blue-0 canopy" role="navigation">
			<div class="menu-ribbon"></div>
			<div class="nav-wrapper container">
				<a id="logo-container" href="${staticpagelocation}/" class="brand-logo">
					<img class="menu-seal" src="${resourceLocation}/static/img/edited-seal.png"/>
					<span>Brandeis <span class="droppable">University </span>Student Union</span>
				</a>
				<ul class="no-bullet-list menu hide-on-med-and-down">
					<li><a href="${staticpagelocation}/">Home</a></li>
					<li><a class="dropdown-button" href="#!" data-activates="about-dropdown">Organization<i class="mdi-navigation-arrow-drop-down right"></i></a></li>
					<li><a href="${staticpagelocation}/directory">Directory</a></li>
					<c:if test="${production || !f:loggedIn() || !f:admin()}">
						<li><a href="${staticpagelocation}/news">News</a></li>
					</c:if>
					<c:if test="${!production && f:loggedIn() && f:admin()}">
						<li><a href="${webapplocation}/news">News</a></li>
					</c:if>
					<li><a href="${webapplocation}/petitions">Petitions</a></li>
					<li><a href="${staticpagelocation}/elections">Elections</a></li>
					<c:if test="${!production}"> 
						<c:if test="${f:loggedIn()}">
							<c:if test="${f:admin()}">
								<li><a href="${f:logoutUrl(afterLogoutAdmin)}">Log Out</a></li>
							</c:if>
							<c:if test="${! f:admin()}">
								<li><a href="${f:logoutUrl(afterLogoutRegular)}">Log Out</a></li>
							</c:if>
						</c:if>
						<c:if test="${! f:loggedIn()}">
							<li><a href="${f:loginUrl(afterLogin)}">Log In</a></li>
						</c:if>
						<c:if test="${f:admin()}">
							<li class="bg-brandeis-blue-1 txt-brandeis-white"><a href="/console">Site Manager</a></li>
						</c:if>
					</c:if>
				</ul>
				<a href="#" data-activates="nav-mobile" class="button-collapse"><i class="mdi-navigation-menu"></i></a>
			</div>
		</nav>
		
		<div class="content card bg-brandeis-white">
			<div data-editable class="section content-tools-editable left-align" id="content-inner-wrapper">
			
			<!-- CONTENT BELOW HERE --> 
			
		
			<jsp:invoke fragment="content" />
		
		
			<!-- CONTENT ABOVE HERE -->    
			
			</div>
		</div>
		
		<footer class="page-footer footer-canopy bg-brandeis-blue-0">
			<div class="container">
				<div class="row">
					<div class="col m6 s12">
						<h5 class="white-text">The Union Office</h5>
						<p class="light grey-text text-lighten-4">
						Feel free to visit us on the third floor of the Shapiro Campus Center! 
						Our office is a great place to come study, ask questions, and talk about your vision for Brandeis. 
						Check out the <a class="blue-text text-lighten-3" href="${staticpagelocation}/office-hours">full list of office hours</a> to see when you can drop by!
					</p>
					</div>
					<div class="col m6 s12">
						<h5 class="white-text">Stay Connected</h5>
						<ul class="no-bullet-list light">
							<li><a class="blue-text text-lighten-3" href="http://www.facebook.com/deisunion">Student Union Facebook</a></li>
							<li><a class="blue-text text-lighten-3" href="http://www.twitter.com">Student Union Twitter</a></li>
							<li><a class="blue-text text-lighten-3" href="https://www.brandeis.edu/now/">Brandeis Now</a></li>
							<li><a class="blue-text text-lighten-3" href="http://www.thejustice.org">The Justice</a></li>
							<li><a class="blue-text text-lighten-3" href="http://www.brandeishoot.com">The Hoot</a></li>
						</ul>
					</div>
				</div>
			</div>
			<c:if test="${production}">
				<div class="footer-copyright bg-brandeis-blue-2 txt-brandeis-white">
					<div id="last-edited" class="container">
						This page was last edited on <span id="last-editor-date">${lastEditorDate}</span>, by <span id="last-editor-nickname">${lastEditorName}</span> (<a class="blue-text text-lighten-3" href="mailto:${lastEditorEmail}" id="last-editor-email">${lastEditorEmail}</a>).
						<a class="orange-text text-lighten-3" href="#" id="edit-now-link">Edit Now</a>.      
					</div>
				</div>
			</c:if>
			<div class="footer-copyright bg-brandeis-blue-1 txt-brandeis-white">
				<div class="container">
					Made by Grady Ward '16, using 
					<a class="blue-text text-lighten-3" href="http://materializecss.com">MaterializeCSS</a>, 
					<a class="blue-text text-lighten-3" href="http://getcontenttools.com">Content Tools</a>, 
					<a class="blue-text text-lighten-3" href="https://pages.github.com">GitHub Pages</a> and 
					<a class="blue-text text-lighten-3" href="https://cloud.google.com/appengine/">Google App Engine</a>. 
					<a class="blue-text text-lighten-3" href="${staticpagelocation}/about-the-site">About This Site</a>.
					<a class="orange-text text-lighten-3" href="${webapplocation}/console">Site Manager</a>
				</div>
			</div>
		</footer>
		<script src="${resourceLocation}/static/js/jquery.min.js"></script>
		<script src="${resourceLocation}/static/js/materialize.js"></script>
		<script src="${resourceLocation}/static/js/init.js"></script>
		
		<jsp:invoke fragment="js"/>
    </body>
</html>

