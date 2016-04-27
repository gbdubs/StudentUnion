<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
    	<script src="/static/js/jquery.min.js"></script>
        <title>Student Union Website Editor | Page Editor Waiting</title>
    </head>
    
    <body>
        <style> body {font-family: sans-serif;}</style>
        
		<h1 class="centered">Waiting for the Github Server to Update...</h1>
		
		<div style="display:none;" id="sub-page">
		
		</div>
		
		<script>
			$(function(){
			
				$.ajaxSetup ({
					// Disable caching of AJAX responses
					cache: false
				});
			
				var urlToCheck= "${urlToGoTo}";
				
				var urlToDefaultTo = "${urlToDefaultTo}";
			
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
				var original;
				
				getLastEdited(function (n) {
					original = n; 
					firstFound = 1;
				});
				
				var trial = 1;
				
				var interval = setInterval(function(){
					console.log("TRIAL #" + trial++);
					getLastEdited(function (n){
						if (firstFound) {
							if (n == original){
								console.log ("Same.");
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
