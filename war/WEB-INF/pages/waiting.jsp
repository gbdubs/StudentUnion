<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
    	<script src="/static/js/jquery.min.js"></script>
        <title>Student Union Website Editor | Page Editor Waiting</title>
    </head>
    
    <body>
        
		<h1>Waiting for the Github Server to Update...</h1>
		<h4>
			This can take anywhere from 10 seconds to 1 minute.
			After 1 minute, just try to go to the page manually by clicking 
			<a href="${urlToGoTo}">this link</a>.
		</h4>
		
		<div style="display:none;" id="sub-page">
		
		</div>
		
		<script>
			$(function(){
				
				// Disable caching of AJAX responses
				$.ajaxSetup ({
					cache: false
				});
			
				var urlToCheck= "${urlToGoTo}";
				
				function getLastEdited(callback){
					$("#sub-page").load(urlToCheck, 
    					function (responseText, textStatus, req) {
        					if (textStatus == "error") {
          						callback("Page Not Found");
        					} else {
        						var temp = $("#sub-page #last-editor-date").text();
        						callback(temp);
        					}
						}
					);
				}
			
				var firstFound = 0;
				var originalLastEdited;
				
				getLastEdited(function (response) {
					originalLastEdited = response; 
					firstFound = 1;
				});
				
				var trial = 1;
				
				var interval = setInterval(function(){
					console.log("TRIAL #" + trial++);
					getLastEdited(function (n){
						if (firstFound) {
							if (n == originalLastEdited){
								console.log ("Same as original, Server has not yet updated.");
							} else {
								console.log("CHANGED!");
								clearInterval(interval);
								window.location.replace(urlToCheck);
							}
						}
					});
				}, 500);
			});
		</script>
    </body>
</html>
