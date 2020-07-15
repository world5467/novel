new Vue({
	el: '#app',
	data: {
		checked: false,
		loginDialog: false,
		registerDialog: false,
		isLogin: false,
		loginInfo: {
			account: '',
			password: ''
		},
		loginUser:{
			id:'',
			account: '',
			password: '',
			username:'',
			email:'',
			sex:'',
			birthday:'',
			headPortrait:''
		},
		registerInfo: {
			account: '',
			password: '',
			againPassword:'',
			email:''
		},
		user: {
			id:'',
			account: '',
			password: '',
			username:'',
			email:'',
			sex:'',
			birthday:'',
			headPortrait:''
		},
		input: '',
		popularBooks: [],
		endBooks: [],
		serialBooks: [],
		rules: {
			account: [
				{required: true, message: '请输入账号', trigger: 'blur'},
				{min: 6, max: 12, pattern: /^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9]{6,12}$/, message: '账号为 6 到12 位数字或字母组合', trigger: 'blur'},
			],
			password:[
				{required: true, message: '请输入密码', trigger: 'blur'},
				{min: 6, max: 16, pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,16}$/, message: '输入6 到16 位字母、数字或者符号（不含空格），且至少包含其中两种', trigger: 'blur'},
			],
			againPassword: [
				{validator: function(rule, value, callback){
        			if (value == '') {
         		 		return callback(new Error('请再次输入密码'));
        			} else if (value !== document.getElementById("registerPassword").value) {
          				return callback(new Error('两次输入密码不一致!'));
        			} else {
          				callback();
        			}
      			}, trigger: 'blur' }
			],
			email: [
				{required: true, type:'email', message: '请输入邮箱', trigger: 'blur'},
			]
		},

	},
	methods: {
		login: function() {
			if(this.loginInfo.account!=''&&this.loginInfo.password!=''){
				this.loginInfo.password= hex_md5(this.loginInfo.password);
			}
			var _this=this;
			//登录验证
			$.ajax({
				type:"post",
				url:'http://localhost/users/login',
				async:true,
				contentType:'application/json',
				data: JSON.stringify(_this.loginInfo),
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message({
							message: '登陆成功！！',
							type: 'success' 
						});
						_this.loginUser=data.data;
						_this.loginDialog=false;
						sessionStorage.setItem("novelUser",JSON.stringify(data.data));
						_this.isLogin=true;
						if(_this.checked==true){
							setCookie('novelUser',JSON.stringify(data.data));
						}
					}else{
						_this.$message.error('登陆失败！！');
					}
				}
			});
			this.loginInfo.password='';
		},
		resgister: function(){
			this.user.account=this.registerInfo.account;
			this.user.password=this.registerInfo.password;
			this.user.email=this.registerInfo.email;
			if(this.user.password!=''){
				this.user.password=hex_md5(this.user.password);
			}
			var _this=this;
			//注册账户
			$.ajax({
				type:"post",
				url:'http://localhost/users/addAccount',
				async:true,
				contentType:'application/json',
				data: JSON.stringify(_this.user),
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message({
							message: data.message,
							type: 'success' 
						});
						_this.registerDialog=false;
						_this.loginInfo.account=_this.registerInfo.account;
						_this.loginInfo.password=_this.registerInfo.password;
						_this.loginDialog=true;
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		logOut: function(){
			this.isLogin=false;
			delCookie('novelUser');
			sessionStorage.removeItem('novelUser');
			this.loginInfo.password='';
		},
		changeClassImg: function(index) {
			this.classifications[index].classImgColor = this.classifications[index].classImg1;
			this.classifications[index].isChangeColor = true;
		},
		changeClassImg1: function(index) {
			this.classifications[index].classImgColor = this.classifications[index].classImg;
			this.classifications[index].isChangeColor = false;
		},
		jumpBookDetails: function(bookId){
			window.open('bookdetails.html'+'#'+bookId);
		},
	},
	created: function(){
		var classHash=window.location.hash.substring(1);
//		console.log(classHash);
		classHash= decodeURI(classHash);
//		console.log(classHash+'    '+decodeURI(classHash));
		var classification='';
		switch (classHash){
			case '武侠': classification=classHash
				break;
			case '仙侠': classification=classHash
				break;
			case '玄幻': classification=classHash
				break;
			case '灵异': classification=classHash
				break;
			case '历史': classification=classHash
				break;
			case '都市': classification=classHash
				break;
			case '游戏': classification=classHash
				break;
			case '科幻': classification=classHash
				break;
			case '言情': classification=classHash
				break;
			case '军事': classification=classHash
				break;
			default: classification='仙侠';window.location.hash='仙侠'
				break;
		}
//		console.log(classification);
		var _this=this;
		//推荐
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBook/6/"+classification,
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.popularBooks=data.data;
				}
			}
		});
		//完本
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectClassBookEnd/6/"+classification,
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.endBooks=data.data;
				}
			}
		});
		//连载
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectClassBookSerial/6/"+classification,
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.serialBooks=data.data;
				}
			}
		});
		var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
		var novelCookie=JSON.parse(unescape(getCookie('novelUser')));
		if(novelUser!=null&&novelUser!=''){
			this.loginUser=novelUser;
			_this.isLogin=true;
		}else if(novelCookie!=null&&novelCookie!=''){
			this.loginUser=novelCookie;
			_this.isLogin=true;
		}
	}
});