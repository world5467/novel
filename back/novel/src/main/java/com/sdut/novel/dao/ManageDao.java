package com.sdut.novel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.Comment;
import com.sdut.novel.bean.CommentResult;
import com.sdut.novel.bean.User;

@Mapper
public interface ManageDao {
	
	//查询图书
	@Select("select * from books where bookName like concat('%',concat(#{search},'%')) or writer like concat('%',concat(#{search},'%'))")
	List<Book> selectBook(String search);
	
	//查询全部图书
	@Select("select * from books")
	List<Book> selectAllBook();
	
	//修改图书信息
	@Update("update books set bookName=#{book.bookName},writer=#{book.writer},type=#{book.type},state=#{book.state} where id=#{book.id}")
	void updateBook(@Param("book")Book book);
	
	//删除图书
	@Delete("delete from books where id=#{id}")
	void deleteBook(int id);
	
	//删除章节
	@Delete("drop table ${chapterTable}")
	void deleteChapter(@Param("chapterTable")String chapterTable);
	
	//通过小说删除书架
	@Delete("delete from bookrack where bookId=#{bookId}")
	void deleteBookrackByBook(int bookId);
	
	//通过小说删除评论
	@Delete("delete from bookcomment where bookId=#{bookId}")
	void deleteCommentByBook(int bookId);
	
	//通过账号获取用户信息
	@Select("select * from users where account=#{account}")
	List<User> selectUserByAccount(String account);
	
	//通过昵称获取用户信息
	@Select("select * from users where username like concat('%',concat(#{username},'%'))")
	List<User> selectUserByUsername(String username);
	
	//获取全部用户信息
	@Select("select * from users")
	List<User> selectAllUser();
	
	//修改用户信息
	@Update("update users set username=#{user.username},headPortrait=#{user.headPortrait} where id=#{user.id}")
	void updateUser(@Param("user")User user);
	
	//删除用户
	@Delete("delete from users where id=#{id}")
	void deleteUser(int id);
	
	//通过用户删除书架
	@Delete("delete from bookrack where userId=#{userId}")
	void deleteBookrackByUser(int userId);
	
	//通过用户删除评论
	@Delete("delete from bookcomment where userId=#{userId}")
	void deleteCommentByUser(int userId);
	
	//按内容获取小说评论
	@Select("select b.id,b.userId,b.bookId,b.commentContent,u.account,u.username from bookcomment b,users u where b.userId=u.id and commentContent like concat('%',concat(#{text},'%'))")
	List<CommentResult> selectComment(String text);
	
	//按账号获取小说评论
	@Select("select b.id,b.userId,b.bookId,b.commentContent,u.account,u.username from bookcomment b,users u where b.userId=u.id and u.account=#{account}")
	List<CommentResult> selectCommentByAccount(String account);
	
	//获取全部小说评论
	@Select("select b.id,b.userId,b.bookId,b.commentContent,u.account,u.username from bookcomment b,users u where b.userId=u.id")
	List<CommentResult> selectAllComment();
	
	//修改小说评论
	@Update("update bookcomment set commentContent=#{comment.commentContent} where id=#{comment.id}")
	void updateComment(@Param("comment") Comment comment);
	
	//删除评论
	@Delete("delete from bookcomment where id=#{id}")
	void deleteComment(int id);
}
