<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作汇报</title>
<title>jQuery和CSS3动感手风琴多级列表树菜单|DEMO_jQuery之家-自由分享jQuery、html5、css3的插件库</title>
	<link rel="stylesheet" type="text/css" href="css/normalize.css" />
	<link rel="stylesheet" type="text/css" href="css/indexDefault.css">
	<link rel='stylesheet prefetch' href='css/foundation.css'>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<script src='js/stopExecutionOnTimeout.js?t=1'></script>
	<script src='js/jquery-1.11.0.min.js'></script>
	<script src='js/jquery.velocity.min.js'></script>
	<script>
	;(function ($, window, document, undefined) {
	    if ($('ul.mtree').length) {
	        var collapsed = true;
	        var close_same_level = false;
	        var duration = 400;
	        var listAnim = true;
	        var easing = 'easeOutQuart';
	        $('.mtree ul').css({
	            'overflow': 'hidden',
	            'height': collapsed ? 0 : 'auto',
	            'display': collapsed ? 'none' : 'block'
	        });
	        var node = $('.mtree li:has(ul)');
	        node.each(function (index, val) {
	            $(this).children(':first-child').css('cursor', 'pointer');
	            $(this).addClass('mtree-node mtree-' + (collapsed ? 'closed' : 'open'));
	            $(this).children('ul').addClass('mtree-level-' + ($(this).parentsUntil($('ul.mtree'), 'ul').length + 1));
	        });
	        $('.mtree li > *:first-child').on('click.mtree-active', function (e) {
	            if ($(this).parent().hasClass('mtree-closed')) {
	                $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
	                $(this).parent().addClass('mtree-active');
	            } else if ($(this).parent().hasClass('mtree-open')) {
	                $(this).parent().removeClass('mtree-active');
	            } else {
	                $('.mtree-active').not($(this).parent()).removeClass('mtree-active');
	                $(this).parent().toggleClass('mtree-active');
	            }
	        });
	        node.children(':first-child').on('click.mtree', function (e) {
	            var el = $(this).parent().children('ul').first();
	            var isOpen = $(this).parent().hasClass('mtree-open');
	            if ((close_same_level || $('.csl').hasClass('active')) && !isOpen) {
	                var close_items = $(this).closest('ul').children('.mtree-open').not($(this).parent()).children('ul');
	                if ($.Velocity) {
	                    close_items.velocity({ height: 0 }, {
	                        duration: duration,
	                        easing: easing,
	                        display: 'none',
	                        delay: 100,
	                        complete: function () {
	                            setNodeClass($(this).parent(), true);
	                        }
	                    });
	                } else {
	                    close_items.delay(100).slideToggle(duration, function () {
	                        setNodeClass($(this).parent(), true);
	                    });
	                }
	            }
	            el.css({ 'height': 'auto' });
	            if (!isOpen && $.Velocity && listAnim)
	                el.find(' > li, li.mtree-open > ul > li').css({ 'opacity': 0 }).velocity('stop').velocity('list');
	            if ($.Velocity) {
	                el.velocity('stop').velocity({
	                    height: isOpen ? [
	                        0,
	                        el.outerHeight()
	                    ] : [
	                        el.outerHeight(),
	                        0
	                    ]
	                }, {
	                    queue: false,
	                    duration: duration,
	                    easing: easing,
	                    display: isOpen ? 'none' : 'block',
	                    begin: setNodeClass($(this).parent(), isOpen),
	                    complete: function () {
	                        if (!isOpen)
	                            $(this).css('height', 'auto');
	                    }
	                });
	            } else {
	                setNodeClass($(this).parent(), isOpen);
	                el.slideToggle(duration);
	            }
	            e.preventDefault();
	        });
	        function setNodeClass(el, isOpen) {
	            if (isOpen) {
	                el.removeClass('mtree-open').addClass('mtree-closed');
	            } else {
	                el.removeClass('mtree-closed').addClass('mtree-open');
	            }
	        }
	        if ($.Velocity && listAnim) {
	            $.Velocity.Sequences.list = function (element, options, index, size) {
	                $.Velocity.animate(element, {
	                    opacity: [
	                        1,
	                        0
	                    ],
	                    translateY: [
	                        0,
	                        -(index + 1)
	                    ]
	                }, {
	                    delay: index * (duration / size / 2),
	                    duration: duration,
	                    easing: easing
	                });
	            };
	        }
	        if ($('.mtree').css('opacity') == 0) {
	            if ($.Velocity) {
	                $('.mtree').css('opacity', 1).children().css('opacity', 0).velocity('list');
	            } else {
	                $('.mtree').show(200);
	            }
	        }
	    }
	}(jQuery, this, this.document));
	$(document).ready(function () {
		$.ajax(function(){
			url:"${ctx}/dept/deptAction!findDeptUser",
			success:function(data){
				var dt = eval('('+data+')');
				console.log(data);
			}
		});
	    var mtree = $('ul.mtree');
	    mtree.wrap('<div class=mtree-demo></div>');
	    var skins = [
	        'bubba',
	        'skinny',
	        'transit',
	        'jet',
	        'nix'
	    ];
	    mtree.addClass(skins[3]);
	    //$('body').prepend('<div class="mtree-skin-selector"><ul class="button-group radius"></ul></div>');
	    var s = $('.mtree-skin-selector');
	    $.each(skins, function (index, val) {
	        s.find('ul').append('<li><button class="small skin">' + val + '</button></li>');
	    });
	    s.find('ul').append('<li><button class="small csl active">Close Same Level</button></li>');
	    s.find('button.skin').each(function (index) {
	        $(this).on('click.mtree-skin-selector', function () {
	            s.find('button.skin.active').removeClass('active');
	            $(this).addClass('active');
	            mtree.removeClass(skins.join(' ')).addClass(skins[index]);
	        });
	    });
	    s.find('button:first').addClass('active');
	    s.find('.csl').on('click.mtree-close-same-level', function () {
	        $(this).toggleClass('active');
	    });
	});
	</script>
</head>
<body>
	<div class="htmleaf-container">
		<div class="htmleaf-content bgcolor-3">
			<!-- This is mtree list -->
			<ul class=mtree>
			  <li><a href="#">Africa</a>
			    <ul>
			      <li><a href="#">Algeria</a></li>
			      <li><a href="#">Marocco</a></li>
			      <li><a href="#">Libya</a></li>
			      <li><a href="#">Somalia</a></li>
			      <li><a href="#">Kenya</a></li>
			      <li><a href="#">Mauritania</a></li>
			      <li><a href="#">South Africa</a></li>
			    </ul>
			  </li>
			  <li><a href="#">America</a>
			    <ul>
			      <li><a href="#">North-America</a>
			        <ul>
			          <li><a href="#">Canada</a></li>
			          <li><a href="#">USA</a>
			            <ul>
			              <li><a href="#">New York</a></li>
			              <li><a href="#">California</a>
			                <ul>
			                  <li><a href="#">Los Angeles</a></li>
			                  <li><a href="#">San Diego</a></li>
			                  <li><a href="#">Sacramento</a></li>
			                  <li><a href="#">San Francisco</a></li>
			                  <li><a href="#">Bakersville</a></li>
			                </ul>
			              </li>
			              <li><a href="#">Lousiana</a></li>
			              <li><a href="#">Texas</a></li>
			              <li><a href="#">Nevada</a></li>
			              <li><a href="#">Montana</a></li>
			              <li><a href="#">Virginia</a></li>
			            </ul>
			          </li>
			        </ul>
			      </li>
			      <li><a href="#">Middle-America</a>
			        <ul>
			          <li><a href="#">Mexico</a></li>
			          <li><a href="#">Honduras</a></li>
			          <li><a href="#">Guatemala</a></li>
			         </ul>
			      </li>
			      <li><a href="#">South-America</a>
			        <ul>
			          <li><a href="#">Brazil</a></li>
			          <li><a href="#">Argentina</a></li>
			          <li><a href="#">Uruguay</a></li>
			          <li><a href="#">Chile</a></li>
			        </ul>
			      </li>
			    </ul>
			  </li>
			  <li><a href="#">Asia</a>
			    <ul>
			      <li><a href="#">China</a></li>
			      <li><a href="#">India</a></li>
			      <li><a href="#">Malaysia</a></li>
			      <li><a href="#">Thailand</a></li>
			      <li><a href="#">Vietnam</a></li>
			      <li><a href="#">Singapore</a></li>
			      <li><a href="#">Indonesia</a></li>
			      <li><a href="#">Mongolia</a></li>
			   </ul>
			  </li>
			  <li><a href="#">Europe</a>
			    <ul>
			      <li><a href="#">North</a>
			        <ul>
			          <li><a href="#">Norway</a></li>
			          <li><a href="#">Sweden</a></li>
			          <li><a href="#">Finland</a></li>
			        </ul>
			      </li>
			      <li><a href="#">East</a>
			        <ul>
			          <li><a href="#">Romania</a></li>
			          <li><a href="#">Bulgaria</a></li>
			          <li><a href="#">Poland</a></li>
			        </ul>
			      </li>
			      <li><a href="#">South</a>
			        <ul>
			          <li><a href="#">Italy</a></li>
			          <li><a href="#">Greece</a></li>
			          <li><a href="#">Spain</a></li>
			        </ul>
			      </li>
			      <li><a href="#">West</a>
			        <ul>
			          <li><a href="#">France</a></li>
			          <li><a href="#">England</a></li>
			          <li><a href="#">Portugal</a></li>
			        </ul>
			      </li>
			   </ul>
			  </li>
			  <li><a href="#">Oceania</a>
			    <ul>
			      <li><a href="#">Australia</a></li>
			      <li><a href="#">New Zealand</a></li>
			    </ul>
			  </li>
			  <li><a href="#">Arctica</a></li>
			  <li><a href="#">Antarctica</a></li>
			</ul>
		</div>
	</div>
	
</body>
</html>