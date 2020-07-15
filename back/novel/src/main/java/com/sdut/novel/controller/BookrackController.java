package com.sdut.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdut.novel.bean.BookrackInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.service.BookrackService;
@RestController
@RequestMapping("/bookrack")
@CrossOrigin
public class BookrackController {

	@Autowired
	BookrackService service;
	
	//添加书架信息
	@PostMapping("/addBookrack")
	Result addBookrack(@RequestBody BookrackInfo bookrackInfo) {
		return service.addBookrack(bookrackInfo);
	}
	
	//根据用户id获取书架内容
	@PostMapping("/selectBookrackByUserId")
	Result selectBookrackByUserId(@RequestBody BookrackInfo bookrackInfo) {
		return service.selectBookrackByUserId(bookrackInfo);
	}
	
	//更新书架信息
	@PostMapping("/updateBookrack")
	Result updateBookrack(@RequestBody BookrackInfo bookrackInfo) {
		return service.updateBookrack(bookrackInfo);
	}
		
	//删除书架中小说
	@PostMapping("/deleteBookrack")
	Result deleteBookrack(@RequestBody BookrackInfo bookrackInfo) {
		return service.deleteBookrack(bookrackInfo);
	}
}
