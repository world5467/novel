package com.sdut.novel.service;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sdut.novel.bean.LoginInfo;
import com.sdut.novel.bean.Result;
import com.sdut.novel.bean.User;
import com.sdut.novel.bean.UserInfo;
import com.sdut.novel.dao.BookrackDao;
import com.sdut.novel.dao.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao dao;
	
	//注册
	public Result addAccount(User user) {
		Result result=new Result();
		try {
			if(dao.selectAccount(user.getAccount())==0) {
				dao.addAccount(user);
				result.setMessage("注册成功！！");
				result.setSuccess(true);
			}else {
				result.setMessage("该账户已存在！！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//登录
	public Result login(LoginInfo loginInfo) {
		Result result=new Result();
		try {
			if(dao.selectAccount(loginInfo.getAccount())>0) {
				
				String password=dao.login(loginInfo.getAccount());
				if(password.equals(loginInfo.getPassword())) {
					result.setMessage("登录成功！！");
					result.setSuccess(true);
					result.setData(dao.selectUser(loginInfo.getAccount()));
				}else {
					result.setMessage("密码错误！！");
					result.setSuccess(false);
				}
			}else { 
				result.setMessage("该账户不存在！！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//修改信息
	public Result updateUser(UserInfo userInfo) {
		Result result=new Result();
		try {
			if(userInfo.getPassword().equals(dao.login(userInfo.getAccount()))) {
				dao.updateUser(userInfo);
				result.setMessage("修改成功！！");
				result.setSuccess(true);
			}else {
				result.setMessage("密码错误！！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage("服务器错误！！");
			result.setSuccess(false);
		}
		return result;
	}
	
	//保存头像
	public Result uploadFile(MultipartFile file) {
		Result result=new Result();
		String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString().substring(0, 6)+new Date().getTime() + suffix;
        String path = "D:\\Project\\WebProject\\book\\head\\";
        File newFile = new File(path + newFileName);
        try {
            file.transferTo(newFile);
            System.out.println("成功");
//            return "img/"+newFileName;
            result.setData(newFileName);
            result.setSuccess(true);
            result.setMessage("保存头像成功!!");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            result.setSuccess(false);
            result.setMessage("保存头像失败！！");
        }
		return result;
	}
}
