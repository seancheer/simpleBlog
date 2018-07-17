<!DOCTYPE html>
<html lang="zh-CN">

<#include "template/header.ftl">
<link rel="stylesheet" type='text/css' href="css/editormd.css" />
<link rel="stylesheet" type='text/css' href="css/new-blog.css" />
<script src="js/editor/editormd.min.js"></script>
<script src="js/newBlog.js"></script>
    
<body class="home">

    <div id="la-wrapper" style="border-color:#000000;">
		<#include "template/blogHeaderMenu.ftl">
		<#include "template/banner.ftl">
        <div class="style_breadcrumbs">
            <div class="container">
                <div class="row">
                    <div class="col-md-6">
                    </div>
                    <div class="col-md-6 breadcrumb_items">

                    </div>
                </div>
            </div>
        </div>


        <section id="passage-content" class="" role="main">
            <form id="newBlogForm" name="newBlogForm" role="form" action="/createNewBlog" method="POST">
                <div class='blod-editor-container'>
                    <!--title-->
                    <div class='title-div'>
                        <span class='title-span'>标题：</span>
                        <input id='blogTitle' name='blogTitle' class='form-control title-input' type='text' placeholder="请输入标题">
                    </div>
                    <div id="blogEditorDiv">
                        <textarea id='blogContent' name='blogContent' style="display:none;" placeholder="请输入内容"></textarea>
                    </div>

                    <script type="text/javascript">
                        var newBlogEditor;
                        $(function () {
                            newBlogEditor = editormd("blogEditorDiv", {
                                width: "90%",
                                height: 640,
                                syncScrolling: "single",
                                path: "js/editor/lib/",
                                saveHTMLToTextarea : true
                            });

                        });
                    </script>


                    <!-- category -->
                    <div id="categoryDiv" class='category-div'>
                        <span class='category-span'>类别：</span>
                      
                    </div>

                    <!-- submit btn-->
                    <div class='submit-div'>
                        <button id='newBlogBtn' class='btn btn-info submit-btn' type='submit'>提交</button>
                    </div>
                </div>
            </form>
        </section>

      
 	<#include "template/footer.ftl">
    </div>
   <#include "template/popup-hint.ftl">
</body>
</html>