package com.sdut.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.BookInfo;
import com.sdut.novel.bean.Comment;
import com.sdut.novel.bean.CommentInfoResult;
import com.sdut.novel.bean.LoginInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.bean.User;
import com.sdut.novel.bean.UserInfo;
import com.sdut.novel.bean.UserInfoResult;
import com.sdut.novel.dao.UserDao;
import com.sdut.novel.service.ManageService;
@RestController
@RequestMapping("/manage")
@CrossOrigin
public class ManageController{
	
	@Autowired
	ManageService service;
	String password="666666";
	//管理员密码验证
	@PostMapping("/verifyPassword")
	public Result verifyPassword(@RequestBody LoginInfo loginInfo) {
		return service.verifyPassword(loginInfo);
	}
	
	//查询全部图书
	@GetMapping("/selectAllBook")
	public Result selectAllBook(){
		return service.selectAllBook();
	}
	
	//查询图书
	@GetMapping("/selectBook/{search}")
	public Result selectBook(@PathVariable String search){
		return service.selectBook(search);
	}
	
	//修改图书信息
	@PostMapping("/updateBook")
	public Result updateBook(@RequestBody BookInfo bookInfo){
		if(bookInfo.getPassword().equals(password)) {
			Book book=new Book();
			book.setId(bookInfo.getId());
			book.setBookName(bookInfo.getBookName());
			book.setType(bookInfo.getType());
			book.setState(bookInfo.getState());
			book.setWriter(bookInfo.getWriter());
			return service.updateBook(book);
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
		
	}
	
	//删除图书
	@PostMapping("/deleteBook")
	public Result deleteBook(@RequestBody BookInfo bookInfo){
		
		if(bookInfo.getPassword().equals(password)) {
			return service.deleteBook(bookInfo.getId(), bookInfo.getChapterTable());
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
	}
	
	//查询全部用户
	@GetMapping("/selectAllUser")
	public Result selectAllUser(){
		return service.selectAllUser();
	}
	
	//按账号查询用户
	@GetMapping("/selectUserByAccount/{account}")
	public Result selectUserByAccount(@PathVariable String account){
		return service.selectUserByAccount(account);
	}
	
	//按昵称查询用户
	@GetMapping("/selectUserByUsername/{username}")
	public Result selectUserByUsername(@PathVariable String username){
		return service.selectUserByUsername(username);
	}
	
	//修改用户信息
	@PostMapping("/updateUser")
	public Result updateUser(@RequestBody UserInfoResult userInfoResult){
		if(userInfoResult.getPassword().equals(password)) {
			User user=new User();
			user.setId(userInfoResult.getId());
			user.setUsername(userInfoResult.getUsername());
			user.setHeadPortrait(userInfoResult.getHeadPortrait());
			return service.updateUser(user);
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
	}
	
	//删除用户
	@PostMapping("/deleteUser")
	public Result deleteUser(@RequestBody UserInfoResult userInfoResult){
		if(userInfoResult.getPassword().equals(password)) {
			return service.deleteUser(userInfoResult.getId());
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
	}
	
	//查询全部评论
	@GetMapping("/selectAllComment")
	public Result selectAllComment(){
		return service.selectAllComment();
	}
	
	//按账号查询评论
	@GetMapping("/selectCommentByAccount/{account}")
	public Result selectCommentByAccount(@PathVariable String account){
		return service.selectCommentByAccount(account);
	}
	
	//按内容查询评论
	@GetMapping("/selectComment/{text}")
	public Result selectComment(@PathVariable String text){
		return service.selectComment(text);
	}
	
	//修改评论
	@PostMapping("/updateComment")
	public Result updateComment(@RequestBody CommentInfoResult commentInfoResult){
		if(commentInfoResult.getPassword().equals(password)) {
			Comment comment=new Comment();
			comment.setId(commentInfoResult.getId());
			comment.setCommentContent(commentInfoResult.getCommentContent());
			return service.updateComment(comment);
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
	}
	
	//删除评论
	@PostMapping("/deleteComment")
	public Result deleteComment(@RequestBody CommentInfoResult commentInfoResult){
		if(commentInfoResult.getPassword().equals(password)) {
			return service.deleteComment(commentInfoResult.getId());
		}else {
			Result result=new Result();
			result.setSuccess(false);
			result.setMessage("密码验证失败");
			return result;
		}
		
	}
}
