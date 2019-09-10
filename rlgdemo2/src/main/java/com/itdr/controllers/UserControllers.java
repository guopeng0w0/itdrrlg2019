package com.itdr.controllers;

import com.itdr.common.Const;
import com.itdr.common.ResponstCode;
import com.itdr.pojo.User;
import com.itdr.services.UserServices;
import com.sun.org.apache.regexp.internal.RE;
import org.omg.CORBA.FREE_MEM;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController //@ResponseBody+@Controller
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private UserServices userServices;

//    用户注册
    @RequestMapping("/register.do")
    public ResponstCode register( User user){
        ResponstCode register = userServices.register(user);
        return register;
    }

//    用户登录
    @RequestMapping("/login.do")
    @ResponseBody
    public ResponstCode login(String username,String password,HttpSession session){
        ResponstCode login = userServices.login(username, password);

        //登录时将用户放到session中
        session.setAttribute(Const.USER_TRUE_MSG,login.getData());
        return login;
    }

//    根据用户名获取问题
    @RequestMapping("//forget_get_question.do")
    public ResponstCode forget_get_question(String username){
        return null;
    }

//    提交问题和答案
    @RequestMapping("/forget_check_answer")
    public ResponstCode forget_check_answer(String username,
                                            String question,
                                            String answer){
        return null;
    }

//    修改密码
    @RequestMapping("/forget_reset_password")
    public ResponstCode forget_reset_password(String username,
                                              String passwordnew,
                                              String forgettoken){
        return null;
    }
}
















