
//该js中只包含管理员才能使用到的一些逻辑，对于普通用户不能进行引用
$(document).ready(function (event) {
	var infoModal = $("#infoModal");
	var modalTitle = $("#infoModal .modal-title");
	var modalBody = $("#infoModal .modal-body");
	var modalOKButton = $("#infoModal button[name='btnOK']");
	var modalCancelButton = $("#infoModal button[name='btnCancel']");
	//给每一个delete link绑定事件
	$("a[id^=delBlog").on("click", function(event) {
		event.preventDefault();
		var target = $(event.target);
		var href = target.attr('href');
		console.log("href:" + href);
		var isFakeDelete = true;
		
		if (href.indexOf("fakeDelete=1") > -1)
		{
			isFakeDelete = false;
		}

		modalTitle.html("提醒");
		modalBody.html("你确认删除该博客？" + (isFakeDelete ? "" : "<span class='danger-span'>（注意：删除后无法恢复！)</span>"));
		modalOKButton.unbind('click');
		modalOKButton.bind('click',function(event){
			event.preventDefault();
			deleteAsync(errorHandlerForDelBlog, succHandlerForDelBlog, modalOKButton, href);
		});
		infoModal.modal('toggle');
	});


	/**
	 * 删除blog服务端返回成功的处理方式
	 * @param {msgJson} msgJson 
	 */
	function succHandlerForDelBlog(msgJson)
	{
		succHandler(msgJson);
		infoModal.modal('toggle');
	}

	/**
	 * 删除blog服务端返回失败的处理方式
	 * @param {msgJson} msgJson 
	 */
	function errorHandlerForDelBlog(msgJson)
	{
		errorHandler(msgJson);
		infoModal.modal('toggle');
	}
})