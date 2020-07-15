package com.sdut.novel.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sdut.novel.bean.Comment;
import com.sdut.novel.bean.CommentShow;


@Mapper
public interface CommentDao {

	//添加小说评论
	@Insert("insert into bookcomment value(null,#{userId},#{bookId},#{commentContent},#{score},#{time})")
	void addComment(int userId,int bookId,String commentContent,double score,Date time);
	
	//按小说ID获取小说评论
	@Select("select c.*,u.username,u.headPortrait from bookcomment c,users u where c.userId=u.id and c.bookId=#{bookId}")
	List<CommentShow> selectCommentByBookId(int bookId);
	
	//按用户ID获取小说评论
	@Select("select * from bookcomment where userId=#{userId}")
	List<Comment> selectCommentByUserId(int userId);
	
	//更改小说评分
	@Update("update books set score=(select avg(score) from bookcomment where bookId=#{bookId}) where id=#{bookId}")
	void updateBookScore(int bookId);
}
