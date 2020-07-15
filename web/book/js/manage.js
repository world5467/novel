new Vue({
	el: '#app',
	data: {
		bookResult: [],
		userResult:[],
		commentResult:[],
		isBook: true,
		isUser: false,
		isComment: false,
		bookDialog: false,
		userDialog: false,
		commentDialog: false,
		isLogin: false,
		loginInfo: {
			account: '',
			password: '',
		},
		bookInfo: {
			account: '',
			password: '',
			id: '',
			bookName: '',
			writer: '',
			bookDescription: '',
			state: '',
			cover: '',
			type: '',
			renewalTime: '',
			heat: '',
			score: '',
			amount: '',
			chapterTable: '',
		},
		userInfoResult: {
			account: '',
			password: '',
			id:'',
			username:'',
			email:'',
			sex:'',
			birthday:'',
			headPortrait:''
		},
		commentInfoResult: {
			account: '',
			password: '',
			id:'',
			userId: '',
			bookId: '',
			commentContent: '',
			score: '',
		},
		input: '',
		isVerify:true,
		resultIndex:'',
	},
	methods: {
		verifyPassword: function(){
			var _this=this;
			//密码验证
			$.ajax({
				type:"POST",
				url:'http://localhost/manage/verifyPassword',
				data: JSON.stringify(_this.loginInfo),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.isVerify=false;
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		deleteOpen: function(index,del) {
			this.$confirm('是否删除该项?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(function() {
				del(index);
			}).catch(function() {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		bookPage:function(){
			this.input='';
			this.isBook=true;
			this.isUser=false;
			this.isComment=false;
			this.selectAllBook();
		},
		userPage:function(){
			this.input='';
			this.isBook=false;
			this.isUser=true;
			this.isComment=false;
			this.selectAllUser();
		},
		commentPage:function(){
			this.input='';
			this.isBook=false;
			this.isUser=false;
			this.isComment=true;
			this.selectAllComment();
		},
		selectAllBook: function(){
			var _this=this;
			//全部小说
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectAllBook',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.bookResult=data.data;
					}
				}
			});
		},
		selectBook: function(){
			var _this=this;
			//全部小说
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectBook/'+_this.input,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.bookResult=data.data;
					}
				}
			});
		},
		selectAllUser: function(){
			var _this=this;
			//全部用户
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectAllUser',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.userResult=data.data;
					}
				}
			});
		},
		selectUserByAccount: function(){
			var _this=this;
			//全部用户
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectUserByAccount/'+_this.input,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.userResult=data.data;
					}
				}
			});
		},
		selectUserByUsername: function(){
			var _this=this;
			//全部用户
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectUserByUsername/'+_this.input,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.userResult=data.data;
					}
				}
			});
		},
		selectAllComment: function(){
			var _this=this;
			//全部评论
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectAllComment',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.commentResult=data.data;
					}
				}
			});
		},
		selectComment: function(){
			var _this=this;
			//全部评论
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectComment/'+_this.input,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.commentResult=data.data;
					}
				}
			});
		},
		selectCommentByAccount: function(){
			var _this=this;
			//全部评论
			$.ajax({
				type:"get",
				url:'http://localhost/manage/selectCommentByAccount/'+_this.input,
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.commentResult=data.data;
					}
				}
			});
		},
		updateBook: function(){
			var _this=this;
			//修改图书信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/updateBook',
				async:true,
				data: JSON.stringify(_this.bookInfo),
				contentType:'application/json',
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.bookResult[_this.resultIndex].bookName=_this.bookInfo.bookName;
						_this.bookResult[_this.resultIndex].writer=_this.bookInfo.writer;
						_this.bookResult[_this.resultIndex].state=_this.bookInfo.state;
						_this.bookResult[_this.resultIndex].type=_this.bookInfo.type;
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		updateUser: function(){
			var _this=this;
			//修改图书信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/updateUser',
				data: JSON.stringify(_this.userInfoResult),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.userResult[_this.resultIndex].account=_this.userInfoResult.account;
						_this.userResult[_this.resultIndex].username=_this.userInfoResult.username;
						_this.userResult[_this.resultIndex].headPortrait=_this.userInfoResult.headPortrait;
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		updateComment: function(){
			var _this=this;
			//修改评论信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/updateComment',
				data: JSON.stringify(_this.commentInfoResult),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.commentResult[_this.resultIndex].commentContent=_this.commentInfoResult.commentContent;
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		deleteBook: function(index){
			this.bookInfo.id=this.bookResult[index].id;
			this.bookInfo.chapterTable=this.bookResult[index].chapterTable;
			this.bookInfo.password=this.loginInfo.password;
			var _this=this;
			//删除小说信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/deleteBook',
				data: JSON.stringify(_this.bookInfo),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.bookResult.splice(index,1);
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		deleteUser: function(index){
			this.userInfoResult.id=this.userResult[index].id;
			this.userInfoResult.password=this.loginInfo.password;
			var _this=this;
			//删除用户信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/deleteUser',
				data: JSON.stringify(_this.userInfoResult),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.userResult.splice(index,1);
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		deleteComment: function(index){
			this.commentInfoResult.id=this.commentResult[index].id;
			this.commentInfoResult.password=this.loginInfo.password;
			var _this=this;
			//删除评论信息
			$.ajax({
				type:"post",
				url:'http://localhost/manage/deleteComment',
				data: JSON.stringify(_this.commentInfoResult),
				contentType:'application/json',
				async:true,
				dataType:'json',
				success: function(data){
					if(data.success){
						_this.$message.success(data.message);
						_this.commentResult.splice(index,1);
					}else{
						_this.$message.error(data.message);
					}
				}
			});
		},
		bookUpdate:function(index){
			this.bookInfo.id=this.bookResult[index].id;
			this.bookInfo.bookName=this.bookResult[index].bookName;
			this.bookInfo.writer=this.bookResult[index].writer;
			this.bookInfo.state=this.bookResult[index].state;
			this.bookInfo.type=this.bookResult[index].type;
			this.bookInfo.password=this.loginInfo.password;
			this.bookDialog=true;
			this.resultIndex=index;
		},
		bookUpdateSure:function(){
			this.bookDialog=false;
			this.updateBook();
		},
		userUpdate:function(index){
			this.userInfoResult.id=this.userResult[index].id;
			this.userInfoResult.account=this.userResult[index].account;
			this.userInfoResult.username=this.userResult[index].username;
			this.userInfoResult.headPortrait=this.userResult[index].headPortrait;
			this.userInfoResult.password=this.loginInfo.password;
			this.userDialog=true;
			this.resultIndex=index;
		},
		userUpdateSure:function(){
			this.userDialog=false;
			this.updateUser();
		},
		commentUpdate:function(index){
			this.commentInfoResult.id=this.commentResult[index].id;
			this.commentInfoResult.account=this.commentResult[index].account;
			this.commentInfoResult.username=this.commentResult[index].username;
			this.commentInfoResult.commentContent=this.commentResult[index].commentContent;
			this.commentInfoResult.password=this.loginInfo.password;
			this.commentDialog=true;
			this.resultIndex=index;
		},
		commentUpdateSure:function(){
			this.commentDialog=false;
			this.updateComment();
		}
	},
	created: function(){
		this.selectAllBook();
		this.selectAllUser();
		this.selectAllComment();
	}
});