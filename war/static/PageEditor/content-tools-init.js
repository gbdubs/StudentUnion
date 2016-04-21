window.addEventListener('load', function() {
	var editor;
	
	editor = ContentTools.EditorApp.get();
	
	editor.init('*[data-editable]', 'data-name');
	
	ContentTools.StylePalette.add([
		new ContentTools.Style('Card',  'card', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Padding - XS', 'pad-5',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Padding - S', 'pad-10',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Padding - M', 'pad-15',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Padding - L', 'pad-20',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Padding - XL', 'pad-25',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Black', 'txt-brandeis-black', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - White', 'txt-brandeis-white', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Grey', 'txt-brandeis-grey',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Light Blue', 'txt-brandeis-blue-0',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Normal Blue', 'txt-brandeis-blue-1',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Dark Blue', 'txt-brandeis-blue-2', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Text - Yellow', 'txt-brandeis-yellow',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Black', 'bg-brandeis-black', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - White', 'bg-brandeis-white', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Grey', 'bg-brandeis-grey',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Light Blue', 'bg-brandeis-blue-0',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Normal Blue', 'bg-brandeis-blue-1',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Dark Blue', 'bg-brandeis-blue-2', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
		new ContentTools.Style('Background - Yellow', 'bg-brandeis-yellow',  ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6']),
	]);
	

    // Listen for saved events
    editor.addEventListener('saved', function (ev) {
    
        $("*[data-editingonly]").remove();
        //$(".ce-element").removeClass("ce-element");
        //$(".ce-element--type-text").removeClass("ce-element--type-text");
        //$(".ce-element--focused").removeClass("ce-element--focused");
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