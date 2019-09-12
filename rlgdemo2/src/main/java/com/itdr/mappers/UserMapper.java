package com.itdr.mappers;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    /*
     * 获取当前登录用户的详细信息
     * */
    User selectByPrimaryKey(@Param("id")Integer id);

    int updateByPrimaryKey(User record);

    /*
     * 登录状态更新个人信息接口
     * */
    int updateByPrimaryKeySelective(User record);

    /*
    * 判断用户名是否存在
    * */
    int checkUsername(@Param("username")String username);

    /*
    * 判断邮箱是否存在
    * */
    int checkEmail(@Param("email")String email);

    /*
     * 1：用户注册接口
     * */
    int insert(User record);

    /*
     *2：用户登录接口
     * */
    User selectUserByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    /*
     * 忘记密码：根据用户名获取密保问题接口
     * */
    String selectQuestionByUsername(@Param("username")String username);

    /*
     * 提交问题和答案接口
     * */
    int selectByUsernameAndQuestionAndAnswer(@Param("username")String username, @Param("question")String question, @Param("answer")String answer);

    /*
     * 忘记密码的重设密码接口
     * */

    int forgetResetPassword(@Param("username")String username, @Param("passwordnew")String passwordnewn);

    //验证旧密码
    int selectByIdAndPassword(@Param("id")Integer id, @Param("passwordOld")String passwordOld);

    //验证新密码
    int updataByUserNameAndPassword(@Param("username")String username, @Param("passwordNew")String passwordNew);
}