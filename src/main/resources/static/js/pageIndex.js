(function(w,d,u){
	var plist = util.get('plist');
	if(!plist){
		return;
	}
	var layer = new Layer();
	var loading = new Loading();
	var page = {
		init:function(){
			plist.addEventListener('click',function(e){
				var ele = e.target;
				var delId = ele.dataset && ele.dataset.del;
				if(delId){
					this.ondel(delId);
					return;
				}
			}.bind(this),false);
		},
		ondel:function(id){
			layer.reset({
				content:'确定要删除该内容吗？',
				onconfirm:function(){
					layer.hide();
					loading.show();

                    var form = new FormData();
                    form.append('commodityId', id);
                    form.enctype = "multipart/form-data";

                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "/commodity/api/delete", true);
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            loading.result('删除成功');
                            location.href = '/';
                        }else {
                        	loading.result('删除失败');
                            // 跳转到主页
                            location.href = xhr.responseText;
						}
                    };
                    xhr.send(form);

				}.bind(this)
			}).show();
		},
		delItemNode:function(id){
			var item = util.get('p-'+id);
			if(item && item.parentNode){
				item.parentNode.removeChild(item);
			}
		}
	};
	page.init();
})(window,document);