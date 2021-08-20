//<![CDATA[
	'use strict'
let HOME = (function ($) {
	return {

	}
}(jQuery));

$(document).ready(function() {
	'use strict'
	let a = '[[#{welcome.message}]]';

	const blobToData = (blob) => {
		return new Promise((resolve) => {
		  const reader = new FileReader()
		  reader.onloadend = () => resolve(reader.result)
		  reader.readAsDataURL(blob)
		})
	  };

	$('#uploadFile').on('change', async (e) => {
		const file = e.target.files[0];
		console.log(file);
		let data = await blobToData(file);
		console.log(data);
		const uploadForm = {};
		uploadForm.fileName = file.name;
		uploadForm.fileData = data;
		uploadForm.fileContentType = file.type;
		$('#uploadPic').attr('src',data);
		$('#uploadLink').attr('href',data);
		$('#uploadLink').attr('download',file.name);
		$('#uploadCard').show();
		console.log(uploadForm);
		$(e.target).val(null);
	});
});
//]]>