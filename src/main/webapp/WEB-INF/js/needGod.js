$(document).ready(function (event) {
	window.popId = 0;

	$('#godBtn').click(
		function (event) {
			event.preventDefault();
			var godKey = $('#key').val().trim();
			if (godKey === "" || godKey.length <= 10) {
				console.info("Invalid key!");
				new PopupMessageBox("key长度过短，必须大于10！", "ERROR")
					.showBoxShortTime();
				return;
			}
			
			var postData = {}
			postData['key'] = godKey;
			submitFormAsync($("#needGodForm"), errorHandler,
				succHandler, JSON.stringify(postData));
		});

});