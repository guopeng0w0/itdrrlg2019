package com.itdr.mappers;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);



    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /*
    * 判断用户名是否存在
    * */
    int checkUsername(String username);

    /*
    * 判断邮箱是否存在
    * */
    int checkEmail(String email);

    /*
     * 用户注册接口
     * */
    int insert(User record);

    /*
     * 用户登录接口
     * */
    User selectUserByUsernameAndPassword( String username,String password);

    /*
     * 检查用户名是否有效
     * */
    ServerResponse<User> check_valid(String str, String type);

    /*
     * 根据用户名获取密保问题
     * */
    String selectQuestionByUsername(String username);

    /*
     * 提交问题和答案
     * */
    int selectByUsernameAndQuestionAndAnswer(String username, String question, String answer);

    /*
     * 忘记密码的重设密码
     * */
    int updatePassword(String username, String passwordnew);

}