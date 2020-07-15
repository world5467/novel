package com.sdut.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.sdut.novel.bean.BookrackShow;


@Mapper
public interface BookrackDao {
	
	
	//添加书架信息
	@Insert("insert into bookrack value(null,#{bookId},#{userId},#{chapterId},#{chapterTable})")
	void addBookrack(int bookId,int userId,int chapterId,String chapterTable);
	
	//查找书架是否存在该书
	@Select("select count(*) from bookrack where bookId=#{bookId} and userId=#{userId}")
	int isBookrack(int bookId,int userId);
	
	//获取书架章节名
	@Select("select chapterName from ${chapterTable} where id=#{chapterId}")
	String selectChapterTable(String chapterTable,int chapterId);
	
	//根据用户id获取书架内容
	@Select("select b.id id,b.bookName bookName,b.writer writer,b.type type,r.chapterId chapterId,b.chapterTable chapterName from books b,bookrack r where b.id=r.bookId and r.userId=#{userId}")
	List<BookrackShow> selectBookrackByUserId(int userId);
	
	//更新书架信息
	@Update("update bookrack set chapterId=#{chapterId} where bookId=#{bookId} and userId=#{userId}")
	void updateBookrack(int bookId,int userId,int chapterId);

	//按删除书架中小说
	@Delete("delete from bookrack where bookId=#{bookId} and userId=#{userId}")
	void deleteBookrack(int bookId,int userId);
	
}
