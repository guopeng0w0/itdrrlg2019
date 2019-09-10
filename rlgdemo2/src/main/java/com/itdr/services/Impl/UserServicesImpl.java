package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.RoleEnum;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.UserMapper;
import com.itdr.pojo.User;
import com.itdr.services.UserServices;
import com.itdr.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserMapper userMapper;

    /*
    * 用户注册
    * */
    @Override
    public ServerResponse<User> register(User user) {
        //1：参数的非空校验
        if (user == null){
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    //参数为空
        }

        //2：校验用户名是否存在
        int result = userMapper.checkUsername(user.getUsername());
        if (result > 0 ){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_YES_MSG);    //1,用户已存在
        }

        //3：判断邮箱是否存在
        int email = userMapper.checkEmail(user.getEmail());

        if (email > 0 ){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_EMAIL_MSG);    //1,邮箱已注册
        }

        //4：MD5密码加密
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置用户角色为普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());

        //5：注册
        int insert = userMapper.insert(user);
        if (insert == 0){
            return ServerResponse.errorRS(Const.USER_PASSWORD_CODE,Const.USER_RESULT_MSG);    //100，注册信息不能为空
        }

        //6：返回结果
        return ServerResponse.errorRS(Const.USER_TRUE_CODE,Const.USER_YESRESULT_MSG);    //0，注册成功

    }

    /*
    * 用户登录
    * */
    @Override
    public ServerResponse<User> login(String username, String password) {

        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAME_CODE,Const.USER_USERNAME_MSG);    // 100,用户名不能为空
        }
        if (password == null || password.equals("")) {
            return ServerResponse.errorRS(Const.USER_PASSWORD_CODE,Const.USER_PASSWORD_MSG);    // 100,密码不能为空
        }

        //2：校验是否有这样一个用户
        int u = userMapper.checkUsername(username);
        if (u <= 0){
            return ServerResponse.errorRS(Const.USER_NO_CODE,Const.USER_NO_MSG);    //  101,用户名不存在
        }

        //3：密码加密
        password = MD5Utils.getMD5Code(password);

        // 4：用户登录
        User user = userMapper.selectUserByUsernameAndPassword(username, password);
        if (user == null){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_PASSWORDERROR_MSG);   //  1,密码错误
        }
        if (user.getRole() == 1){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_NULL_MSG);    //  1，没有权限
        }
        return ServerResponse.sucessRS(user);    //  200，user
        }

    /*
     * 检查用户名是否有效
     * */
    @Override
    public ServerResponse<User> check_valid(String str, String type) {
        //1：参数的非空校验
        if (str == null || str.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAME_CODE,"用户名或邮箱不能为空");
        }
        if (type == null || type.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAME_CODE,"校验的参数不能为空");
        }
        //type:username校验用户名，email：校验邮箱
        //str可以是用户名或邮箱
        if (type.equals("username")){
            int result = userMapper.checkUsername(str);
            if (result > 0){
                return ServerResponse.errorRS(Const.USER_YES_MSG);
            }else {
                return ServerResponse.sucessRS(Const.USER_TRUE_CODE,"校验成功");
            }
        }
        else if (type.equals("email")){
            int result = userMapper.checkEmail(str);
            if (result > 0){
                return ServerResponse.errorRS(Const.USER_EMAIL_MSG);
            }else {
                return ServerResponse.sucessRS(Const.USER_TRUE_CODE,"校验成功");
            }
        }else {
            return ServerResponse.errorRS("参数类型错误");
        }

    }

    /*
     * 根据用户名获取密保问题
     * */
    @Override
    public ServerResponse forget_get_question(String username) {
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAME_CODE,Const.USER_USERNAME_MSG);    // 100,用户名不能为空
        }
        //2：用户名校验
        int u = userMapper.checkUsername(username);
        if (u <= 0){
            return ServerResponse.errorRS(Const.USER_NO_CODE,Const.USER_NO_MSG);    //  101,用户名不存在
        }
        //3：查找密保问题
        String question = userMapper.selectQuestionByUsername(username);
        if (question == null || question.equals("")){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_NULLQUESTION_MSG);    //  1,该用户为设置找回密码问题
        }
        return ServerResponse.sucessRS(Const.USER_TRUE_CODE,Const.USER_QUESTION_MSG);    //  0，这里是问题
    }

    /*
     * 提交问题和答案
     * */
    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAME_CODE,Const.USER_USERNAME_MSG);    // 100,用户名不能为空
        }
        if (question == null || question.equals("")) {
            return ServerResponse.errorRS(Const.USER_PASSWORD_CODE,Const.USER_QUESTIONANDNULL_MSG);    //  100，问题不能为空
        }
        if (answer == null || answer.equals("")) {
            return ServerResponse.errorRS(Const.USER_PASSWORD_CODE,Const.USER_QUESTIONNULL_MSG);    //  100，答案不能为空
        }
        //2：根据用户名密保和答案查询
        int result = userMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (result <= 0){
            return ServerResponse.errorRS(Const.USER_PASSWORDERRORCODE,Const.USER_NOQUESTION_MSG);    //  1，问题答案错误
        }
        //服务端生成一个token保存并返回给客户端
        String forgettoken = UUID.randomUUID().toString();
        return null;
    }

    /*
     * 忘记密码的重设密码
     * */
    @Override
    public ServerResponse forget_reset_password(String username, String passwordnew, String forgettoken) {
        return null;
    }


}





