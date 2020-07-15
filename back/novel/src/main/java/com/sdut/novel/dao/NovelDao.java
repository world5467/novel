package com.sdut.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.BookChapter;
import com.sdut.novel.bean.Chapter;


@Mapper
public interface NovelDao {

	//查询图书
	@Select("select * from books where bookName like concat('%',concat(#{search},'%')) or writer like concat('%',concat(#{search},'%'))")
	List<Book> selectBook(String search);
	
	//增加小说热度
	@Update("update books set heat=heat+1 where id=#{id}")
	void addHeat(@Param("id")int id);
	
	//添加IP记录
	@Insert("insert into heat value(null,#{ip},#{bookId})")
	void addIP(@Param("bookId")int bookId,@Param("ip")String ip);
	
	//查询IP记录
	@Select("select count(*) from heat where bookId=#{bookId} and ip=#{ip}")
	int selectIP(@Param("bookId")int bookId,@Param("ip")String ip);
	
	//删除IP记录
	@Delete("delect from heat")
	void deleteIP();
	
	//热度前十的小说
	@Select("select * from books order by heat desc limit #{number}")
	List<Book> selectHeatBook(int number);
	
	//按分类查热度前十的小说
	@Select("select * from books where type=#{type} order by heat desc limit #{number}")
	List<Book> selectHeatBookByType(@Param("type")String type,@Param("number")int number);
	
	//完结的小说
	@Select("select * from books where state='已完本' order by heat desc limit #{number}")
	List<Book> selectHeatBookEnd(@Param("number")int number);
	
	//按分类连载的小说
	@Select("select * from books where type=#{type} and state='连载中' order by heat desc limit #{number}")
	List<Book> selectClassBookSerial(@Param("type")String type,@Param("number")int number);
	
	//按分类完结的小说
	@Select("select * from books where type=#{type} and state='已完本' order by heat desc limit #{number}")
	List<Book> selectClassBookEnd(@Param("type")String type,@Param("number")int number);
	
	//最新入库
	@Select("select * from books order by id desc limit #{number}")
	List<Book> selectNewestBook(@Param("number")int number);
	
	//查询图书
	@Select("select * from books where id=#{id}")
	Book selectBookDetails(int id);
	
	//小说目录
	@Select("select id,chapterName from ${tableName}")
	List<BookChapter> selectBookCatalog(@Param("tableName")String tableName);
	
	//获取小说内容
	@Select("select * from ${tableName} where id=#{chapterId}")
	Chapter selectChapter(@Param("chapterId")String chapterId,@Param("tableName")String tableName);
	
	//按tableName查找小说id
	@Select("select * from books where chapterTable=#{tableName}")
	int selectBookByTable(@Param("tableName")String tableName);

	//查询所有图书
	@SelectProvider(type=SelectAllBook.class, method = "selectAllBook")
	List<Book> SelectAllBook(@Param("type")String type,@Param("amount")String amount,@Param("time")String time,@Param("state")String state);
	
}