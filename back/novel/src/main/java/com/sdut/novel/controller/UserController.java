package com.sdut.novel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sdut.novel.bean.LoginInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.bean.User;
import com.sdut.novel.bean.UserInfo;
import com.sdut.novel.service.UserService;
@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	UserService service;
	
	//添加账号
	@PostMapping("/addAccount")
	Result addAccount(@RequestBody User user) {
//		System.out.println(user);
		return service.addAccount(user);
	}
	//登录
	@PostMapping("/login")
	Result login(@RequestBody LoginInfo loginInfo) {
//		System.out.println(loginInfo);
		return service.login(loginInfo);
	}
	//修改信息
	@PostMapping("/updateUser")
	Result updateUser(@RequestBody UserInfo userInfo) {
//		System.out.println(user);
		return service.updateUser(userInfo);
	}
	
	//保存文件
	@PostMapping("/head")
    Result uploadFile(@RequestParam("file") MultipartFile file){
        return service.uploadFile(file);
    }
}
