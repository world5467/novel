package com.sdut.novel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.Result;
import com.sdut.novel.dao.NovelDao;
import com.sdut.novel.getNovel.SelectBook;

@Service
public class NovelService {

	@Autowired
	NovelDao dao;
	@Autowired
	SelectBook selectBook;
	
	//查询图书
	public Result selectBook(String search){
		Result result=new Result();
		try {
			List<Book> b=dao.selectBook(search);
			result.setData(b);
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
			if(b.size()==0) {
				result.setMessage("查找结果为空！！");
				System.out.println("开始查");
				selectBook.selectbook(search);
			}
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//获取小说
	void getNovel() {
		
	}
	
	//增加小说热度
	public Result addHeat(int id,String ip) {
		Result result=new Result();
		try {
			if(dao.selectIP(id, ip)==0) {
				dao.addIP(id, ip);
				dao.addHeat(id);
				result.setMessage("增加热度成功！！");
				result.setSuccess(true);
			}else {
				result.setMessage("已经增加过热度！！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//热度的小说
	public Result selectHeatBook(int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectHeatBook(number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按分类查热度的小说
	public Result selectHeatBookByType(String type,int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectHeatBookByType(type,number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//完结的小说
	public Result selectHeatBookEnd(int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectHeatBookEnd(number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//按分类连载的小说
	public Result selectClassBookSerial(String type,int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectClassBookSerial(type,number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//按分类查热度的小说
	public Result selectClassBookEnd(String type,int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectClassBookEnd(type,number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//最新入库
	public Result selectNewestBook(int number) {
		Result result=new Result();
		try {
			result.setData(dao.selectNewestBook(number));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//小说目录
	public Result selectBookCatalog(String tableName) {
		Result result=new Result();
		try {
			result.setData(dao.selectBookCatalog(tableName));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//小说信息
	public Result selectBookDetails(int id){
		Result result=new Result();
		try {
			result.setData(dao.selectBookDetails(id));
			result.setCode(200);
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
	
	//获取小说内容
	public Result selectChapter(String chapterId,String tableName) {
		Result result=new Result();
		try {
			result.setData(dao.selectChapter(chapterId, tableName));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//按tableName查找小说id
	public Result selectBookByTable(String tableName) {
		Result result=new Result();
		try {
			result.setData(dao.selectBookByTable(tableName));
			result.setCode(200);
			result.setMessage("查询成功！！");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			result.setCode(400);
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	//按条件查找小说
	public Result SelectAllBook(String type,String amount,String time,String state) {
		Result result=new Result();
		try {
			result.setData(dao.SelectAllBook(type,amount,time,state));
			result.setCode(200);
			result.setMessage("查询成功！！");
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
}
