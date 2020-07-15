package com.sdut.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdut.novel.bean.Book;
import com.sdut.novel.bean.Result;
import com.sdut.novel.service.NovelService;
@RestController
@RequestMapping("/novel")
@CrossOrigin
public class NovelController {

	@Autowired
	NovelService service;
	
	//查询图书
	@GetMapping("/selectBook/{search}")
	Result selectBook(@PathVariable String search){
		return service.selectBook(search);
	}
	
	//增加小说热度
	@GetMapping("/addHeat/{bookId}/{ip}")
	Result addHeat(@PathVariable int bookId,@PathVariable String ip) {
		return service.addHeat(bookId,ip);
	}
	
	//热度的小说
	@GetMapping("/selectHeatBook/{number}")
	Result selectHeatBook(@PathVariable int number) {
		return service.selectHeatBook(number);
	}
	
	//按分类查热度的小说
	@GetMapping("/selectHeatBook/{number}/{type}")
	Result selectHeatBookByType(@PathVariable String type,@PathVariable int number) {
		return service.selectHeatBookByType(type,number);
	}
	
	//完结的小说
	@GetMapping("/selectHeatBookEnd/{number}")
	Result selectHeatBookEnd(@PathVariable int number) {
		return service.selectHeatBookEnd(number);
	}
	
	//分类连载的小说
	@GetMapping("/selectClassBookSerial/{number}/{type}")
	Result selectClassBookSerial(@PathVariable String type,@PathVariable int number) {
		return service.selectClassBookSerial(type,number);
	}
	
	//分类完结的小说
	@GetMapping("/selectClassBookEnd/{number}/{type}")
	Result selectClassBookEnd(@PathVariable String type,@PathVariable int number) {
		return service.selectClassBookEnd(type,number);
	}
	
	//最新入库
	@GetMapping("/selectNewestBook/{number}")
	Result selectNewestBook(@PathVariable int number) {
		return service.selectNewestBook(number);
	}
	
	//小说信息
	@GetMapping("/selectBookDetails/{id}")
	Result selectBookDetails(@PathVariable int id){
		return service.selectBookDetails(id);
	}
	
	//小说目录
	@GetMapping("/selectBookCatalog/{tableName}")
	Result selectBookCatalog(@PathVariable String tableName) {
		return service.selectBookCatalog(tableName);
	}
	
	//获取小说内容
	@GetMapping("/selectChapter/{tableName}/{chapterId}")
	Result selectChapter(@PathVariable String chapterId,@PathVariable String tableName) {
		return service.selectChapter(chapterId, tableName);
	}
	
	//按tableName查找小说id
	@GetMapping("/selectBookByTable/{tableName}")
	Result selectBookByTable(@PathVariable String tableName) {
		return service.selectBookByTable(tableName);
	}
	
	//按条件查找小说
	@GetMapping("/SelectAllBook/{type}/{amount}/{time}/{state}")
	Result SelectAllBook(@PathVariable String type,@PathVariable String amount,@PathVariable String time,@PathVariable String state) {
		return service.SelectAllBook(type,amount,time,state);
	}
}
