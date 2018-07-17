<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="profile" href="http://gmpg.org/xfn/11">
    <link rel="shortcut icon" href="" />
    <!-- 此处放置一个icon图标-->

    <title>那个人样子好怪</title>
    <link rel='dns-prefetch' href='//s.w.org' />
    <style type="text/css">
        img.wp-smiley,
        img.emoji {
            display: inline !important;
            border: none !important;
            box-shadow: none !important;
            height: 1em !important;
            width: 1em !important;
            margin: 0 .07em !important;
            vertical-align: -0.1em !important;
            background: none !important;
            padding: 0 !important;
        }
    </style>

    <!-- css -->
    <link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/content.css' type='text/css' media='all' />
    <link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/navmenu.css' type='text/css' media='all' />
    <link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/passage.css' type='text/css' media='all' />
    <link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/common.css' type='text/css' media='all' />
    <link rel='stylesheet' id='parent-theme-css-css' type='text/css' href='css/popup.css' type='text/css' media='all' />


    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="js/common.js"></script>
    <script src="js/popupMsgBox.js"></script>
    <script src="js/navmenuBuilder.js"></script>
    
</head>

<style type="text/css">
    body {
        font-size: 100%;
    }



    #la-banner {
        min-height: 150px !important;
        background-repeat: no-repeat;
    }



    /*.lavish_header {background-color:; }*/

    /*site title */

    #la-site-title a {
        color: #fff;
    }

    #la-logo-group,
    #la-text-group {
        padding: 5px 0px 5px 0px
    }

    /*Navmenu Customizer */

    #secondary-nav .nav-menu li a,
    #secondary-nav .nav-menu li.home a {
        color: #ffffff;
    }

    #secondary-nav .nav-menu li a:hover {
        color: #6c603c;
    }

    #secondary-nav ul.nav-menu ul a,
    #secondary-nav .nav-menu ul ul a {
        color: #ffffff;
    }

    #secondary-nav ul.nav-menu ul a:hover,
    #secondary-nav .nav-menu ul ul a:hover,
    #secondary-nav .nav-menu .current_page_item>a,
    #secondary-nav .nav-menu .current_page_ancestor>a,
    #secondary-nav .nav-menu .current-menu-item>a,
    #secondary-nav .nav-menu .current-menu-ancestor>a {
        color: #6c603c;
    }

    #secondary-nav ul.sub-menu .current_page_item>a,
    #secondary-nav ul.sub-menu .current_page_ancestor>a,
    #secondary-nav ul.sub-menu .current-menu-item>a,
    #secondary-nav ul.sub-menu .current-menu-ancestor>a {
        background-color: #d7c58c;
    }

    #secondary-nav ul.nav-menu li:hover>ul,
    #secondary-nav .nav-menu ul li:hover>ul {
        background-color: #c6b274;
        border-color: #707070;
    }

    #secondary-nav ul.nav-menu li:hover>ul li:hover {
        background-color: #d7c58c;
    }
</style>
<script type="text/javascript">
    (function ($) {
        $(document).ready(function () {
            var active = "";
            var scroll_number = "180";
            var top_activated = "1";
            if (top_activated == 1) {
                $('#la-banner').css({ "min-height": "110px" });
            }



            //if navigation menu is First One

            if (active == 1) {
                $(window).scroll(function () {

                    if ($(window).scrollTop() > scroll_number) {
                        $(".lavish_top").hide();
                        $(".lavish_header.header_two").css({ "position": "fixed", "right": "0px", "left": "0px", "top": "0px", "z-index": "400" });
                        $(".lavish_header.header_one").css({ "position": "fixed", "right": "0px", "left": "0px", "top": "0px", "z-index": "400" });
                        $(".lavish_header.header_two").css({ 'marginTop': '0px' });
                    }
                    else {
                        $(".lavish_top").show();
                        $(".lavish_header.header_two").css({ "position": "absolute", 'box-shadow': 'none', "width": "100%", "z-index": "400" });
                        $(".lavish_header.header_one").css({ "position": "relative", 'box-shadow': 'none', "width": "100%", "z-index": "400" });
                        $(".lavish_header.header_two").css({ 'marginTop': '15px' });
                    }
                });
            }




        });
    })(jQuery);

</script>

<style type="text/css">
    .blog .style_breadcrumbs,
    .archive .style_breadcrumbs,
    .category .style_breadcrumbs {
        display: none;
    }
</style>

<!-- Clean Archives Reloaded v3.2.0 | http://www.viper007bond.com/wordpress-plugins/clean-archives-reloaded/ -->
<style type="text/css">
    .car-collapse .car-yearmonth {
        cursor: s-resize;
    }
</style>
<script type="text/javascript">
    /* <![CDATA[ */
    jQuery(document).ready(function () {
        jQuery('.car-collapse').find('.car-monthlisting').hide();
        jQuery('.car-collapse').find('.car-monthlisting:first').show();
        jQuery('.car-collapse').find('.car-yearmonth').click(function () {
            jQuery(this).next('ul').slideToggle('fast');
        });
        jQuery('.car-collapse').find('.car-toggler').click(function () {
            if ('Expand All' == jQuery(this).text()) {
                jQuery(this).parent('.car-container').find('.car-monthlisting').show();
                jQuery(this).text('Collapse All');
            }
            else {
                jQuery(this).parent('.car-container').find('.car-monthlisting').hide();
                jQuery(this).text('Expand All');
            }
            return false;
        });
    });
		/* ]]> */
</script>

</head>
