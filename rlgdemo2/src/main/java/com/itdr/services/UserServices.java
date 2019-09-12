package com.itdr.services;


import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;

public interface UserServices {

     //1：用户注册接口
     ServerResponse<User> register(User user);


     //2：用户登录接口
     ServerResponse<User> login(String username, String password);


     //3:检查用户名是否有效接口
     ServerResponse<User> check_valid(String str, String type);


     //4：记密码 ：根据用户名获取问题接口
     ServerResponse forgetGetQuestion(String username);


     //5：提交问题和答案接口
     ServerResponse forgetCheckAnswer(String username, String question, String answer);


     //6：忘记密码的重设密码接口
     ServerResponse forgetResetPassword(String username, String passwordnew, String forgettoken);


     //7：登录中状态重置密码
     ServerResponse resetPassword(User user, String passwordOld, String passwordNew);


     //8：登录状态更新个人信息
     ServerResponse updateInformationt(User u);


     //9：获取当前登录用户的详细信息
     ServerResponse getInforamtion(User user);


}




























