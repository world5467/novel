new Vue({
	el: '#app',
	data: {
		updPassword:{
			password:'',
			newPassword:'',
			againNewPassword:''
		},
		userInfo:{
			account:'',
			password:'',
			newPassword:'',
			username:'',
			email:'',
			sex:'',
			birthday:'',
			headPortrait:'',
		},
		passwordDialog: false,
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
			],
		},
		userRules:{
			password:[
				{required: true, message: '请输入密码', trigger: 'blur'},
				{min: 6, max: 16, pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,16}$/, message: '输入6 到16 位字母、数字或者符号（不含空格），且至少包含其中两种', trigger: 'blur'},
			],
			newPassword:[
				{required: true, message: '请输入密码', trigger: 'blur'},
				{min: 6, max: 16, pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,16}$/, message: '输入6 到16 位字母、数字或者符号（不含空格），且至少包含其中两种', trigger: 'blur'},
			],
			againNewPassword:[
				{validator: function(rule, value, callback){
        			if (value == '') {
         		 		return callback(new Error('请再次输入新密码'));
        			} else if (value !== document.getElementById("newPassword").value) {
          				return callback(new Error('两次输入密码不一致!'));
        			} else {
          				callback();
        			}
      			}, trigger: 'blur' }
			]
		},
	},
	methods: {
		updateUserInfo:function(){
			if(this.userInfo.newPassword==''||this.userInfo.newPassword==null){
				this.userInfo.newPassword=this.userInfo.password;
			}
			var _this=this;
			//修改信息
			$.ajax({
				type:"post",
				url:'http://localhost/users/updateUser',
				async:true,
				contentType:'application/json',
				data: JSON.stringify(_this.userInfo),
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message({
							message: '修改成功！！',
							type: 'success' 
						});
						_this.loginUser.password=_this.userInfo.newPassword;
						_this.loginUser.username=_this.userInfo.username;
						_this.loginUser.email=_this.userInfo.email;
						_this.loginUser.sex=_this.userInfo.sex;
						_this.loginUser.birthday=_this.userInfo.birthday;
						_this.loginUser.headPortrait=_this.userInfo.headPortrait;
						sessionStorage.setItem("novelUser",JSON.stringify(_this.loginUser));
						if(JSON.parse(unescape(getCookie('novelUser')))!=''&&JSON.parse(unescape(getCookie('novelUser')))!=null){
							setCookie('novelUser',JSON.stringify(_this.loginUser));
						}
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		updatePassword: function(){
			var password= hex_md5(this.updPassword.password);
			reg=/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,16}$/;
			if(password==this.loginUser.password){
				if(reg.test(this.updPassword.newPassword)){
					if(this.updPassword.newPassword==this.updPassword.againNewPassword){
						this.userInfo.newPassword=hex_md5(this.updPassword.newPassword);
						this.updPassword.password='';
						this.updPassword.newPassword='';
						this.updPassword.againNewPassword='';
						this.passwordDialog=false;
						this.$message.success('修改密码完成，请点击保存确认修改！！');
					}else{
						this.$message.error('两次密码不一致！！');
					}
				}else{
					this.$message.error('密码格式错误！！');
				}
			}else{
				this.$message.error('原密码错误！！');
			}
		},
		handleSuccess:function(response, file, fileList){
			if(file.response.success){
				this.userInfo.headPortrait=file.response.data;
			}
		},
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
						_this.getUserInfo();
						if(_this.checked==true){
							setCookie('novelUser',JSON.stringify(data.data));
						}
					}else{
						_this.$message.error('登陆失败！！');
					}
				}
			});
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
		getUserInfo: function(){
			this.userInfo.account=this.loginUser.account;
			this.userInfo.password=this.loginUser.password;
			this.userInfo.username=this.loginUser.username;
			this.userInfo.email=this.loginUser.email;
			this.userInfo.sex=this.loginUser.sex;
			this.userInfo.birthday=this.loginUser.birthday;
			this.userInfo.headPortrait=this.loginUser.headPortrait;
		},
	},
	created: function(){
		var _this=this;
		var rankingHash='';
		rankingHash=window.location.hash.substring(1);
		rankingHash= decodeURI(rankingHash);
		var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
		var novelCookie=JSON.parse(unescape(getCookie('novelUser')));
		if(novelUser!=null&&novelUser!=''){
			this.loginUser=novelUser;
			_this.isLogin=true;
			
		}else if(novelCookie!=null&&novelCookie!=''){
			this.loginUser=novelCookie;
			_this.isLogin=true;
		}
		this.getUserInfo();
	}
});