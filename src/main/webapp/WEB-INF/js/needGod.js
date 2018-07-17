$(document).ready(function (event) {
	window.popId = 0;

	$('#godBtn').click(
		function (event) {
			event.preventDefault();
			var btn = $(event.target);
			var godKey = $('#key').val().trim();
			if (godKey === "" || godKey.length <= 10) {
				console.info("Invalid key!");
				new PopupMessageBox("key长度过短，必须大于10！", "ERROR")
					.showBoxShortTime();
				return;
			}

			submitFormAsync($("#needGodForm"), errorHandler,
				succHandler);
		});

});