$(document).ready(function (event) {

    var wrapperDivIdPrefix = "categoryWrapperDiv";


    //读取所有的类别
    function loadCategories() {
        var getAllCategoriesUrl = "/getAllCategoies"
        ajaxCategories(getAllCategoriesUrl);

        var parentDiv = $("#categoryDiv");
        if (window.allCategories === undefined) {
            console.error("Query categories failed!");
            clearCategoryAndSetError(parentDiv);
            return;
        }

        var categoryPlaceHolder = $("#categoryPlaceHolder");
        var selected = categoryPlaceHolder.html().split(",");

        removeAllSelect(parentDiv);
        resetCategorySelect(parentDiv, window.allCategories, 0, selected);
        //设置当前blog的category
        //setBlogCategory();
    }

    /**
     * 重置category选择框的入口，此为生成parent的category
         <div id = "topCategory" class="form-group category-form">
                            <select class="form-control">
                                <option>1</option>
                            </select>
                        </div>
        
                        //selectedArray: 在渲染select的时候，应该自动selected的value
     **/
    function resetCategorySelect(parentDiv, category = window.allCategories, selectIndex = 0, selectedArray = []) {
        if (undefined === category || category.length == 0) {
            return;
        }

        var selectPrefix = "select";
        var header = "<div id = '" + wrapperDivIdPrefix + selectIndex + "' class='form-group category-form'>";
        header += "<select id=" + selectPrefix + selectIndex + " name=select" + selectIndex + " class='form-control'>";
        var options = "";
        var childSelect = $(parentDiv.find('select')[selectIndex]);
        var defaultCategory = undefined;

        var shouldSelected = selectedArray[selectIndex];
        if (shouldSelected != undefined)
        {
            shouldSelected = parseInt(shouldSelected);
        }

        category.forEach(function (curValue, index) {
            if (defaultCategory === undefined) {
                //默认取当前第一个
                if (shouldSelected === undefined)
                {
                    defaultCategory = curValue.children;
                }
                else if(shouldSelected === curValue.id)
                {
                    defaultCategory = curValue.children;    
                }
                else
                {
                    ;
                }
            }
            
            //如果当前需要选中的为undefined的话，那么默认选取第一个，否则的话，选取正确的value
            if (shouldSelected === undefined)
            {
                selected = (0 == index ? "selected" : "");
            }
            else
            {
                selected = (shouldSelected === curValue.id ? "selected" : "");
            }
            options += genOption(curValue, selected);
        });

        footer = "</select>" + "</div>";

        //select存在
        if (undefined === childSelect || childSelect.length == 0) {
            parentDiv.append(header + options + footer);
            //绑定事件
            $("#" + selectPrefix + selectIndex).on("change", function (event) {
                var curTarget = $(event.target);
                var parentIdString = curTarget.parent().attr('id');
                var curSelecIndex = parseInt(parentIdString.substring(parentIdString.length - 1));

                var coordinate = [];
                var allSelect = $("#categoryDiv").find('select');
                var index = 0;

                allSelect.each(function () {
                    //说明已经到目标了
                    if (index > curSelecIndex) {
                        return;
                    }
                    index++;
                    var selectedIndex = $(this).get(0).selectedIndex;
                    var option = $(this).find('option')[selectedIndex];
                    var tmpId = $(option).attr('id');
                    coordinate.push(tmpId.substring(tmpId.length - 1));

                });
                
                //根据select的坐标来查找其名下的children
                var children = findCurChildren(coordinate);
                if (undefined === children || 0 == children.length) {
                    return;
                }

                //更新后面的select
                resetCategorySelect($("#categoryDiv"), children, curSelecIndex + 1);
                //清空多余的select
                removeSelectAfterIndex(0);
            });
        }
        else {
            childSelect.css("display", "");
            childSelect.children('option').remove();
            childSelect.append(options);
        }
        resetCategorySelect(parentDiv, defaultCategory, ++selectIndex, selectedArray);
    }

    /**
     * 清理掉多余的index
     * @param {index} index 
     */
    function removeSelectAfterIndex(index) {
        //now. do nothing here.
        return;
    }

    /**
     * 根据坐标在window.allcategories中查找chilren
     * @param {chilren的坐标} coordinate 
     */
    function findCurChildren(coordinate, index = 0, curArray = window.allCategories) {
        var children = undefined;
        curArray.forEach(function (value) {
            var id = value['id']
            if (id == coordinate[index]) {
                children = value['children'];
            }
        });

        if (index == coordinate.length - 1) {
            return children;
        }

        return findCurChildren(coordinate, ++index, children);
    }

    /**
     * 重置category选择框，并根据当前的请求重新生成
     **/
    function genOption(curCategory, selected = "") {
        var id = curCategory['id'];
        var value = curCategory['name'];
        var template = "<option id=" + id + " name=" + id + " value=" + id + " " + ("" == selected ? "" : "selected") + ">"
            + value + "</option>";
        return template;
    }


    //生成category的div，为category select的最近父div
    function genCategoryDiv(id) {
        var template = "<div id = '" + 'category' + id + "' class='form-group category-form'>";
        template += "<select id = '" + 'categorySelect' + id + "'" + "name = 'categorySelect" + id + "'" + " class='form-control'>";
        return template;
    }

    //清除已有的select标志，并设置error相关的信息
    function clearCategoryAndSetError(parentDiv) {
        if (undefined === parentDiv) {
            console.error("ParentDiv undefined. Please contant the administrator!");
            return;
        }

        removeAllSelect(parentDiv);
        var errorSpan = "<span style='color:red' id='" + wrapperDivIdPrefix + '100' + "'>error</span>";
        parentDiv.append(errorSpan);
    }

    //移除掉所有的select，仅保留内容为类别的span
    function removeAllSelect(parentDiv) {
        if (undefined === parentDiv) {
            console.error("ParentDiv undefined. Please contant the administrator!");
            return;
        }

        var children = parentDiv.find("[id^='" + wrapperDivIdPrefix + "']");
        children.each(function () {
            $(this).remove();
        });

    }


    /**
     * 设置当前blog的category
     */
    function setBlogCategory()
    {
        var categoryPlaceHolder = $("#categoryPlaceHolder");
        var categories = categoryPlaceHolder.html().split(",");
        console.log("categories:" + categories);

        categories.forEach(function(item, index){
            var s = $("#select" + index);
            s.val(item);
        });
    }
   


    //自调用代码，做一些初始化工作
    (function () {
        loadCategories();
    })();

    //提交博客更改的请求
    $('#editBlogBtn').click(
        function (event) {
            event.preventDefault();

            if (checkBlog()) {
                var form = $("#editBlogForm");
                //此处需要拼装传送给服务器的内容
                var dataJson = constructFormData(blogEditor);

                submitFormAsync(form, errorHandler,
                    succHandler, dataJson);
            }
        });

    /**
     * 检查各个参数是否合法
     */
    function checkBlog() {
        var blogTitle = $('#blogTitle').val().trim();
        var blogContent = $('#blogContent').html().trim();
        if (blogTitle.length == 0) {
            new PopupMessageBox("标题不能为空！", "ERROR").showBoxShortTime();
            return false;
        }

        if (blogContent <= 10) {
            new PopupMessageBox("博客内容太短！", "ERROR").showBoxShortTime();
            return false;
        }

        var children =  $("#categoryDiv").find("[id^='" + wrapperDivIdPrefix + "']");
        var valid = true;
        children.each(function () {
            var selected = $(this).find('select')[0].selectedIndex;
            if (parseInt(selected) < 0) {
                valid = false;
            }
        });

        if (!valid) {
            new PopupMessageBox("尚未选择所属类别，请检查！", "ERROR").showBoxShortTime();
        }
        return true;
    }

    /**
     * 拼装form中的数据，返回json格式的数据
     */
    function constructFormData()
    {
        var result = {};
        result['blogTitle'] = $('#blogTitle').val().trim();
        var blogContent = blogEditor.getHTML();
        result['blogContent'] = blogContent;

        var children =  $("#categoryDiv").find("[id^='" + wrapperDivIdPrefix + "']");
        var index = 0;
        children.each(function () {
            var selected = $(this).find('select')[0].selectedIndex;
            var selectedOptionId = $($(this).find('option')[parseInt(selected)]).attr('id');
            console.log('selecedOptionId:' + selectedOptionId);
            result['select' + index] = selectedOptionId;
            ++index;
        });

        return JSON.stringify(result);
    }
})