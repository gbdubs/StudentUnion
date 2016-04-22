(function($){
  $(function(){

    $('.button-collapse').sideNav();
    
    $(".dropdown-button").dropdown();
    
    var editorURL = "http://githubdemo-1285.appspot.com/edit";
    
    var pathToPage = window.location.href;
	pathToPage = pathToPage.substring(pathToPage.indexOf("/", 8)+1);
	$("#edit-now-link").attr("href", editorURL + "/" + pathToPage);
		
  });
})(jQuery);