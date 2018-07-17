
infoCss = {
    'background-color': '#2E86C1',
    'color': '#fff'
}

errorCss = {
    'background-color': '#CC3333',
    'color': '#fff'
}

warnCss = {
    'background-color': '#D4AC0D',
    'color': '#fff'
}

window.msgBoxType = {
    "INFO": infoCss,
    "ERROR": errorCss,
    "WARN": warnCss
}

var PopupMessageBox = function (msg, type) {
    this.prefix = "popupMsgBox";
    this.msg = msg === undefined ? "Unknown msg!" : msg;
    this.popupId = this.prefix + window.counter++;
    this.type = type === undefined ? "INFO" : type; //默认为信息提示框
    this.msgBox = undefined;
    this.jqueryTarget = undefined;
    this.boxCssStyle = window.msgBoxType[this.type];



    /**
      * 在html中增加一个popup
      * @param msg
      * @returns
      */
    this.createAndAppend = function () {
        text = "undefined";
        var popupTemplate = "<div " + 'id=\'' + this.popupId + "\' class='popup-container'> <div class='popup-content'>";
        popupTemplate = popupTemplate + text;
        popupTemplate = popupTemplate + "</div></div>";
        $("body").append(popupTemplate);
        return popupTemplate;
    }

    /**
     * 更新提示框的颜色
     */
    this.updateCssByType = function () {
        this.jqueryTarget.css(this.boxCssStyle);
    }.bind(this);

    /**
     * 设置popup中的提示信息 
     * @returns
     */
    this.setPopupContent = function () {
        this.jqueryTarget.html(this.msg);
    }.bind(this);


    /**
     * 显示popup
     * @returns
     */
    this.displayBox = function () {
        this.jqueryTarget.css('display', 'block');
        this.jqueryTarget.css('animation', 'popup-showup 1s 1');
    }

    /**
     * 显示MessageBox 2s
     */
    this.showBoxShortTime = function () {
        this.displayBox();
        setTimeout(function () {
            this.jqueryTarget.remove();
        }.bind(this), 2 * 1000);
    }.bind(this);


    /**
     * 显示MessageBox，不消失
     */
    this.showBoxForever = function () {
        this.displayBox();
    }.bind(this);


    /**
     * 构造函数
     */
    (function () {
        this.msgBox = this.createAndAppend();
        this.jqueryTarget = $('#' + this.popupId);
        this.setPopupContent();
        this.updateCssByType();
    }.bind(this))();
}