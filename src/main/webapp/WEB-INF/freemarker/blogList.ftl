<!DOCTYPE html>
<html lang="zh-CN">

<#include "template/header.ftl">
<link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/god-toolbar.css' type='text/css' media='all' />

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


        <section id="blog-content-area" class="blog-contents" role="main">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 la-blog-content" role="main">
                    <#macro blogLoop>
					  <#if (blogList?? && blogList?size > 0)>
						<#list blogList as blog>
						
						<#local blogTitle=blog.title?trim />
						<#if (blogTitle?length > blogTitleLimit)>
						     <#local blogTitle=blogTitle?substring(0,blogTitleLimit) + "...">
						</#if>
						
						<#local blogContent=blogSummary(blog.content?trim) />
						<#if (blogContent?length > blogContentLimit)>
							 <#local blogContent=blogContent?substring(0,blogContentLimit) + "...">
						</#if>
                        <article class="blogright  post-450 post type-post status-publish format-standard hentry category-40 category-mind tag-24">
                            <header class="entry-header">
                                <h2 class="entry-title">
                                    <a href="/blog?blogId=${blog.id}" title="${blogTitle}" rel="bookmark">
                                    ${blogTitle} </a>
                                </h2>
                                <h5>
                                    <div class="entry-meta">
                                        <span class="posted-on">
                                            <span class="lr_blog_entry_head">发表于:&nbsp;</span>
                                            <time class="entry-date published" datetime="${blog.createTime}">${blog.createTime}</time>
                                        </span>
                                        <span class="byline">
                                            <span class="lr_blog_entry_head">By:&nbsp;</span>
                                            <span class="entry-date published">李江涛</span>
                                        </span>

                                    </div>
                                    <!-- .entry-meta -->
                                </h5>
                            </header>
                            <!-- .entry-header -->
                            <div class="entry-content clearfix" style="word-break: break-all">
                                <p class="entry-summary">${blogContent}</p>
                            </div>

                            <footer class="summary-entry-meta">
                            </footer>

                            <div class='clear'></div>
                            <!-- 操作按钮 -->
                            <div class="god-toolbar">
                                <span>阅读(${blog.readCount})</span>
                                <span>评论(${blog.commentCount})</span>
                                <a href="/editBlog?blogId=${blog.id}">编辑</a>
                                <a href="#">删除</a>
                                <a href="#">彻底删除</a>
                            </div>
                            <div class='clear'></div>
                            <!-- .entry-meta -->
                            <div class="blog_seperator"></div>
                        </article>
                        </#list>
                      <#else>
                        <h1>No blog here!</h1>
					  </#if>
					  
					  <#if (totalPage > 1)>
                        <div class='page_pagination'>
                        <#if (categoryId??)>
                        	<#local categoryUrl="&categoryId=${categoryId}">
                        <#else>
                        	<#local categoryUrl="">
                        </#if>
                        <#list 1..totalPage as p>
                            <#if (p == page)>
                            	<span class='current'>${p}</span>
                            <#else>
                            	<a href='blogList?page=${p}${categoryUrl}' class='inactive'>${p}</a>
                            </#if>
                        </#list>
                        </div>
                      </#if>
                    </#macro>
					<@blogLoop/>
                    </div>

                    <div class="col-md-4">
                        <aside id="la-right" role="complementary">
                            <div id="newBlogContainer" class="new-blog-container">
                                <a id="newBlog" href="/newBlog">新的博客</a>
                            </div>
                            <div class="div-placeholder"></div>
                        </aside>
                    </div>

                     <div class="col-md-4">
                        <aside id="la-right" role="complementary">
                                <a id="newBlog" href="/newBlog">通知</a>
                            <div class="div-placeholder"></div>
                        </aside>
                    </div>

                </div>
            </div>
        </section>


	<#include "template/footer.ftl">
    </div>
   <#include "template/popup-hint.ftl">
   
</body>
</html>