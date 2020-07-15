package com.sdut.novel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sdut.novel.bean.BookrackInfo;
import com.sdut.novel.bean.BookrackShow;
import com.sdut.novel.bean.Result;
import com.sdut.novel.dao.BookrackDao;
import com.sdut.novel.dao.UserDao;

@Service
public class BookrackService {

	@Autowired
	BookrackDao dao;
	@Autowired
	UserDao userDao;
	
	//添加书架信息
	public Result addBookrack(BookrackInfo bookrackInfo) {
		Result result=new Result();
		try {
			if(bookrackInfo.getPassword().equals(userDao.login(bookrackInfo.getAccount()))) {
				if(dao.isBookrack(bookrackInfo.getBookId(), bookrackInfo.getUserId())==0) {
					dao.addBookrack(bookrackInfo.getBookId(), bookrackInfo.getUserId(), bookrackInfo.getChapterId(),bookrackInfo.getChapterTable());
					result.setMessage("添加成功！！");
					result.setSuccess(true);
				}else {
					result.setMessage("该书已加入过书架！！");
					result.setSuccess(false);
				}
			}else {
				result.setMessage("账号密码错误！！");
				result.setSuccess(false);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//根据用户id获取书架内容
	public Result selectBookrackByUserId(BookrackInfo bookrackInfo) {
		Result result=new Result();
		try {
			if(bookrackInfo.getPassword().equals(userDao.login(bookrackInfo.getAccount()))) {
				List<BookrackShow> bookrackShow=dao.selectBookrackByUserId(bookrackInfo.getUserId());
				for(BookrackShow b : bookrackShow) {
					b.setChapterName(dao.selectChapterTable(b.getChapterName(), b.getChapterId()));
				}
				result.setData(bookrackShow);
			}
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//更新书架信息
	public Result updateBookrack(BookrackInfo bookrackInfo) {
		Result result=new Result();
		try {
			if(bookrackInfo.getPassword().equals(userDao.login(bookrackInfo.getAccount()))) {
				if(dao.isBookrack(bookrackInfo.getBookId(), bookrackInfo.getUserId())>0) {
					dao.updateBookrack(bookrackInfo.getBookId(), bookrackInfo.getUserId(), bookrackInfo.getChapterId());
					result.setMessage("更新成功！！");
					result.setSuccess(true);
				}else {
					result.setMessage("该书未加入书架！！");
					result.setSuccess(false);
				}
			}else {
				result.setMessage("账号密码错误！！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		
		return result;
	}

	//按删除书架中小说
	public Result deleteBookrack(BookrackInfo bookrackInfo) {
		Result result=new Result();
		try {
			if(bookrackInfo.getPassword().equals(userDao.login(bookrackInfo.getAccount()))) {
				dao.deleteBookrack(bookrackInfo.getBookId(), bookrackInfo.getUserId());
			}
			result.setMessage("删除成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
}
