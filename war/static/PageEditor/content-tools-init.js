window.addEventListener('load', function() {
	var editor;
	editor = ContentTools.EditorApp.get();
	editor.init('*[data-editable]', 'data-name');

    // Listen for saved events
    editor.addEventListener('saved', function (ev) {
    
        $("*[data-editingonly]").remove();
        $(".ce-element").removeClass("ce-element");
        $(".ce-element--type-text").removeClass("ce-element--type-text");
        $(".ce-element--focused").removeClass("ce-element--focused");
        $(".hiddendiv.common").remove();
        $(".drag-target").remove();
        $(".ct-app").remove();
        
        var pageData = $("html").html();
        
        $("body").append("<div class=\"waiting-screen\">Waiting</div>");
        
        var url = window.location.pathname.substring(6);
        
        if (url.length > 0){
        	url = url + "/";
        }
        
        if (url.indexOf(".html") == -1){
        	url = url + "index.html";
        }
        
        
        $.ajax({
        	type: "POST",
        	url: "/edit",
        	data: {
				path: url,
				content: pageData
			},
			complete: function( data ) {
				console.log("CALLED!");
				var resp = JSON.parse(data.responseText);
				if (data.status == 200){
					window.location.replace("http://subrandeis.github.io/website/"+resp.url);
				} else if (data.status == 400){
					var r = confirm("!!! ERROR FROM SERVER !!! \n\n " +resp.message + " \n\n\t- The editor will need to be reloaded to continue. \n \t- If this error keeps happening, please email grady.b.ward@gmail.com. \n\t-Is it possible that the page you are creating interferes with another page URL? \n\n\nPress OK to Reload the page and try again. \nPress CANCEL to stay on this page (which cannot edit without a reload).");
					if (r){
						location.reload();
					}
					$(".waiting-screen").remove();
				}
				console.log(resp.message);
			}
		});
    });
});