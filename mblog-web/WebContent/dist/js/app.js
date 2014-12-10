(function($){
	var uuid = 0;
	
	var App = function() {};
	App.browser = function() {
       var u = navigator.userAgent;
       return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }();
	
	this.App = App;
	
	// 注册上传插件
	jQuery.fn.extend({
		upload: function(url, callback){
			var self = this;
			var frameId = "ajax-pload-" + (++uuid);
			var $frame = $("<iframe id='" + frameId + "' name='" + frameId + "' style='position:absolute; top:-9999px;display:none;'/>").appendTo("body");
			var $form = $("<form action='" + url + "' target='" + frameId + "' method='post' enctype='multipart/form-data' style='display:none;'/>");
			
			var nfile = self.clone(true);
			self.attr("id", "uf-" + uuid);
			self.before(nfile);
			self.detach().appendTo($form);
			
			$form.appendTo("body");
			
			$form.submit(function() {
				$frame.load(function() {
					var contents = $frame.contents().get(0);
					var data = $(contents).find('body').html();
					data = (data ? $.parseJSON(data) : {});
					$form.remove();
					setTimeout( function() {
						$frame.remove();
						if (callback) {
							callback.call(self, data);
						}
					}, 1000);
				});
				return true;
			}).submit();
			return this;
		}
	});
	
	jQuery.extend({
		/**
	     * 使用参数格式化字符串
	     * source：字符串模式 abcdef{0}-{1}，
	     * params：参数数组，参数序号对应模式中的下标
	     */
        format: function(source, params) {
	    	if ( arguments.length == 1 ) 
	    		return function() {
	    			var args = $.makeArray(arguments);
	    			args.unshift(source);
	    			return $.format.apply( this, args );
	    		};
	    	if ( arguments.length > 2 && params.constructor != Array  ) {
	    		params = $.makeArray(arguments).slice(1);
	    	}
	    	if ( params.constructor != Array ) {
	    		params = [ params ];
	    	}
	    	$.each(params, function(i, n) {
	    		source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
	    	});
	    	return source;
	    },
	});
	
})(window.jQuery);

(function($){
	// 显示模板
	var cm_template = '<li>' +
						'<article class="comment clr">' +
							'<div class="comment-author">' +
								'<img src="../dist/images/ava.png" class="avatar" height="45" width="45">' +
							'</div>' +
							'<div class="comment-details clr">' +
								'<header class="comment-meta">' +
									'<cite class="fn"><a href="#" uid="{0}" class="url">{1}</a></cite>' +
									'<span class="comment-date">' + 
										'<time datetime="2014-03-02T00:50:16+00:00">{3}</time>' +
									'</span>' +
								'</header>' +
								'<div class="comment-content entry clr">' +
									'<p>{2}</p>' +
								'</div>' +
							'</div>' +
						'</article>' +
						'</li>';
	
	var Comment = function() {};
	Comment.prototype = {
		options: {
			loadUrl: null,
			postUrl: null,
			maxItems: 10
		},
		/**
		 * 构造方法
		 */
		init: function (container, opts){
			this.options = $.extend({}, this.options, opts);
			this.container = container;
			this.load();
		},
		/**
		 * 评论加载
		 */
		load: function() {
			var context = this;
			var opts = this.options;
			
			var cid = $('#contentId').val();
			var pid = $('#projectId').val();
			jQuery.getJSON(opts.loadUrl, {contentId: cid, projectId: pid}, function(ret){
				if(ret){
					$('#c_count').text(ret.totalCount);
	          		jQuery.each(ret.results, function(i, n) {
	          			context.render(n);
	          		});
	          	}
			});
		},
		
		reload: function() {
			this.container.empty();
			this.load();
		},
		
		post: function(content) {
			var context = this;
			var opts = this.options;
			var ret = false;
			
			if (!content || content.length < 1) {
				alert('内容不能为空');
			}
			if (content.length > 255) {
				alert('内容过长，请输入255以内个字符');
			}
			var cid = $('#contentId').val();
			var pid = $('#projectId').val();
			
			jQuery.ajax({
				url: opts.postUrl, 
				data: {contentId: cid, projectId: pid, content: content},
				dataType: "json",
				type :  "POST",
				cache : false,
				error : function(i, g, h) {
					alert("发生错误");
				},
				success: function(ret){
					if(ret){
						alert(ret.data);
						
						if (ret.success) {
							context.reload();
							ret = true;
						}
					}
	          	}
			});
		}, 
		/*
		 * 元素渲染
		 */
		render: function(data) {
			var item = jQuery.format(cm_template, data.owner.id, data.owner.username, data.content, data.created);
			this.container.append(item);
		}
	};
	
	this.Comment = Comment;

})(window.jQuery);