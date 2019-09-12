package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.RoleEnum;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.UserMapper;
import com.itdr.pojo.User;
import com.itdr.services.UserServices;
import com.itdr.utils.MD5Utils;
import com.itdr.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;


@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserMapper userMapper;

    /*
     * 1：用户注册
     * */
    @Override
    public ServerResponse<User> register(User user) {
        //1：参数的非空校验
        if (user == null) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    //参数为空
        }

        //2：校验用户名是否存在
        int result = userMapper.checkUsername(user.getUsername());
        if (result > 0) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_YESUSER_MSG);    //1,用户已存在
        }

        //3：判断邮箱是否存在
        int email = userMapper.checkEmail(user.getEmail());

        if (email > 0) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_EMAIL_MSG);    //1,邮箱已注册
        }

        //4：MD5密码加密
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置用户角色为普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());

        //5：注册
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return ServerResponse.errorRS(Const.USER_MSGISNULL_MSG);    //100，注册信息不能为空
        }

        //6：返回结果
        return ServerResponse.sucessRS(Const.USER_REGISTER_MSG);    //0，用户注册成功

    }

    /*
     *:2：用户登录
     * */
    @Override
    public ServerResponse<User> login(String username, String password) {

        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAMENULL_MSG);    // 100,用户名不能为空
        }
        if (password == null || password.equals("")) {
            return ServerResponse.errorRS(Const.USER_PASSWORD_MSG);    // 100,密码不能为空
        }

        //2：校验是否有这样一个用户
        int u = userMapper.checkUsername(username);
        if (u <= 0) {
            return ServerResponse.errorRS(Const.USER_NOUSERNAME_MSG);    //  100,用户名不存在
        }

        //3：密码加密
        password = MD5Utils.getMD5Code(password);

        // 4：用户登录
        User user = userMapper.selectUserByUsernameAndPassword(username, password);
        if (user == null) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_PASSWORDERROR_MSG);   //  1,密码错误
        }
        if (user.getRole() == 1) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_NULL_MSG);    //  1，没有权限
        }
        user.setPassword("");
        user.setPhone("");
        user.setAnswer("");
        user.setQuestion("");
        user.setRole(null);
        return ServerResponse.sucessRS(user);    //  100，user
    }

    /*
     *:3：检查用户名是否有效
     * */
    @Override
    public ServerResponse<User> check_valid(String str, String type) {
        //1：参数的非空校验
        if (str == null || str.equals("")) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        if (type == null || type.equals("")) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);
        }
        //type:username校验用户名，email：校验邮箱
        //str可以是用户名或邮箱
        if (type.equals("username")) {
            int result = userMapper.checkUsername(str);
            if (result > 0) {
                return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_YESUSERNAME_MSG);
            } else {
                return ServerResponse.sucessRS(Const.USER_JIAOYAN_MSG);
            }
        } else if (type.equals("email")) {
            int result = userMapper.checkEmail(str);
            if (result > 0) {
                return ServerResponse.errorRS(Const.USER_EMAIL_CODE, Const.USER_EMAIL_MSG);
            } else {
                return ServerResponse.sucessRS(Const.USER_JIAOYAN_MSG);
            }
        } else {
            return ServerResponse.errorRS("参数类型错误");
        }
    }

    /*
     * 4：忘记密码：根据用户名获取密保问题
     * */
    @Override
    public ServerResponse forgetGetQuestion(String username) {
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAMENULL_MSG);    // 100,用户名不能为空
        }
        //2：用户名校验
        int u = userMapper.checkUsername(username);
        if (u <= 0) {
            return ServerResponse.errorRS(Const.USER_NOUSERNAME_MSG);    //  100,用户名不存在
        }
        //3：查找密保问题
        String question = userMapper.selectQuestionByUsername(username);
        if (question == null || question.equals("")) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_NOPASSWORDQUESTION_MSG);    //  1,该用户为设置找回密码问题
        }
        return ServerResponse.sucessRS(Const.USER_QUESTION_MSG);    //  0，这里是问题
    }

    /*
     * 5：交问题和答案
     * */
    @Override
    public ServerResponse forgetCheckAnswer(String username, String question, String answer) {
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAMENULL_MSG);    // 100,用户名不能为空
        }
        if (question == null || question.equals("")) {
            return ServerResponse.errorRS(Const.USER_QUESTIONNULL_MSG);    //  100，问题不能为空
        }
        if (answer == null || answer.equals("")) {
            return ServerResponse.errorRS(Const.USER_ANSWERISNULL_MSG);    //  100，答案不能为空
        }
        //2：根据用户名密保和答案查询
        int result = userMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (result <= 0) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_QUESTIONANSWER_MSG);    //  1，问题答案错误
        }
        //产生随机字符令牌
        String token = UUID.randomUUID().toString();
        //把令牌放到缓存中，使用的是Google的guava缓存，后期会使用redis数据库
        TokenCache.set(Const.USER_TOKEN + username, token);
        //往前端返回随机令牌
        return ServerResponse.sucessRS(token);
    }

    /*
     *6：忘记密码的重设密码
     * */
    @Override
    public ServerResponse forgetResetPassword(String username, String passwordnew, String forgettoken) {
        if (username == null || username.equals("")) {
            return ServerResponse.errorRS(Const.USER_USERNAMENULL_MSG);    // 100,用户名不能为空
        }
        if (passwordnew == null || passwordnew.equals("")) {
            return ServerResponse.errorRS(Const.USER_PASSWORD_MSG);    // 100,密码不能为空
        }
        if (forgettoken == null || forgettoken.equals("")) {
            return ServerResponse.errorRS(Const.USER_TOKENISNULL_CODE, Const.USER_FEIFATOKEN_MSG);
        }
        //判断缓存中的token
        String token = TokenCache.get(Const.USER_TOKEN + username);
        if (token == null) {
            return ServerResponse.errorRS(Const.USER_TOKENISNULL_CODE, Const.USER_TOKENISNULL_MSG);
        }
        //判断值是否相等
        if (!token.equals(forgettoken)) {
            return ServerResponse.errorRS(Const.USER_TOKENISNULL_CODE, Const.USER_TOKENISNULL_MSG);
        }
        passwordnew = MD5Utils.getMD5Code(passwordnew);
        int i = userMapper.forgetResetPassword(username, passwordnew);
        if (i <= 0) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_UPDATEPASSWORDERROR_MSG);
        }
        return ServerResponse.sucessRS(Const.USER_UPDATEPASSWORD_MSG);
    }

    /*
     *7：登录中状态重置密码
     * */
    @Override
    public ServerResponse resetPassword(User user, String passwordOld, String passwordNew) {
        if (passwordOld == null || passwordOld.equals("")) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    // 100,
        }
        if (passwordNew == null || passwordNew.equals("")) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    // 100,
        }
        passwordOld = MD5Utils.getMD5Code(passwordOld);
        int i = userMapper.selectByIdAndPassword(user.getId(), passwordOld);
        if (i <= 0){
            return ServerResponse.errorRS(Const.USER_OLDPASSWORD_MSG);
        }
        passwordNew = MD5Utils.getMD5Code(passwordNew);
        int result = userMapper.updataByUserNameAndPassword(user.getUsername(), passwordNew);
        if (result <= 0){
            return ServerResponse.errorRS(Const.USER_UPDATEPASSWORDERROR_MSG);
        }
        return ServerResponse.sucessRS(Const.USER_UPDATEPASSWORD_MSG);
    }

    /*
     *8：登录状态更新个人信息
     * */
    @Override
    public ServerResponse updateInformationt(User u) {
        //1：参数的非空校验
        if (u == null) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    //参数为空
        }
        //2：更新用户信息
        int i = userMapper.updateByPrimaryKeySelective(u);
        if (i > 0) {
            return ServerResponse.sucessRS(Const.USER_UPDATEMSG_MSG);
        }
        return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_USERNOLOGIN_MSG);

    }

    /*
     *9：获取当前登录用户的详细信息
     * */
    @Override
    public ServerResponse getInforamtion(User user) {
        //1：参数的非空校验
        if (user == null) {
            return ServerResponse.errorRS(Const.USER_NOLL_MSG);    //参数为空
        }
        //2：获取登录用户的信息
        User users = userMapper.selectByPrimaryKey(user.getId());
        if (users == null) {
            return ServerResponse.errorRS(Const.USER_ERRORS_CODE, Const.USER_USERNOLOGINMSG_MSG);
        }
        users.setPassword("");
        return ServerResponse.sucessRS(users);
    }

}





