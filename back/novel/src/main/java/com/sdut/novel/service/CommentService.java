package com.sdut.novel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.novel.bean.CommentInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.dao.CommentDao;
import com.sdut.novel.dao.UserDao;

@Service
public class CommentService {

	@Autowired
	CommentDao dao;
	@Autowired
	UserDao userDao;
	
	//添加小说评论
	public Result addComment(CommentInfo commentInfo) {
		Result result=new Result();
		try {
			if(commentInfo.getPassword().equals(userDao.login(commentInfo.getAccount()))) {
				Date time=new Date();
				System.out.println(time);
				dao.addComment(commentInfo.getUserId(), commentInfo.getBookId(), commentInfo.getCommentContent(),commentInfo.getScore(),time);
				dao.updateBookScore(commentInfo.getBookId());
			}
			result.setMessage("添加成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按小说ID获取小说评论
	public Result selectCommentByBookId(int bookId) {
		Result result=new Result();
		try {
			result.setData(dao.selectCommentByBookId(bookId));
			result.setMessage("查找成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		
		return result;
	}
		
	//按用户ID获取小说评论
	public Result selectCommentByUserId(CommentInfo commentInfo) {
		Result result=new Result();
		try {
			if(commentInfo.getPassword().equals(userDao.login(commentInfo.getAccount()))) {
				result.setData(dao.selectCommentByUserId(commentInfo.getUserId()));
			}
			result.setMessage("查找成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
}
