new Vue({
	el: '#app',
	data: {
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
		num: 18,
		backgroundColor: 'rgb(248,243,233)',
		fontColor: '#000000',
		fontFamily:'微软雅黑',
		chapter: [],
		bookCatalog:[],
		bookId: 0,
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
			]
		},
	},
	methods: {
		updateBookrack: function(){
			this.bookrackInfo.account=this.loginUser.account;
			this.bookrackInfo.password=this.loginUser.password;
			this.bookrackInfo.userId=this.loginUser.id;
			this.bookrackInfo.bookId=this.bookId;
			this.bookrackInfo.chapterId=this.chapter.id;
			var _this=this;
			$.ajax({
				type:"post",
				url:"http://localhost/bookrack/updateBookrack",
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
						_this.updateBookrack();
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
		previousChapter: function(){
			var _this=this;
			var chapterHash='';
			chapterHash=window.location.hash.substring(1);
			chapterHash= decodeURI(chapterHash);
			chapterHash=chapterHash.split("/")
			for (var i=0;i<this.bookCatalog.length;i++) {
				if(this.chapter.id==this.bookCatalog[i].id){
					if (i>=1) {
						//章节内容
						$.ajax({
							type:"get",
							url:"http://localhost/novel/selectChapter/"+chapterHash[0]+'/'+_this.bookCatalog[i-1].id,
							async:true,
							dataType:'json',
							success: function(data){
								if(data.success){
									_this.chapter=data.data;
									_this.chapter.chapterContent=_this.chapter.chapterContent.split('\n');
									window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#'+chapterHash[0]+'/'+_this.bookCatalog[i-1].id);
									_this.updateBookrack();
								}
							}
						});
						window.scroll(0,0);
					} else{
						_this.$message({
							message: '这是第一章，已经不能再向前了！！',
          					type: 'warning'
        				});
					}
					break;
				}
			}
		},
		nextChapter: function(){
			var _this=this;
			var chapterHash='';
			chapterHash=window.location.hash.substring(1);
			chapterHash= decodeURI(chapterHash);
			chapterHash=chapterHash.split("/")
			for (var i=0;i<this.bookCatalog.length;i++) {
				if(this.chapter.id==this.bookCatalog[i].id){
					if (i<=this.bookCatalog.length-2) {
						//章节内容
						$.ajax({
							type:"get",
							url:"http://localhost/novel/selectChapter/"+chapterHash[0]+'/'+_this.bookCatalog[i+1].id,
							async:true,
							dataType:'json',
							success: function(data){
								if(data.success){
									_this.chapter=data.data;
									_this.chapter.chapterContent=_this.chapter.chapterContent.split('\n');
									window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#'+chapterHash[0]+'/'+_this.bookCatalog[i+1].id);
									_this.updateBookrack();
								}
							}
						});
						window.scroll(0,0);
					} else{
						_this.$message({
							message: '这是最新章节，已经不能再向后了！！',
          					type: 'warning'
        				});
					}
					break;
				}
			}
		},
		jumpChapter: function(chapterId){
			var _this=this;
			var chapterHash='';
			chapterHash=window.location.hash.substring(1);
			chapterHash= decodeURI(chapterHash);
			chapterHash=chapterHash.split("/");
			$('div.directoryWindow').fadeOut(500);
			//章节内容
			$.ajax({
				type:"get",
				url:"http://localhost/novel/selectChapter/"+chapterHash[0]+'/'+chapterId,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.chapter=data.data;
						_this.chapter.chapterContent=_this.chapter.chapterContent.split('\n');
						window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#'+chapterHash[0]+'/'+chapterId);
						_this.updateBookrack();
					}
				}
			});
			window.scroll(0,0);
		}
	},
	created: function(){
		var novelUser=JSON.parse(sessionStorage.getItem("novelUser"));
		var novelCookie=JSON.parse(unescape(getCookie('novelUser')));
		if(novelUser!=null&&novelUser!=''){
			this.loginUser=novelUser;
			this.isLogin=true;
		}else if(novelCookie!=null&&novelCookie!=''){
			this.loginUser=novelCookie;
			this.isLogin=true;
		} 
		
		var chapterHash='';
		chapterHash=window.location.hash.substring(1);
//		console.log(searchHash);
		chapterHash= decodeURI(chapterHash);
		chapterHash=chapterHash.split("/")
		var _this=this;
		if(chapterHash[0]!= ''&&chapterHash[1]!= ''){
			//章节内容
			$.ajax({
				type:"get",
				url:"http://localhost/novel/selectChapter/"+chapterHash[0]+'/'+chapterHash[1],
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.chapter=data.data;
						_this.chapter.chapterContent=_this.chapter.chapterContent.split('\n')
					}
				}
			});
			//目录
			$.ajax({
				type:"get",
				url:"http://localhost/novel/selectBookCatalog/"+chapterHash[0],
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.bookCatalog=data.data;
					}
				}
			});
			//根据表名查小说ID
			$.ajax({
				type:"get",
				url:"http://localhost/novel/selectBookByTable/"+chapterHash[0],
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.bookId=data.data;
						_this.updateBookrack();
					}
				}
			});
		}
	}
});