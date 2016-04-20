window.addEventListener('load', function() {
	var editor;
	editor = ContentTools.EditorApp.get();
	editor.init('*[data-editable]', 'data-name');

    // Listen for saved events
    editor.addEventListener('saved', function (ev) {
        $("*[data-editingonly]").remove();
        var pageData = $("html").html();
        console.log(pageData);
    });
});