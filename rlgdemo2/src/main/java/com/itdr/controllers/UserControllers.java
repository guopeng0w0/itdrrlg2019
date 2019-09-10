package com.itdr.controllers;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController //@ResponseBody+@Controller
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserServices userServices;

    //用户注册
    @RequestMapping("/register.do")
    public ServerResponse register(User user){
        System.out.println("gp");
        ServerResponse register = userServices.register(user);
        return register;
    }

    //用户登录
    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse<User> login(String username,String password,HttpSession session){
        ServerResponse<User> login = userServices.login(username, password);
        //登录时将用户放到session中
        if (login.isSucess()){
            session.setAttribute("u",login.getData());
        }
        return login;
    }

    //检查用户名是否有效
    @RequestMapping("/check_valid")
    @ResponseBody
    public ServerResponse<User> check_valid(String str,String type){
        ServerResponse<User> check = userServices.check_valid(str, type);
        return check;
    }

    //根据用户名获取问题
    @RequestMapping("/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        return null;
    }

    //提交问题和答案
    @RequestMapping("/forget_check_answer")
    public ServerResponse forget_check_answer(String username, String question, String answer){
        return null;
    }

    //修改密码
    @RequestMapping("/forget_reset_password")
    public ServerResponse forget_reset_password(String username, String passwordnew, String forgettoken){
        return null;
    }

}
















