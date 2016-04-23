<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <title>Student Union Website Editor | Personal Information</title>
        <script src="/static/js/jquery.min.js"></script>
    </head>
    
    <body>
        <style>
            body {
                font-family: sans-serif;
        	}
        </style>
        
		<h1 class="centered">Edit your Personal Information</h1>
		
		<div class="logout-button">
			Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
			<a href="${logoutUrl}">Log Out</a>
		</div>
		<div class="home-button">
			<a href="/console">Return to Console</a>
		</div>
		
		<form method="POST" action="/self-manager">
			<h2>Email</h2>
			<h5>This is the email you must use in order to use the site. Additionally, it will be displayed on your profile. You cannot change this.</h5>
			<div>
				<input type="hidden" name="email" value="${person.email}"/>
				${person.email}
			</div>
		
			<h2>Nickname</h2>
			<h5>This is the name that you will be described by on any page on the Student Union Website. Please include first and last name, or as much information as necessary so that people know who you are.</h5>
			<div>
				<input type="text" name="nickname" value="${person.nickname}">
			</div>
		
			<h2>Class Year</h2>
			<h5>What year do you anticipate graduating? (e.x. 2016, 2020, 2021)</h5>
			<div>
				<input type="text" name="classYear" placeholder="Class Year" value="${person.classYear}">
			</div>
		
			<h2>Short Biography</h2>
			<h5>Give a brief biography, who are you, what do you do on campus, what do you do within the Union, what do you want to accomplish in the student union, what are the best ways to contact you?</h5>
			<div>
				<textarea name="biography" placeholder="Type your brief biography here">${person.biography}</textarea>
			</div>
		
			<h2>Image URL</h2>
			<h5>
				Images must be uploaded on another site, then linked to here.
				On any membership page, your biography and photo will be visible. 
				It is recommended to chose a photo that is square (as all will be squashed to be square). 
			</h5>
			<h5>
				A good way to upload one of your own photos is to go to <a href="http://www.imgur.com">imgur.com</a>
				upload your photo however you would like, then once it is uploaded, click on the link on the side that says
				<b>Direct Link</b>.
			</h5>
			<div>
				<input name="imageUrl" placeholder="Image URL Here" value="${person.imageUrl}"/>
			</div>
        
        	<div>
				<button>Submit</button>
			</div>
		</form>
            
        <p class="footer">Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>