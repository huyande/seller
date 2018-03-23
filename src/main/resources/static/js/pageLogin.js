(function(w,d,u){
	var loginForm = util.get('loginForm');
	if(!loginForm){
		return;
	}
	var userName = loginForm['userName'];
	var password = loginForm['password'];
	var rememberMe = loginForm['rememberMe'];
	var isSubmiting = false;
	var loading = new Loading();
	var page = {
		init:function(){
			loginForm.addEventListener('submit',function(e){
				if(!isSubmiting && this.check()){
					var value1 = userName.value;
					// TODO[checked] 测试方便没有MD5加密，后期修改
					var passwordMD5 = md5(password.value);
					isSubmiting = true;
					loading.show();

                    var form = new FormData();
                    console.log(userName.value);
                    console.log(passwordMD5);
                    console.log(rememberMe.value);
                    form.append("userName", userName.value);
                    form.append("password", passwordMD5);
                    form.append("rememberMe", rememberMe.value);
                    form.enctype = "multipart/form-data";

                    var xhr = new XMLHttpRequest();
                    xhr.open("post", "/api/login", true);
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            loading.hide();
                            var path = xhr.responseText;
                            location.href = path;
                        } else {
                        	var message = xhr.responseText;
                            console.log('message'+message);
                            loading.result(message);
                            isSubmiting = false;
                        }
                    };
                    xhr.send(form);
				}
			}.bind(this),false);
			[userName,password].forEach(function(item){
				item.addEventListener('input',function(e){
					item.classList.remove('z-err');
				}.bind(this),false);
			}.bind(this));
		},
		check:function(){
			var result = true;
			[
				[userName,function(value){return value == ''}],
				[password,function(value){return value == ''}]
			].forEach(function(item){
				var value = item[0].value.trim();
				if(item[1](value)){
					item[0].classList.add('z-err');
					result = false;
				}
				item[0].value = value;
			});
			return result;
		}
	};
	page.init();
})(window,document);