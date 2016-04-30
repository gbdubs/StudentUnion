<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<t:page>
	<jsp:attribute name="content">
		<div class="content card bg-brandeis-white">
			<div data-editable class="section content-tools-editable">
		
				<h1>Waiting for the Github Server to Update...</h1>
				<h4>
					This can take anywhere from 10 seconds to 1 minute.
					After 1 minute, just try to go to the page manually by clicking 
					<a href="${urlToGoTo}">this link</a>.
				</h4>
		
				<div style="display:none;" id="sub-page"></div>
		    </div>
		</div>
	</jsp:attribute>
	
	<jsp:attribute name="js">
		<script>
			$(function(){
				
				// Disable caching of AJAX responses
				// $.ajaxSetup ({
				//	cache: false
				// });
			
				var urlToCheck= "${urlToGoTo}";
				
				function getLastEdited(callback){
					$("#sub-page").load(urlToCheck+"?_timestamp="+new Date().getTime(), 
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
	</jsp:attribute>
	
</t:page>