new Vue({
	el: '#app',
	data: {
		commentInfo:{
			account:'',
			password:'',
			userId:'',
			bookId:'',
			commentContent:'',
			score:''
		},
		bookrackInfo:{
			account:'',
			password:'',
			bookId:'',
			userId:'',
			chapterId:'',
			chapterTable:''
		},
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
		myComment: '',
		myCommentRate: '',
		value: 4.0,
		tabs: 'directory',
		input: '',
		bookDetails: '',
		bookChapter: [],
		bookComment: [],
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
		addComment: function(){
			var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
			if(novelUser==null||novelUser==''){
				this.loginDialog=true;
				this.$message.error('您还未登陆!!');
			} else if(this.myComment==''||this.myCommentRate==0){
				this.$message.warning("您的评论还没有完成！！");
			}else{
				var _this=this;
				this.commentInfo.account=this.loginUser.account;
				this.commentInfo.password=this.loginUser.password;
				this.commentInfo.userId=this.loginUser.id;
				this.commentInfo.bookId=this.bookDetails.id;
				this.commentInfo.commentContent=this.myComment;
				this.commentInfo.score=this.myCommentRate;
				$.ajax({
					type:"post",
					url:"http://localhost/comment/addComment",
					async:true,
					contentType:'application/json',
					data: JSON.stringify(_this.commentInfo),
					dataType:'json',
					success: function(data){
						if(data.success){
							_this.myComment='';
							_this.myCommentRate=0;
							//评论
							$.ajax({
								type:"get",
								url:"http://localhost/comment/selectCommentByBookId/"+_this.bookDetails.id,
								async:true,
								dataType:'json',
								success: function(data){
									if(data.success){
										_this.bookComment=data.data;
										for (var i=0;i< _this.bookComment.length;i++) {
											if(_this.bookComment[i].time!=null)
											_this.bookComment[i].time=_this.bookComment[i].time.substring(0,19).replace('T',' ');
										}
									}
								}
							});
							_this.$message.success(data.message);
						}else{
							_this.$message.error(data.message);
						}
					}
				});
			}
		},
		putBookrack: function(){
			var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
			if(novelUser==null||novelUser==''){
				this.loginDialog=true;
				this.$message.error('您还未登陆!!');
			}else{
				var _this=this;
				this.bookrackInfo.account=this.loginUser.account;
				this.bookrackInfo.password=this.loginUser.password;
				this.bookrackInfo.userId=this.loginUser.id;
				this.bookrackInfo.bookId=this.bookDetails.id;
				this.bookrackInfo.chapterId=1;
				this.bookrackInfo.chapterTable=this.bookDetails.chapterTable;
				$.ajax({
					type:"post",
					url:"http://localhost/bookrack/addBookrack",
					async:true,
					contentType:'application/json',
					data: JSON.stringify(_this.bookrackInfo),
					dataType:'json',
					success: function(data){
						if(data.success){
							_this.$message.success(data.message);
						}else{
							_this.$message.error(data.message);
						}
					}
				});
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
		jumpChapter: function(chapterId){
			window.open('chapter.html'+'#'+this.bookDetails.chapterTable+'/'+chapterId);
		}
	},
	created: function(){
		var bookHash='';
		bookHash=window.location.hash.substring(1);
//		console.log(searchHash);
		bookHash= decodeURI(bookHash);
		var _this=this;
		if(bookHash != ''){
			//小说信息
			$.ajax({
				type:"get",
				url:"http://localhost/novel/selectBookDetails/"+bookHash,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.bookDetails=data.data;
						//小说目录
						$.ajax({
							type:"get",
							url:"http://localhost/novel/selectBookCatalog/"+_this.bookDetails.chapterTable,
							async:true,
							dataType:'json',
							success: function(data){
								if(data.success){
									_this.bookChapter=data.data;
								}
							}
						});
						//评论
						$.ajax({
							type:"get",
							url:"http://localhost/comment/selectCommentByBookId/"+bookHash,
							async:true,
							dataType:'json',
							success: function(data){
								if(data.success){
									_this.bookComment=data.data;
									for (var i=0;i< _this.bookComment.length;i++) {
										if(_this.bookComment[i].time!=null)
										_this.bookComment[i].time=_this.bookComment[i].time.substring(0,19).replace('T',' ');
									}
								}
							}
						});
					}
				}
			});
			
		}
		var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
		var novelCookie=JSON.parse(unescape(getCookie('novelUser')));
		if(novelUser!=null&&novelUser!=''){
			this.loginUser=novelUser;
			_this.isLogin=true;
		}else if(novelCookie!=null&&novelCookie!=''){
			this.loginUser=novelCookie;
			_this.isLogin=true;
		}
		var ip='';
		ip=returnCitySN["cip"];
		if(bookHash=='')
			bookHash=0;
		$.ajax({
			type:"get",
			url:"http://localhost/novel/addHeat/"+bookHash+'/'+ip,
			async:true,
			dataType:'json',
			success: function(data){
				
			}
		});
	}
});