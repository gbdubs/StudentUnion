<html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<head>
<script src="https://code.jquery.com/jquery-2.2.3.min.js" integrity="sha256-a23g1Nt4dtEYOj7bR+vTu7+T8VP13humZFBJNIYoEJo=" crossorigin="anonymous"></script>
</head>
<body>

<style>
	.hidden{ display: none }
</style>
<div>
	<input type="file" id="file-upload">
	<button id="btn">Process</button>
	<img id="img"></img>
	<div id="waiting" class="hidden">WAITING</id>
</div>
<script>

$(function(){
	
	function sendImage(imageData, suffix){
		console.log("Upload started.");
		$.ajax({
		  url: '/image-upload',
		  type: 'POST',
		  data: {
			'imageData': imageData,
			'suffix': suffix
		  },
		  success: function( imageLink ) {
			alert('File Was successfully uploaded.');
			$("#img").attr('src', imageLink);
			$("#waiting").removeClass("hidden");
		  }
		});
	}
	
	$("#btn").click(function(){
		$("#waiting").removeClass("hidden");
		$("#img").attr('src', "");
		console.log("Encoding initiated.");
		var input = document.getElementById('file-upload');
		var file = input.files[0];
		var suffix = file.name.substring(file.name.lastIndexOf('.')+1);
		var reader = new FileReader();
		reader.onload = function (readerEvent){
			var binaryString = readerEvent.target.result;
			var inBase64 = btoa(binaryString);
			console.log("Encoding complete.");
			sendImage(inBase64, suffix);
		}
      	reader.readAsBinaryString(file);
	});
	
});


</script>


</body>


</html>