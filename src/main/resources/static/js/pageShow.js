(function(w,d,u){
	var showContent = util.get('showContent');
	if(!showContent){
		return;
	}
	var loading = new Loading();
	var layer = new Layer();
	var page = {
		init:function(){
			showContent.addEventListener('click',function(e){
				var ele = e.target;
				var buy = ele && ele.dataset.buy;
				if(buy){
					layer.reset({
						content:'确认购买本内容吗？',
						onconfirm:function(){
							layer.hide();
							loading.show();

                            var form = new FormData();
                            form.append('commodityId', buy);
                            form.enctype = "multipart/form-data";

                            var xhr = new XMLHttpRequest();
                            xhr.open("post", "/orders/api/addshopcar", true);
                            xhr.onload = function () {
                                if (xhr.status === 200) {
                                    loading.result('购买成功',function(){location.href = '/orders/page/shoppingcar';});
                                } else {
                                	// 购买失败，跳转到服务器返回的页面
                                    loading.result("购买失败",function(){location.href = xhr.responseText;});
                                }
                            };
                            xhr.send(form);

						}.bind(this)
					}).show();
					return;
				}
			}.bind(this),false);
		}
	};
	page.init();
})(window,document);