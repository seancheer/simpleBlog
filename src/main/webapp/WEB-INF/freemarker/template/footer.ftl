<div class="content-footer-separator"></div>
	
<div id="lavish_footer" class="lavish_footer">
    <div class="container">
        <div style="border-bottom:1px solid #3C3C3C"></div>
        <div class="row">
            <div class="col-md-12">
            </div>
            <div class="col-md-12">
                <ul id="menu-pages" class="footer">
                    <li id="menu-item-280" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-280">
                        <a href="/blogList">关于</a>
                    </li>

                    </li>
                    <li id="menu-item-279" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-279">
                        <a href="/blogList">所有文章</a>
                    </li>

                </ul>
            </div>
            <div class="col-md-12">
                <div class="copyright">
                    <p>
                        Copyright &copy; 2017
                        <strong>李江涛</strong>. All rights reserved. </p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 提示框-->
<div name="modalContainer">
    <div class="modal fade" id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="infoModalLabel">default</h4>
                </div>
                <div class="modal-body">default</div>
                <div class="modal-footer">
                    <button type="button" name="btnCancel" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" name="btnOK" class="btn btn-primary">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

        //设置footer的位置
        function relocationFooter(init = false) {
            if (($(document.body).height() - $("#la-wrapper").height()) > 10) {
                $("#lavish_footer").css("position", "absolute");
                $("#lavish_footer").css("display", "block");
            }
            else {
                $("#lavish_footer").css("position", "");
                $("#lavish_footer").css("display", "block");
            }

            if (init)
            {
                 $("#lavish_footer").css("animation", "footer-appear 1s");
                 $("#lavish_footer").css("-webkit-animation", "footer-appear 1s");
            }
        };

        $(window).resize(function () {
            relocationFooter();
        });


        relocationFooter(true);
    })
</script>