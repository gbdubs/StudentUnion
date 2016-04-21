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
        console.log(pageData);
        
        var url = window.location.pathname.substring(6);
        
        if (url.length > 0){
        	url = url + "/";
        }
        
        if (url.indexOf(".html") == -1){
        	url = url + "index.html";
        }
        
        $.ajax({
        	type: "POST",
        	url: "/page",
        	data: {
        		path: url,
        		content: pageData
        	}
        });
    });
});