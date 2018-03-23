var $ = function(id){
    return document.getElementById(id);
};

var loading = new Loading();
var layer = new Layer();

$('plusNum').onclick = function(e){
    e = window.event || e;
    o = e.srcElement || e.target;
    var num = $('allNum').textContent;
    if(num > 0){
        num --;
        $('allNum').innerHTML = num;
    }else{
        alert("您没有购买任何商品");
    }
};

$('addNum').onclick = function(e){
    e = window.event || e;
    o = e.srcElement || e.target;
    var num = $('allNum').textContent;
    num ++;
    $('allNum').innerHTML = num;
};

$('add').onclick = function(e){
    var ele = e.target;
    var id = ele && ele.dataset.id;
    var num = $('allNum').innerHTML;

    var form = new FormData();
    form.append('commodityId', id);
    form.append('purchasedQuantity',num);
    console.log(document.cookie);
//		util.deleteCookie(name);
    e == window.event || e;
    layer.reset({
        content:'确认加入购物车吗？',
        onconfirm:function(){
            layer.hide();
            loading.show();

            var form = new FormData();
            form.append('commodityId', id);
            form.append('purchasedQuantity',num);
            console.log(id);
            console.log(num);
            form.enctype = "multipart/form-data";

            var xhr = new XMLHttpRequest();
            xhr.open("post", "/orders/api/addshopcar", true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    loading.result('添加购物车成功',function(){location.href = '/orders/page/shoppingcar';});
                } else {
                    // 购买失败，跳转到服务器返回的页面
                    loading.result( xhr.responseText);
                }
            };
            xhr.send(form);
        }.bind(this)
    }).show();
    return;
};


