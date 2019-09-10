package com.itdr.services;


import com.itdr.common.ResponstCode;
import com.itdr.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;

public interface UserServices {


     /*
     * 用户注册接口
     * */
     ResponstCode register(User user);

     /*
     * 用户登录接口
     * */
     ResponstCode login(String username, String password);

     /*
      * 根据用户名获取密保问题
      * */
     ResponstCode forget_get_question(String username);

     /*
      * 提交问题和答案
      * */
     ResponstCode forget_check_answer(String username, String question, String answer);

     /*
      * 忘记密码的重设密码
      * */
     ResponstCode forget_reset_password(String username, String passwordnew, String forgettoken);

}




























