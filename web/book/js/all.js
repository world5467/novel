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
		books:[],
		classifications: [{
			optionName: "全部",
			isOption: true,
		}, {
			optionName: "仙侠",
			isOption: false,
		}, {
			optionName: "武侠",
			isOption: false,
		}, {
			optionName: "玄幻",
			isOption: false,
		}, {
			optionName: "灵异",
			isOption: false,
		}, {
			optionName: "历史",
			isOption: false,
		}, {
			optionName: "都市",
			isOption: false,
		}, {
			optionName: "游戏",
			isOption: false,
		}, {
			optionName: "科幻",
			isOption: false,
		}, {
			optionName: "言情",
			isOption: false,
		}, {
			optionName: "军事",
			isOption: false,
		}],
		amounts: [{
			optionName:'全部',
			isOption: true,
		},{
			optionName:'50万以下',
			isOption: false,
		},{
			optionName:'50-100万字',
			isOption: false,
		},{
			optionName:'100-200万字',
			isOption: false,
		},{
			optionName:'200万字以上',
			isOption: false,
		}],
		times: [{
			optionName: '全部',
			isOption :true,
		},{
			optionName: '七日内',
			isOption :false,
		},{
			optionName: '一月内',
			isOption :false,
		},{
			optionName: '六月内',
			isOption :false,
		}],
		states: [{
			optionName: '全部',
			isOption :true,
		},{
			optionName: '连载中',
			isOption :false,
		},{
			optionName: '已完本',
			isOption :false,
		}],
		classOption:'全部',
		amountOption:'全部',
		timeOption:'全部',
		stateOption:'全部',
		total: 0,
		pageBooks: [],
		currentPage : 1,
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
		changeOption: function(options,optionName){
			for (var i=0;i<options.length;i++) {
				options[i].isOption=false;
				if(options[i].optionName==optionName){
					options[i].isOption=true;
				}
			}
			window.location.replace(window.location.href.toString().replace(window.location.hash, '')+'#'+this.classOption+'/'+this.amountOption+'/'+this.timeOption+'/'+this.stateOption);
			
			var _this=this;
			//小说
			$.ajax({
				type:"get",
				url:'http://localhost/novel/SelectAllBook/'+this.classOption+'/'+this.amountOption+'/'+this.timeOption+'/'+this.stateOption,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.books=data.data;
						_this.total=_this.books.length;
						_this.pageBooks=_this.books.slice(0,10);
						_this.currentPage=1;
					}
				}
			});
		},
		currentChange:function(val){
			this.pageBooks=this.books.slice((val-1)*10,val*10);
			this.currentPage=val;
		},
	},
	created: function(){
		var allBookHash='';
		allBookHash=window.location.hash.substring(1);
		if(allBookHash==''){
			allBookHash='全部/全部/全部/全部'
		}
//		console.log(searchHash);
		allBookHash= decodeURI(allBookHash);
		allBookHash=allBookHash.split('/');
		this.classOption=allBookHash[0];
		this.changeOption(this.classifications,this.classOption);
		this.amountOption=allBookHash[1];
		this.changeOption(this.amounts,this.amountOption);
		this.timeOption=allBookHash[2];
		this.changeOption(this.times,this.amountOption);
		this.stateOption=allBookHash[3];
		this.changeOption(this.states,this.stateOption);
		var _this=this;
		//小说
		$.ajax({
			type:"get",
			url:'http://localhost/novel/SelectAllBook/'+this.classOption+'/'+this.amountOption+'/'+this.timeOption+'/'+this.stateOption,
			async:true,
			dataType:'json',
			success: function(data){
				if(data.success){
					_this.books=data.data;
					_this.total=_this.books.length;
					_this.pageBooks=_this.books.slice(0,10);
					_this.currentPage=1;
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