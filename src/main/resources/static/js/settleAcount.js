(function(w,d,u){
    var name = 'products';

    var $ = function(id){
        return document.getElementById(id);
    };

    // 传入JSON字符串
    var jsonText = $('jsonText').textContent;

    console.log(jsonText);

    var loading = new Loading();
    var layer = new Layer();
    $('Account').onclick = function(e){
        var ele = e.target;
        layer.reset({
            content:'确认购买吗？',
            onconfirm:function(){
                layer.hide();
                loading.show();

                var xhr = new XMLHttpRequest();
                xhr.open("post", "/orders/api/pay", true);
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        loading.result('购买成功',function(){location.href = '/orders/page/purchased';});
                    } else {
                        // 购买失败，跳转到服务器返回的页面
                        loading.result(xhr.responseText);
                    }
                };
                xhr.send(jsonText);

            }.bind(this)
        }).show();
        return;
    };
    $('back').onclick = function(){
        location.href = $('returnUrl').textContent;
    }
})(window,document);