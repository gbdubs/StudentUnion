<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Student Union Website Editor | Personal Information</title>
        <script src="/static/js/jquery.min.js"></script>
    </head>
    
    <body>
        <p>
    		Currently logged in as ${currentUser.nickname} (${currentUser.email}). 
    	</p>
    	<p>
    		Click here to <a href="${logoutUrl}">Log Out</a> or to <a href="/console">return to the console</a>.
    	</p>
    	
    	<br>
    	<br>
    	
    	<h1>Brandeis Student Union Website : Personal Information Manager</h1>
    	
    	<br>
    	<br>
		
		<h1>Edit Your Personal Information</h1>
		<p>
			On this page, there are a number of questions which will be featured in your student union profile.
			Profiles appear in every group that you are in (for example, if you are a senator, your profile
			will be on the membership page of every one of the committees that you are on, in addition to being
			on the Senate membership page.
		</p>
		<p>
			You are the only person who is able to edit your profile.
			Please be careful with what you put up. Professionalism is KEY.
		</p>
	
		
		<form method="POST" action="/self-manager">
			<h2>Email</h2>
			<p>
				This is the email you must use in order to use the site. Additionally, it will be displayed on your profile. You cannot change this.
			</p>
			<input type="hidden" name="email" value="${person.email}"/>
			<h4>${person.email}</h4>
			
			<br>
			<br>
			<br>
			
			<h2>Nickname</h2>
			<p>
				This is the name that you will be described by on any page on the Student Union Website. 
				Please include first and last name, or as much information as necessary so that people know who you are.
			</p>
			<input type="text" name="nickname" value="${person.nickname}">
			
			<br>
			<br>
			<br>
			
			<h2>Class Year</h2>
			<p>
				What year do you anticipate graduating? (e.x. 2016, 2020, 2021)
			</p>
			<input type="text" name="classYear" placeholder="Class Year" value="${person.classYear}">
			
			<br>
			<br>
			<br>
			
			<h2>Short Biography</h2>
			<p>
				Give a brief biography, who are you, what do you do on campus, what do you do within the Union, 
				what do you want to accomplish in the student union, what are the best ways to contact you?
			</p>
			<textarea name="biography" placeholder="Type your brief biography here">${person.biography}</textarea>
			
			<br>
			<br>
			<br>
			
			<h2>Image URL</h2>
			<p>
				Images must be uploaded on another site, then linked to here.
				On any membership page, your biography and photo will be visible. 
				It is recommended to chose a photo that is square (as all will be squashed to be square). 
			</p>
			<p>
				A good way to upload one of your own photos is to go to <a href="http://www.imgur.com">imgur.com</a>
				upload your photo however you would like, then once it is uploaded, click on the link on the side that says
				<b>Direct Link</b>.
			</p>
			<input name="imageUrl" placeholder="Image URL Here" value="${person.imageUrl}"/>
			
        	
        	<br>
        	<br>
        	<br>
        	<br>
        	
			<button>Save All Changes On Page</button>
			
		</form>
        
        <br>
        <br>
        <br>
        
        <p>Built by Grady Ward '16, for questions or concerns, reach out to <a href="mailto:grady.b.ward@gmail.com">grady.b.ward@gmail.com</a>.</p>
        
    </body>
</html>