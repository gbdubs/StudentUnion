<html>
<head id="head">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
	<title>Brandeis University Student Union</title>
	<link href="../../static/css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
	<link href="../../static/css/style.css" type="text/css" rel="stylesheet" media="screen,projection">
	<link href="../../static/css/content-tools-properties.css" type="text/css" rel="stylesheet">
</head>
<body id="body" class="bg-brandeis-grey" data-name="menu">
	<ul id="nav-mobile" class="side-nav" style="left: -250px;">
		<li class="bg-brandeis-blue-0">
			<a class="menu-header" href="../../">
				<img class="menu-seal" src="../../static/img/edited-seal.png">
				<h6 class="first txt-brandeis-white">Brandeis University</h6>
				<h6 class="txt-brandeis-white">Student Union</h6>
			</a>
		</li>
		<li><a href="../../">Home</a></li>
		<li><a href="../../e-board">Executive Board</a></li>
		<li><a href="../../senate">Senate</a></li>
		<li><a href="../../a-board">Allocations Board</a></li>
		<li><a href="../../judiciary">Judiciary</a></li>
		<li><a href="../../treasury">Treasury</a></li>
		<li><a href="../../documents">Constitution</a></li>
		<li><a href="../../documents">Bylaws</a></li>
		<li><a href="../../committees">Committees</a></li>
		<li><a href="../../news">News</a></li>
		<li><a href="../../elections">Elections</a></li>
		<li><a href="../../contact">Contact</a></li>
		<li><a href="../../pages">Pages</a></li>
	</ul>
	<nav class="bg-brandeis-blue-0 canopy" role="navigation">
		<div class="menu-ribbon"></div>
		<div class="nav-wrapper container">
			<a id="logo-container" href="../../" class="brand-logo">
			<img class="menu-seal" src="../../static/img/edited-seal.png">
			<span>Brandeis <span class="droppable">University </span>Student Union</span>
			</a>
			<ul class="menu hide-on-med-and-down">
				<li><a href="../../">Home</a></li>
				<li>
					<a class="dropdown-button" href="#!" data-activates="about-dropdown">Organization<i class="mdi-navigation-arrow-drop-down right"></i></a>
					<ul id="about-dropdown" class="dropdown-content txt-brandeis-blue-0">
						<li><a href="../../e-board">Executive Board</a></li>
						<li><a href="../../senate">The Senate</a></li>
						<li><a href="../../a-board">Allocations Board</a></li>
						<li><a href="../../judiciary">Judiciary</a></li>
						<li><a href="../../treasury">Treasury</a></li>
						<li class="divider"></li>
						<li><a href="../../documents">Constitution</a></li>
						<li><a href="../../documents">Bylaws</a></li>
					</ul>
				</li>
				<li><a href="../../pages">Pages</a></li>
				<li><a href="../../news">News</a></li>
				<li><a href="../../contact">Contact</a></li>
				<li><a href="../../elections">Elections</a></li>
			</ul>
			<a href="#" data-activates="nav-mobile" class="button-collapse"><i class="mdi-navigation-menu"></i></a>
		</div>
	</nav>
	
	<!-- CONTENT BELOW HERE -->
	
	
	<div class="content card bg-brandeis-white">
		<div data-editable class="section content-tools-editable">
			<h1>
				Create a New Petition
			</h1>
			<h2 class="light">
				<i>Your Voice. Heard.</i>
			</h2>
			<p class="left-align">
				Welcome to the petition creation page. To find out more about petitions, check out our 
				<a href="/petitions">petitions page</a>. If you have further questions, please contact Student Union
				Leaders before you submit your petition.
			<p>
			<p class="left-align">
				Before you start writing your petition, please make sure that you follow our basic rules
				associated with the use of this system. The Student Union will take down any posts which
				violate these rules, and reserves the right to do so for any reason.
			<p>
			<ol class="left-align">
				<li>
					Petitions cannot be hateful in nature, and can in no way directly or indirectly name
					individuals or their actions without their consent. Period.
				</li>
				<li>
					Petitions should be action-centered.  Petitions which only highlight problems on campus
					without suggesting solutions/methods of addressing them are not productive.
				</li>
				<li>
					Petitions need to be within the scope of the Student Union and Brandeis University.
					For example, petitions which support different political candidates, or do not have
					a connection to Brandeis will be removed.
				</li>
			</ol>
			<p class="left-align">
				You can choose the name that you sign to your petition, but your Brandeis email <b>will be public</b>
				meaning that your identity will be uniquely identified by submitting this petition. 
				The time that you submit the petition will be shown alongside the petition (but might reflect non-EST time)
			</p>
			<p class="left-align">
				Only create and publish petitions that you feel comfortable with the world knowing who wrote it.
				Petitions will be posted immediately (along with your email address) after you submit them (but it may take a few minutes for
				this to be reflected on the main page). Petitions cannot be un-submitted. 
				For questions on the petitioning process, please contact a member of the Student Union.
			</p>
			<form action="/petitions" method="POST" class="card bg-brandeis-blue-0">
				<input name="action" type="hidden" value="new"/>
				<input name="petitionName" type="text" placeholder="Title Your Petition Here" class="align-center font-30 bg-brandeis-blue-0 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0">
				
				<div class="bg-brandeis-blue-2 txt-brandeis-white pad-t-10 pad-b-10 margin-t-0 margin-b-0 margin-l-5 margin-r-5">
					Written by <input name="petitionAuthorName" class="petition-input-name" type="text" placeholder="Your Name Here"></input> (gward@brandeis.edu) on XXX
				</div>
				<textarea name="petitionBody" class="bg-brandeis-white left-align petition-input">
                    Lorem ipsum dolor sit amet, aptent amet at per aliquet vitae, accumsan placerat at, iaculis justo gravida ut, ut diam fusce vel, inceptos nascetur scelerisque sem a. Nec vitae vestibulum ac litora neque, nec vitae praesent proin pede. Fermentum malesuada rutrum ornare in integer nulla, arcu dui pellentesque nunc imperdiet iaculis in, risus duis massa fermentum libero tincidunt. Adipiscing sit in velit sit, dolor metus phasellus etiam. Sodales urna, at vel quis nunc eu convallis. Vitae ipsum quis, placerat ipsum dui fusce a purus phasellus, lectus mattis arcu viverra cras, netus cursus ultrices. Wisi eget ut a quis praesent eu, per mattis, ultricies nulla, dis qui cras in quis accumsan quis, aliquam nunc ligula aliquam dui auctor blandit. Ut ligula elit, urna tincidunt curabitur in id vel semper, vehicula mauris sem, duis ut. Orci lorem tincidunt accumsan amet, neque ullamcorper.
                </textarea>
				<div class="pad-b-10 right-align">
					<span class="card txt-brandeis-white bg-brandeis-blue-1 pad-t-10 pad-r-10 pad-l-10 pad-b-10 margin-r-10">
						1 For
					</span>
					<span class="card txt-brandeis-white bg-brandeis-blue-1 pad-t-10 pad-r-10 pad-l-10 pad-b-10 margin-r-10">
						0 Against
					</span>
					<button class="btn bg-brandeis-yellow txt-brandeis-black margin-r-10">
						Submit Petition (Cannot Be Undone)
					</button>
				</div>
			</div>
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
						Check out the <a class="blue-text text-lighten-3" href="../../office-hours">full list of office hours</a> to see when you can drop by!
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
			<div id="last-edited" class="container">
				This page was last edited on <span id="last-editor-date">Thursday, April 28, 2016 at 09:05 PM</span>, by <span id="last-editor-nickname">Grady Ward</span> (<a class="blue-text text-lighten-3" href="mailto:grady.b.ward@gmail.com" id="last-editor-email">grady.b.ward@gmail.com</a>).
				<a class="blue-text text-lighten-3" href="http://githubdemo-1285.appspot.com/edit/edit/eboard" id="edit-now-link">Edit Now</a>.      
			</div>
		</div>
		<div class="footer-copyright bg-brandeis-blue-0 txt-brandeis-white">
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
	<script src="../../static/js/jquery.min.js"></script>
	<script src="../../static/js/materialize.js"></script>
	<script src="../../static/js/init.js"></script>
</body>
</html>