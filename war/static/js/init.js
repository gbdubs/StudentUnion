(function($){
  $(function(){

    $('.button-collapse').sideNav();
    
    $(".dropdown-button").dropdown();
    
    var editorURL = "http://admin.brandeis.io/edit";
    
    var pathToPage = window.location.pathname;
    
    if (pathToPage.indexOf("index.html") > -1){
    	pathToPage = pathToPage.replace("index.html","");
    }
    
    if (pathToPage.length > 0 && pathToPage[pathToPage.length - 1] == '/'){
    	pathToPage = pathToPage.substring(0, pathToPage.length - 1);
    }
    
    while (pathToPage.length > 0 && pathToPage[0] == '/'){
    	pathToPage = pathToPage.substring(1);
    }
    
	$("#edit-now-link").attr("href", editorURL + "/" + pathToPage);
		
  });
})(jQuery);