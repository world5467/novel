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
		classifications: [{
			className: "仙侠",
			classImgColor: "img/xianxia.png",
			classImg: "img/xianxia.png",
			classImg1: "img/xianxia1.png",
			isChangeColor: false
		}, {
			className: "武侠",
			classImgColor: "img/wuxia.png",
			classImg: "img/wuxia.png",
			classImg1: "img/wuxia1.png",
			isChangeColor: false
		}, {
			className: "玄幻",
			classImgColor: "img/xuanhuan.png",
			classImg: "img/xuanhuan.png",
			classImg1: "img/xuanhuan1.png",
			isChangeColor: false
		}, {
			className: "灵异",
			classImgColor: "img/lingyi.png",
			classImg: "img/lingyi.png",
			classImg1: "img/lingyi1.png",
			isChangeColor: false
		}, {
			className: "历史",
			classImgColor: "img/lishi.png",
			classImg: "img/lishi.png",
			classImg1: "img/lishi1.png",
			isChangeColor: false
		}, {
			className: "都市",
			classImgColor: "img/dushi.png",
			classImg: "img/dushi.png",
			classImg1: "img/dushi1.png",
			isChangeColor: false
		}, {
			className: "游戏",
			classImgColor: "img/youxi.png",
			classImg: "img/youxi.png",
			classImg1: "img/youxi1.png",
			isChangeColor: false
		}, {
			className: "科幻",
			classImgColor: "img/kehuan.png",
			classImg: "img/kehuan.png",
			classImg1: "img/kehuan1.png",
			isChangeColor: false
		}, {
			className: "言情",
			classImgColor: "img/yanqing.png",
			classImg: "img/yanqing.png",
			classImg1: "img/yanqing1.png",
			isChangeColor: false
		}, {
			className: "军事",
			classImgColor: "img/junshi.png",
			classImg: "img/junshi.png",
			classImg1: "img/junshi1.png",
			isChangeColor: false
		}],
		recommends: [{
			recommendImg: 'img/混天记.jpeg',
			recommendHref: ''
		}, {
			recommendImg: 'img/极品农民.jpeg',
			recommendHref: ''
		}, {
			recommendImg: 'img/我家的妖狐大人.jpeg',
			recommendHref: ''
		}, {
			recommendImg: 'img/赘婿神医.jpeg',
			recommendHref: ''
		}],
		popularBook:[],
		heatRanking: [],
		collectRanking: [],
		endRanking: [],
		commentRanking: [],
		newestbooks: [],
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
		clickClassification: function(classfi){
			window.location.href= 'classification.html#'+classfi;
		},
		search:function(){},
		jumpBookDetails: function(bookId){
			window.open('bookdetails.html'+'#'+bookId);
		}
	},
	created: function(){
		var _this=this;
		//小说推荐
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBook/6",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.popularBook=data.data;
					for (var i=0; i<_this.popularBook.length; i++) {
						if(_this.popularBook[i].bookDescription.length>148){
							_this.popularBook[i].bookDescription = _this.popularBook[i].bookDescription.substr(0,148);
							
//							console.log(_this.popularBook[i].bookDescription.length)
						}
					}
				}
			}
		});
		//热度榜
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBook/10",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.heatRanking=data.data;
				}
			}
		});
		//收藏榜
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBook/10",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.collectRanking=data.data;
				}
			}
		});
		//完本帮
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBookEnd/10",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.endRanking=data.data;
				}
			}
		});
		//评论榜
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectHeatBook/10",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.commentRanking=data.data;
				}
			}
		});
		//最新入库
		$.ajax({
			type:"get",
			url:"http://localhost/novel/selectNewestBook/10",
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.newestbooks=data.data;
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
	},
});