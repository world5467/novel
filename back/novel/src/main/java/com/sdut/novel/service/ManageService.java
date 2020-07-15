package com.sdut.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.Comment;
import com.sdut.novel.bean.LoginInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.bean.User;
import com.sdut.novel.bean.UserInfo;
import com.sdut.novel.dao.ManageDao;
import com.sdut.novel.dao.NovelDao;

@Service
public class ManageService {

	@Autowired
	ManageDao dao;
	
	//验证密码
	public Result verifyPassword(LoginInfo loginInfo) {
		Result result=new Result();
		try {
			if (loginInfo.getPassword().equals("666666")) {
				result.setMessage("验证成功！！");
				result.setSuccess(true);
			} else {
				result.setMessage("验证失败！！");
				result.setSuccess(false);
			} 
		} catch (Exception e) {
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//查询全部图书
	public Result selectAllBook(){
		Result result=new Result();
		try {
			result.setData(dao.selectAllBook());
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//查询图书
	public Result selectBook(String search){
		Result result=new Result();
		try {
			result.setData(dao.selectBook(search));
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//修改图书信息
	public Result updateBook(Book book){
		Result result=new Result();
		try {
			dao.updateBook(book);
			result.setMessage("修改成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//删除图书
	public Result deleteBook(int bookId,String chapterTable){
		Result result=new Result();
		try {
			dao.deleteBook(bookId);
			dao.deleteBookrackByBook(bookId);
			dao.deleteCommentByBook(bookId);
			dao.deleteChapter(chapterTable);
			result.setMessage("删除成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//查询全部用户
	public Result selectAllUser(){
		Result result=new Result();
		try {
			result.setData(dao.selectAllUser());
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按账号查询用户
	public Result selectUserByAccount(String account){
		Result result=new Result();
		try {
			result.setData(dao.selectUserByAccount(account));
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按昵称查询用户
	public Result selectUserByUsername(String username){
		Result result=new Result();
		try {
			result.setData(dao.selectUserByUsername(username));
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//修改用户信息
	public Result updateUser(User user){
		Result result=new Result();
		try {
			dao.updateUser(user);
			result.setMessage("修改成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//删除用户
	public Result deleteUser(int userId){
		Result result=new Result();
		try {
			dao.deleteUser(userId);
			dao.deleteBookrackByUser(userId);
			dao.deleteCommentByUser(userId);
			result.setMessage("删除成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//查询全部评论
	public Result selectAllComment(){
		Result result=new Result();
		try {
			result.setData(dao.selectAllComment());
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按账号查询评论
	public Result selectCommentByAccount(String account){
		Result result=new Result();
		try {
			result.setData(dao.selectCommentByAccount(account));
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按内容查询评论
	public Result selectComment(String text){
		Result result=new Result();
		try {
			result.setData(dao.selectComment(text));
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(result.getData()==null) {
				result.setMessage("查找结果为空！！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//修改评论
	public Result updateComment(Comment comment){
		Result result=new Result();
		try {
			dao.updateComment(comment);
			result.setMessage("修改成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//删除评论
	public Result deleteComment(int id){
		Result result=new Result();
		try {
			dao.deleteComment(id);
			result.setMessage("删除成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
}
