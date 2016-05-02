<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
	
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
	
	</jsp:attribute>
</t:page>