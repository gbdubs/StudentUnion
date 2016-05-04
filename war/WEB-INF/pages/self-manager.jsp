<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">

		<h1 class="light">Personal Information Manager</h1>

		<p>
			On this page, there are a number of questions which will be featured in your student union profile.
			Profiles appear in every group that you are in (for example, if you are a Senator, your profile
			will be on the membership page of every one of the committees that you are on, in addition to being
			on the Senate membership page. You are the only person who is able to edit your profile.
			Please be careful with what you put up. Professionalism is KEY. Site owners have the ability to take
			down any inappropriate content.
		</p>

		<form method="POST" action="/self-manager" class="left-align">
			
	
	
			<h2>Name</h2>
			<p>
				This is the name that you will be described by on any page on the Student Union Website. 
				Please include first and last name, or as much information as necessary so that people know who you are.
				For example, my nickname on the site is "Grady Ward".
				Feel free to put whatever name people most frequently call you by. 
				It will be the same across the site.
			</p>
			<input type="text" name="nickname" value="${person.nickname}">
	
			<br>
			<br>
	
			<h2>Class Year</h2>
			<p>
				What year do you anticipate graduating? (e.x. 2016, 2020, 2021)
				Must be numerical, and start with 20
			</p>
			<input type="text" name="classYear" placeholder="Class Year" value="${person.classYear}">
	
			<br>
			<br>
			
	
			<h2>Short Biography</h2>
			<p>
				Give a brief biography, who are you, what do you do on campus, what do you do within the Union, 
				what do you want to accomplish in the student union, what are the best ways to contact you.
				Please keep your biography to 500 words or less, otherwise it may be truncated from the website.
			</p>
			<textarea style="min-height: 150px" name="biography" placeholder="Type your brief biography here">${person.biography}</textarea>
			
			<br>
			<br>
			
			
			<h2>Email</h2>
			<p>
				This is the email that you will be tagged with on the site.
				Additionally, it will be displayed on your profile. 
				Sorry, but you cannot change this.
				If you want to access the SUWebsiteEditor via another email, request that from a current site owner.
			</p>
			<input type="hidden" name="email" value="${person.email}"/>
			<h6>${person.email}</h6>
	
			<br>
			<br>
	
			<h2>Image URL</h2>
			<p>
				Images must be uploaded on another site, then linked to here.
				On any membership page, your biography and photo will be visible. 
				It is recommended to chose a photo that is square (as all will be squashed to be square and do you
				want it to be all squashed?).
			</p>
			<p>
				A good way to upload one of your own photos is to go to <a href="http://www.imgur.com">imgur.com</a>
				upload your photo however you would like, then once it is uploaded, click on the link on the side that says
				<b>Direct Link</b>. If the URL you are trying to upload doesn't start with http:// and end with an image
				extension (.jpg, .png, etc.), then your image will NOT BE DISPLAYED.
			</p>
			<input name="imageUrl" type="text" placeholder="Image URL Here" value="${person.imageUrl}"/>
	
	
			<br>
			<br>
			
			<br>
			<br>
	
			<button class="btn bg-brandeis-blue-2">Save All Changes On Page</button>
	
		</form>

	</jsp:attribute>
</t:page>
