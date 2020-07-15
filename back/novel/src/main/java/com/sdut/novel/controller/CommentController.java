package com.sdut.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdut.novel.bean.CommentInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.service.CommentService;
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

	@Autowired
	CommentService service;
	
	//添加小说评论
	@PostMapping("/addComment")
	Result addComment(@RequestBody CommentInfo commentInfo) {
		return service.addComment(commentInfo);
	}
		
	//按小说ID获取小说评论
	@GetMapping("/selectCommentByBookId/{bookId}")
	Result selectCommentByBookId(@PathVariable int bookId) {
		return service.selectCommentByBookId(bookId);
	}
			
	//按用户ID获取小说评论
	@PostMapping("/selectCommentByUserId")
	Result selectCommentByUserId(@RequestBody CommentInfo commentInfo) {
		return service.selectCommentByUserId(commentInfo);
	}
}
