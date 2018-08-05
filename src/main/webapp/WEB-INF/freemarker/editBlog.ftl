<!DOCTYPE html>
<html lang="zh-CN">

<#include "template/header.ftl">
<link rel="stylesheet" type='text/css' href="css/editormd.css" />
<link rel="stylesheet" type='text/css' href="css/new-blog.css" />
<script src="js/editor/editormd.min.js"></script>
<script src="js/editBlog.js"></script>
    
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
            <form id="editBlogForm" name="editBlogForm" role="form" action="/updateBlog?blogId=${curBlog.id}" method="POST">
                <div class='blod-editor-container'>
                    <!--title-->
                    <div class='title-div'>
                        <span class='title-span'>标题：</span>
                        <input id='blogTitle' name='blogTitle' class='form-control title-input' type='text' value='${curBlog.title}'>
                    </div>
                    <div id="blogEditorDiv">
                        <textarea id='blogContent' name='blogContent' style="display:none;" placeholder="">
                        ${curBlog.content}
                        </textarea>
                    </div>

                    <script type="text/javascript">
                        var blogEditor;
                        $(function () {
                            blogEditor = editormd("blogEditorDiv", {
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
                        <!-- 加载category -->
                        <span id="categoryPlaceHolder" style="display:none">${curBlogCategoryIds}</span>
                    </div>

                    <!-- submit btn-->
                    <div class='submit-div'>
                        <button id='editBlogBtn' class='btn btn-info submit-btn' type='submit'>提交更改</button>
                    </div>
                </div>
            </form>
        </section>

      
 	<#include "template/footer.ftl">
    </div>
   <#include "template/popup-hint.ftl">
</body>
</html>