package com.sdut.novel.getNovel;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sdut.novel.bean.*;
@Mapper
public interface BookDao {
	
	//查找全部小说
	@Select("select bookName from books")
	List<String> selectAllBook();
	
	//查找全部未分类小说
	@Select("select * from books where type is null")
	List<Book> selectNoClassBook();
	
	//按书名查找图书是否存在
	@Select("select count(bookName) from books where bookName=#{bookName} and writer=#{writer}")
	int selectBook(@Param("bookName")String bookName,@Param("writer")String writer);
	
	//按书名查找图书
	@Select("select * from books where bookName=#{bookName} and writer=#{writer}")
	Book selectBookName(@Param("bookName")String bookName,@Param("writer")String writer);
	
	//小说目录
	@Select("select chapterName from ${tableName}")
	List<String> selectBookCatalog(@Param("tableName")String tableName);
	
	//插入图书信息
	@Insert("insert into books value(null,#{book.bookName},#{book.writer},#{book.bookDescription},#{book.state},#{book.cover},#{book.type},#{book.renewalTime},0,0,0,#{book.chapterTable})")
	void addBook(@Param("book")Book book);
	
	//修改小说字数
	@Update("update books set amount=amount+#{amount} where bookName=#{bookAmount.bookName} and writer=#{bookAmount.writer}")
	void updateAmount(@Param("bookAmount")Book bookAmount,@Param("amount")int amount);
	
	//创建小说章节表
	@Update("create table ${tableName} ( id int PRIMARY key AUTO_INCREMENT, chapterName varchar(50),chapterContent text )")
	void addChapterTable(@Param("tableName")String tableName);
	
	//插入章节信息
	@Insert("insert into ${tableName} value(#{chapter.id},#{chapter.chapterName},#{chapter.chapterContent})")
	void addChapter(@Param("tableName")String tableName,@Param("chapter")Chapter chapter);
	
	//获取前三十章小说章节
	@Select("select chapterContent from ${chapterTable} limit 30")
	List<String> getChapter(@Param("chapterTable")String chapterTable);
	
	//添加小说类别
	@Update("update books set type=#{book.type} where id=#{book.id}")
	void updateType(@Param("book")Book book);
}
