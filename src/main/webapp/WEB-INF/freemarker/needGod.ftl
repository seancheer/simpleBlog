<!DOCTYPE html>
<html lang="zh-CN">

<#include "template/header.ftl">
<script src="js/needGod.js"></script>
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
            <!-- <div class="pwd-container vertical-center">
              <div class='pwd-input-container'><input type='text' placeholder='请输入通行证'></div>
              <div class='enter-container'><button class='btn btn-info'>确认</button></div>
          </div> -->
            <div class="pwd-container vertical-center">
            
                <form id="needGodForm" class="bs-example bs-example-form" role="form" action="/godEntrance" method="POST" >
                    <div class="row">
                        <div class="input-group pwd-input-container">
                            <input id="key" name="key" type="text" placeholder="请输入key" class="form-control" />
                            <span class="input-group-btn" >
                                <button id="godBtn" class="btn btn-default" type="submit">
                                    Go Now !
                                </button>
                            </span>
                        </div>
                    </div>
                </form>
                
            </div>
        </section>


	<#include "template/footer.ftl">
    </div>
   <#include "template/popup-hint.ftl">
</body>
</html>