package com.itdr.services;


import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserServices {

     /*
     * 用户注册接口
     * */
     ServerResponse<User> register(User user);

     /*
     * 用户登录接口
     * */
     ServerResponse<User> login(String username, String password);

     /*
      * 检查用户名是否有效接口
      * */
     ServerResponse<User> check_valid(String str, String type);

     /*
      * 根据用户名获取密保问题接口
      * */
     ServerResponse forget_get_question(String username);

     /*
      * 提交问题和答案接口
      * */
     ServerResponse forget_check_answer(String username, String question, String answer);

     /*
      * 忘记密码的重设密码接口
      * */
     ServerResponse forget_reset_password(String username, String passwordnew, String forgettoken);


}




























