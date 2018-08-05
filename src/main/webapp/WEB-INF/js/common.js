window.counter = 0; // 全局唯一的计数器，用来生成一些唯一dom id会用到
window.code = {
	"SUCCESS": 100
}

/*---------------  一些通用的方法 ---------------*/

/**
 * 默认的错误处理器
 * 
 * @param msg
 */
function defaultErrorHandler(msg) {
	msg = (msg === undefined ? "未知错误！" : msg);
	console.warn(msg);
	alert(msg);
}

function defaultSuccHandler(msg) {
	msg = (msg === undefined ? "提交成功！" : msg);
	console.info(msg);
	alert(msg);
}


/*---------------  当返回json时常用的处理方法 ---------------*/
/**
 * 服务器返回了错误
 * @param {返回的json}
 *            msgJson
 */
function errorHandler(msgJson) {
	var msg = msgJson.msg;

	if (msgJson.msg === undefined) {
		msg = msgJson.textStatus === undefined ? "未知错误！"
			: msgJson.textStatus;
		if (msg.indexOf('timeout') > -1) {
			msg = "网络超时，请稍后重试！";
		} else if (msg.indexOf('abort') > -1) {
			msg = "服务器重置了连接，请稍后重试！";
		}
	}

	new PopupMessageBox(msg, "ERROR").showBoxShortTime();
}

/**
 * 服务器成功返回
 * 
 * @param {返回的json}
 *            msgJson
 */
function succHandler(msgJson) {
	if (msgJson.code != window.code['SUCCESS']) {
		new PopupMessageBox(msgJson.msg, "ERROR")
			.showBoxShortTime();
		return;
	}

	console.log(msgJson.msg);
	new PopupMessageBox(msgJson.msg, "INFO").showBoxShortTime();

	if (msgJson.redirect != undefined) {
		console.log("Need redirecting:" + msgJson.redirect);
		setTimeout(function(){
			window.location.replace(msgJson.redirect);
		}, 500);
	}
	else if(msgJson.href != undefined)
	{
		console.log("Need href:" + msgJson.href);
		setTimeout(function(){
			window.location.href = msgJson.href;
		}, 500);
	}
	else{
		//
	}
}

/**
 * 异步提交表单数据 errorHandler和successHandler必须拥有一个字符串参数
 * 
 * @returns
 */
function submitFormAsync(target, errorHandler, successHandler, postData=target.serialize()) {
	if (target == undefined) {
		console.error("Target can not be undefined! Please check!");
		return;
	}

	errorHandler = (errorHandler == undefined ? defaultErrorHandler
		: errorHandler);
	successHandler = (successHandler == undefined ? defaultSuccHandler
		: successHandler);
	var btn = target.find('[type=submit]');
	var oldText = btn.text();
	console.log("OldText:" + oldText);

	$.ajax({
		url: target.prop('action'),
		type: target.prop('method'),
		data: postData,
		dataType: "json",
		headers: {
			"Accept": "application/json",
			"Content-Type": "application/json; charset=utf-8"
		},
		beforeSend: function () {
			btn.text('Loading...');
			btn.prop('disabled', 'disabled');
		},

		error: function (request, textStatus, errorThrown) {
			var jsonMsg = JSON.parse('{}');

			if (textStatus.indexOf('timeout') > -1 || textStatus.indexOf('abort') > -1) {
				jsonMsg["textStatus"] = textStatus;
				errorHandler(jsonMsg);
				return;
			}

			try {
				jsonMsg = JSON.parse(request.responseText);
			} catch (err) {
				console.error("Parse json failed. responseText:" + request.responseText);
			}

			jsonMsg["textStatus"] = textStatus;
			errorHandler(jsonMsg);
		},

		success: function (data) {
			successHandler(data);
		},

		complete: function () {
			btn.text(oldText);
			btn.prop('disabled', '');
		}
	});
}


/**
 * 从服务器获取所有的分类信息，然后将结果设置为window.allCategories
 * @param {获取分类的url} getAllCategoriesUrl 
 */
function ajaxCategories(getAllCategoriesUrl, forceUpdate = false) {
	if (!forceUpdate && window.allCategories != undefined && window.allCategories.length > 0)
	{
		return;
	}

    $.ajax({
        url: getAllCategoriesUrl,
        type: "GET",
        dataType: "json",
        async: false,
        headers: {
            Accept: "application/json",
        },
        beforeSend: function () {
            console.log("before sending....");
        },

        error: function (request, textStatus, errorThrown) {
            var jsonMsg = JSON.parse('{}');

            if (textStatus.indexOf('timeout') > -1 || textStatus.indexOf('abort') > -1) {
                jsonMsg["textStatus"] = textStatus;
                console.error(jsonMsg);
                return;
            }

            try {
                jsonMsg = JSON.parse(request.responseText);
            } catch (err) {
                console.error("Parse json failed. responseText:" + request.responseText);
            }

            jsonMsg["textStatus"] = textStatus;
            console.error(jsonMsg);
        },

        success: function (data) {
            window.allCategories = data;
        },

        complete: function () {
            console.log("complete....");
        }
    });
}



/**
 * 准备博客顶部的菜单栏
 */
function prepareNavmenu()
{
	ajaxCategories("/getAllCategoies");
	if (undefined === window.allCategories || window.allCategories.length == 0)
	{
		console.warn("Error or empty categories:" + window.allCategories);
		return;
	}

	var nav = new NavMenuBuilder(window.allCategories);
	nav.build();
}



$(document).ready(function (event) {
	prepareNavmenu();
});
