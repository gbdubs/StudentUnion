window.addEventListener('load', function() {
	var editor;

	console.log("Replacing last editor details");
	$("#last-editor-date").text("[LAST EDITOR DATE]");
	$("#last-editor-nickname").text("[LAST EDITOR NICKNAME]");
	$("#last-editor-email").text("[LAST EDITOR EMAIL]").attr("href", "mailto:[LAST EDITOR EMAIL]");

	editor = ContentTools.EditorApp.get();

	editor.init('*[data-editable]', 'data-name');

	ContentTools.StylePalette.add([
		new ContentTools.Style('Card', 'card', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		
		new ContentTools.Style('Thin Text', 'light', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		
		new ContentTools.Style('Thinest Text', 'txt-super-light', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Boldest Text', 'txt-super-bold', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
	
		new ContentTools.Style('Smaller Text', 'txt-smaller-plz', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Bigger Text', 'txt-bigger-plz', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		
		new ContentTools.Style('Button', 'btn', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

		new ContentTools.Style('Text - Black', 'txt-brandeis-black', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Text - White', 'txt-brandeis-white', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Text - Grey', 'txt-brandeis-grey', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

		new ContentTools.Style('Text - Light Blue', 'txt-brandeis-blue-2', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Text - Normal Blue', 'txt-brandeis-blue-1', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Text - Dark Blue', 'txt-brandeis-blue-0', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		
		new ContentTools.Style('Text - Yellow', 'txt-brandeis-yellow', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

		new ContentTools.Style('Background - Black', 'bg-brandeis-black', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Background - White', 'bg-brandeis-white', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Background - Grey', 'bg-brandeis-grey', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

		new ContentTools.Style('Background - Light Blue', 'bg-brandeis-blue-2', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Background - Normal Blue', 'bg-brandeis-blue-1', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Background - Dark Blue', 'bg-brandeis-blue-0', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Background - Yellow', 'bg-brandeis-yellow', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		
		new ContentTools.Style('Padding - Top - XS', 'pad-t-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Top - S', 'pad-t-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Top - M', 'pad-t-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Top - L', 'pad-t-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Top - XL', 'pad-t-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Bottom - XS', 'pad-b-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Bottom - S', 'pad-b-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Bottom - M', 'pad-b-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Bottom - L', 'pad-b-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Bottom - XL', 'pad-b-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Left - XS', 'pad-l-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Left - S', 'pad-l-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Left - M', 'pad-l-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Left - L', 'pad-l-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Left - XL', 'pad-l-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Right - XS', 'pad-r-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Right - S', 'pad-r-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Right - M', 'pad-r-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Right - L', 'pad-r-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Padding - Right - XL', 'pad-r-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

		new ContentTools.Style('Margin - Top - XS', 'margin-t-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Top - S', 'margin-t-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Top - M', 'margin-t-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Top - L', 'margin-t-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Top - XL', 'margin-t-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Bottom - XS', 'margin-b-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Bottom - S', 'margin-b-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Bottom - M', 'margin-b-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Bottom - L', 'margin-b-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Bottom - XL', 'margin-b-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Left - XS', 'margin-l-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Left - S', 'margin-l-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Left - M', 'margin-l-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Left - L', 'margin-l-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Left - XL', 'margin-l-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Right - XS', 'margin-r-5', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Right - S', 'margin-r-10', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Right - M', 'margin-r-15', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Right - L', 'margin-r-20', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),
		new ContentTools.Style('Margin - Right - XL', 'margin-r-25', ['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'ol', 'li', 'ul', 'div', 'img', 'video']),

	]);


	// Listen for saved events
	editor.addEventListener('saved', function(ev) {

		$("*[data-editingonly]").remove();
		$(".hiddendiv.common").remove();
		$(".drag-target").remove();
		$(".ct-app").remove();

		var pageData = $("#content-inner-wrapper").html();

		$("body").append("<div class=\"waiting-screen\">Waiting</div>");

		var url = window.location.pathname.substring(6);
		var originalUrl = url;

		if (url.length > 0) {
			url = url + "/";
		}

		if (url.indexOf(".html") == -1) {
			url = url + "index.html";
		}


		$.ajax({
			type: "POST",
			url: "/edit",
			data: {
				path: url,
				content: pageData
			},
			complete: function(data) {
				if (data.status == 200) {
					var urlToGoTo = "http://union.brandeis.io/" + originalUrl;
					urlToGoTo = urlToGoTo.replace("/", "%2F").replace(":", "%3A");
					window.location.replace("/waiting-for-page-update?urlToGoTo=" + urlToGoTo);
				} else if (data.status == 400) {
					var r = confirm("!!! ERROR FROM SERVER !!! \n\n \n\n\t- The editor will need to be reloaded to continue. \n \t- If this error keeps happening, please email grady.b.ward@gmail.com. \n\t-Is it possible that the page you are creating interferes with another page URL? \n\n\nPress OK to Reload the page and try again. \nPress CANCEL to stay on this page (which cannot edit without a reload).");
					if (r) {
						location.reload();
					}
					$(".waiting-screen").remove();
				}
				console.log(data);
			}
		});
	});

	function imageExists(imageUrl) {
		var http = new XMLHttpRequest();
		http.open('HEAD', imageUrl, false);
		http.send();
		return http.status != 404;
	}

	function imageUploader(dialog) {
		
		var finalImageUrl;
		var finalImageDims;
	
		dialog.addEventListener('imageuploader.fileready', function(ev) {

			var trial = 1;
			var file = ev.detail().file;

			var suffix = file.name.substring(file.name.lastIndexOf('.') + 1);
			var reader = new FileReader();
			
			
			reader.onload = (function(readerEvent) {
				var binaryString = readerEvent.target.result;
				var inBase64 = btoa(binaryString);
				console.log("Encoding complete.");
				console.log("Upload started.");
				$.ajax({
					url: '/image-upload',
					type: 'POST',
					data: {
						'imageData': inBase64,
						'suffix': suffix
					},
					success: function(imageLink) {
						var imageExistsInterval = setInterval(function() {
							if (imageExists(imageLink)) {
								clearInterval(imageExistsInterval);
							
								var image = new Image();
								image.src = imageLink;
								image.onload = function(){
									var dims = [image.width, image.height];
									dialog.populate(imageLink, dims);
									finalImageUrl = imageLink;
									finalImageDims = dims;
								}
							}
							console.log("Image exists trial... " + trial++);
						}, 700);
					}
				});
				
			});

			dialog.state('uploading');
			dialog.progress(10);

			reader.readAsBinaryString(file);
		});

		dialog.addEventListener('imageuploader.clear', function() {
			dialog.clear();
			image = null;
		});

		dialog.addEventListener('imageuploader.save', function() {
			dialog.busy(false);

			dialog.save(
				finalImageUrl,
				finalImageDims, 
				{
					'alt': "An Image",
					'data-ce-max-width': dialog._domImage.clientHeight
				}
			);
		});
	}




	ContentTools.IMAGE_UPLOADER = imageUploader;
});