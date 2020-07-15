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
		rankingBooks:'',
		rankingColor: [false,false,false,false,false,false,false,false,false,false,false,false,false],
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
		rankingChange:function(rankingClass){
			for(var i=0;i<14;i++){
				this.rankingColor[i]=false;
			}
			window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#'+rankingClass);
			switch (rankingClass){
				case '热度': rankingURL='http://localhost/novel/selectHeatBook/20'; this.rankingColor[0]=true;
					break;
				case '收藏': rankingURL=''; this.rankingColor[1]=true;
					break;
				case '完结': rankingURL='http://localhost/novel/selectHeatBookEnd/20'; this.rankingColor[2]=true;
					break;
				case '评论': rankingURL=''; this.rankingColor[3]=true;
					break;
				case '仙侠':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[4]=true;
					break;
				case '武侠':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[5]=true;
					break;
				case '玄幻':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[6]=true;
					break;
				case '灵异':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[7]=true;
					break;
				case '历史':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[8]=true;
					break;
				case '都市':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[9]=true;
					break;
				case '游戏':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[10]=true;
					break;
				case '科幻':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[11]=true;
					break;
				case '言情':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[12]=true;
					break;
				case '军事':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingClass; this.rankingColor[13]=true;
					break;
				default: rankingURL='http://localhost/novel/selectHeatBook/20';window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#热度');
					break;
			}
			var _this=this;
			//榜单内容
			$.ajax({
				type:"get",
				url:rankingURL,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.rankingBooks=data.data;
					}
				}
			});
		},
		jumpBookDetails: function(bookId){
			window.open('bookdetails.html'+'#'+bookId);
		},
	},
	created: function(){
		var _this=this;
		var rankingHash='';
		rankingHash=window.location.hash.substring(1);
		rankingHash= decodeURI(rankingHash);
		var rankingURL='';
		switch (rankingHash){
			case '热度': rankingURL='http://localhost/novel/selectHeatBook/20'; _this.rankingColor[0]=true;
				break;
			case '收藏': rankingURL=''; _this.rankingColor[1]=true;
				break;
			case '完结': rankingURL='http://localhost/novel/selectHeatBookEnd/20'; _this.rankingColor[2]=true;
				break;
			case '评论': rankingURL=''; _this.rankingColor[3]=true;
				break;
			case '仙侠':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[4]=true;
				break;
			case '武侠':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[5]=true;
				break;
			case '玄幻':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[6]=true;
				break;
			case '灵异':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[7]=true;
				break;
			case '历史':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[8]=true;
				break;
			case '都市':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[9]=true;
				break;
			case '游戏':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[10]=true;
				break;
			case '科幻':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[11]=true;
				break;
			case '言情':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[12]=true;
				break;
			case '军事':  rankingURL='http://localhost/novel/selectHeatBook/20/'+rankingHash; _this.rankingColor[13]=true;
				break;
			default: rankingURL='http://localhost/novel/selectHeatBook/20';window.location.hash='热度';; _this.rankingColor[0]=true;
				break;
		}
		
		var _this=this;
		//榜单内容
		$.ajax({
			type:"get",
			url:rankingURL,
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.rankingBooks=data.data;
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