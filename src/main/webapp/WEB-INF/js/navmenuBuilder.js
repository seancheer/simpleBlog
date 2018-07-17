var NavMenuBuilder = function(categories)
{
    this.menus = categories;
    this.navContainer = $('#menuCategories');



    /**
     * 创建menu的入口
     */
    this.build = function()
    {
        this.buildInternal(this.menus);
    }.bind(this);

    /**
     * 创建navmenu的内部方法
     * @param {包含所有menu的数组} menus 
     */
    this.buildInternal = function(menus, index = 0)
    {
        if (undefined == menus || menus.length == 0)
        {
            return;
        }

        menus.forEach(function (curValue, index) {
            var html = this.parentLi + this.parentAHalfTop;
            html += curValue['name'];
            var children = curValue.children;
            var parentHref = '/blogList?categoryId=' + '1,' + curValue['id'];
            html = html.replace('###',parentHref);
            //修改为传入categoryId=1,2，第一位表示category的编号，第二位表示具体的id
            if (undefined === children || children.length == 0)
            {
                html += this.aCloseTag;
            }
            else
            {
                html += this.caret;
                html += this.aCloseTag;
                html += this.buildChildren(children,2);
            }

            html += this.liCloseTag;
            this.navContainer.append(html);
        }.bind(this));

    }.bind(this);

    /**
     * 创建children item
     */
    this.buildChildren = function(children, childrenIndex)
    {
        if (undefined == children || children.length == 0)
        {
            return "";
        }

        var html = this.childUl;
        children.forEach(function (curValue, index) {
            var child = this.childLiHalfTop + curValue['name'];
            var childHref = '/blogList?categoryId=' + childrenIndex + ','+ curValue['id'];
            child = child.replace('###',childHref);
            child += this.aCloseTag;
            child += this.liCloseTag;
            html += child;
        }.bind(this));

        html += this.ulCloseTag;
        return html;
    }.bind(this);
}

NavMenuBuilder.prototype.caret = '<b class="caret"></b>';
NavMenuBuilder.prototype.parentLi = '<li class="dropdown menu-item menu-item-type-taxonomy menu-item-object-category menu-item-447">';
NavMenuBuilder.prototype.parentAHalfTop = '<a href="###" class="dropdown-toggle"  data-toggle="dropdown" >';

NavMenuBuilder.prototype.aCloseTag = '</a>';
NavMenuBuilder.prototype.childUl = '<ul class="dropdown-menu dropdown-menu-custom">';
NavMenuBuilder.prototype.ulCloseTag = '</ul>';
NavMenuBuilder.prototype.liCloseTag = '</li>';
NavMenuBuilder.prototype.childLiHalfTop = '<li><a textScroll="scroll" href="###">';