$(document).ready(function(){
	var head = $("head");
	var body = $("body");
	$(head).append("<link data-editingonly rel=\"stylesheet\" type=\"text/css\" href=\"/static/ContentTools/build/content-tools.min.css\">");
	$(body).append("<script data-editingonly src=\"/static/ContentTools/build/content-tools.js\"></script>");
	$(body).append("<script data-editingonly src=\"/static/content-tools-init.js\"></script>");
});