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
			<div class="input-field">
				<textarea class="materialize-textarea" name="biography" placeholder="Type your brief biography here">${person.biography}</textarea>
			</div>
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
	
			<h2>Profile Picture</h2>
			<p>
				Shown below is your current profile picture.
				On any membership page, your biography and photo will be visible. 
				It is recommended to chose a photo that is square (as all will be squashed to be square and do you
				want it to be all squashed?).
				To upload a new photo, or change your photo, just press the "select" button below.
				Note that it can take up to 20 seconds for your photo to upload, just be patient.
				Once your photo is uploaded you will still need to save the changes on the page (light blue button) to ensure that your change to your picture is saved.
			</p>
			<input id="imageUrl" name="imageUrl" type="hidden" placeholder="Image URL Here" value="${person.imageUrl}"/>
			<img style="height:300px; width: 300px;" id="imageDisplay" src="${person.imageUrl}"/>
			
			<div class="file-field input-field">
		      <div id="imageUploadBtn" class="btn bg-brandeis-blue-1">
		        <span>Select</span>
		        <input id="imageUploadInput" type="file">
		      </div>
		      <div class="file-path-wrapper">
		        <input class="file-path validate" type="text">
		      </div>
		    </div>
	
			<br>
			<br>
			
			<br>
			<br>
	
			<button class="btn btn-large bg-brandeis-blue-2">Save All Changes On Page</button>

			<br>
			<br>
	
		</form>

	</jsp:attribute>
	
	<jsp:attribute name="js">
		<script>
			$(function(){
				var imDisp = $("#imageDisplay");
				var imInput = $("#imageUploadInput");
				var imUrl = $("#imageUrl");
				var imButton = $("#imageUploadBtn");
				var imMessage = $("span", imButton).first();

				function imageExists(imageUrl) {
					var http = new XMLHttpRequest();
					http.open('HEAD', imageUrl, false);
					http.send();
					return http.status != 404;
				}

				$(imInput).change(function (){
					$(imMessage).text("Uploading...");
					$(imButton).addClass("disabled");
					var file = imInput[0].files[0];
					var suffix = file.name.substring(file.name.lastIndexOf('.') + 1);
					var reader = new FileReader();

					reader.onload = (function(readerEvent) {
						var binaryString = readerEvent.target.result;
						var inBase64 = btoa(binaryString);
						console.log("Encoding complete.");
						console.log("Upload started.");
						$.ajax({
							url: '/image-upload',
							type: 'POST',
							data: {
								'imageData': inBase64,
								'suffix': suffix
							},
							success: function(imageLink) {
								$(imMessage).text("Waiting for Github...");
								var trial = 1;
								var imageExistsInterval = setInterval(function() {
									if (imageExists(imageLink)) {
										clearInterval(imageExistsInterval);
										var image = new Image();
										image.src = imageLink;
										image.onload = function(){
											var dims = [image.width, image.height];
											finalImageUrl = imageLink;
											finalImageDims = dims;
											$(imDisp).attr('src', finalImageUrl);
											$(imInput).val(null);
											$(imUrl).val(finalImageUrl);
											$(imMessage).text("Select");
											$(imButton).removeClass("disabled");
										}
									}
									console.log("Image exists trial... " + trial++);
								}, 700);
							}
						});
						
					});

					reader.readAsBinaryString(file);
     			});

				$(imButton).click(function(ev){
					if (imInput[0].files[0]){
						
					} else {
						$(imMessage).text("Selecting");
					}
				});
				
			});
		</script>
	</jsp:attribute>
	
</t:page>
