<!DOCTYPE html>
<html lang="zh-CN">
<#include "template/header.ftl">
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


        <section id="passage-content" class="passage-content" role="main">
            <div class="container">
                <div class="row">
                    <div id="passage-container" class="col-md-8 passage-container" role="main">
                        <article id="post-450" class="blogright  post-450 post type-post status-publish format-standard hentry category-40 category-mind tag-24">

                            <header class="entry-header">
                                <h2 class="entry-title">
                                    <a href="/blog?blogId=${blog.id}" title="${blog.title}" rel="bookmark">${blog.title} </a>
                                </h2>

                            </header>

                            <div class="passage clearfix">
                                <p>
                                ${renderContent(blog.content)}
                                </p>

                            </div>

                            <footer class="summary-entry-meta">
                            </footer>
                            <!-- .entry-meta -->
                            <div class="blog_seperator"></div>
                        </article>
                        <!-- #post-## -->






                    </div>

                </div>
            </div>
        </section>
      <#include "template/footer.ftl">
    </div>
   <#include "template/popup-hint.ftl">
</body>
</html>