package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ResponstCode;
import com.itdr.common.RoleEnum;
import com.itdr.mappers.UserMapper;
import com.itdr.pojo.User;
import com.itdr.services.UserServices;
import com.itdr.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.security.interfaces.RSAKey;
import java.util.List;
import java.util.UUID;


@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserMapper userMapper;

    /*
    * 用户注册
    * */
    @Override
    public ResponstCode register(User user) {
        ResponstCode rs = new ResponstCode();
        //1：参数的非空校验
        if (user == null){
            rs.setMsg(Const.USER_NOLL_MSG);
            return rs;
        }

        //2：校验用户名是否存在
        int result = userMapper.checkUsername(user.getUsername());
        if (result > 0 ){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);  //1
            rs.setMsg(Const.USER_YES_MSG);      //用户已存在
            return rs;
        }

        //3：判断邮箱是否存在
        int email = userMapper.checkEmail(user.getEmail());
        if (result > 0 ){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);  //1
            rs.setMsg(Const.USER_EMAIL_MSG);      //邮箱已注册
            return rs;
        }

        //4：MD5密码加密
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));
        //设置用户角色为普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());

        //5：注册
        int insert = userMapper.insert(user);
        if (insert == 0){
            rs.setStatus(Const.USER_PASSWORD_CODE);     //100
            rs.setMsg(Const.USER_RESULT_MSG);        //注册信息不能为空
            return rs;
        }

        //6：返回结果
        rs.setStatus(Const.USER_TRUE_CODE);
        rs.setMsg(Const.USER_YESRESULT_MSG);
        return rs;
    }

    /*
    * 用户登录
    * */
    @Override
    public ResponstCode login(String username, String password) {
        ResponstCode rs = new ResponstCode();

        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            rs.setStatus(Const.USER_USERNAME_CODE);     //100
            rs.setMsg(Const.USER_USERNAME_MSG);         //用户名不能为空
            return rs;
        }
        if (password == null || password.equals("")) {
            rs.setStatus(Const.USER_PASSWORD_CODE);     //100
            rs.setMsg(Const.USER_PASSWORD_MSG);         //密码不能为空
            return rs;
        }

        //2：校验是否有这样一个用户
        int u = userMapper.checkUsername(username);
        if (u <= 0){
            rs.setStatus(Const.USER_NO_CODE);       //101
            rs.setMsg(Const.USER_NO_MSG);            //用户名不存在
            return rs;
        }

        //3：密码加密
        password = MD5Utils.getMD5Code(password);

        // 4：用户登录
        User user = userMapper.selectUserByUsernameAndPassword(username, password);
        if (user == null){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);     //1
            rs.setMsg(Const.USER_PASSWORDERROR_MSG);    //密码错误
            return rs;
        }
        if (user.getRole() == 1){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);     //1
            rs.setMsg(Const.USER_NULL_MSG);    //没有权限
            return rs;
        }
        rs.setStatus(Const.USER_TRUE_CODE);     //0
        rs.setData(user);
        return rs;
    }

    /*
     * 根据用户名获取密保问题
     * */
    @Override
    public ResponstCode forget_get_question(String username) {
        ResponstCode rs = new ResponstCode();
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            rs.setStatus(Const.USER_USERNAME_CODE);     //100
            rs.setMsg(Const.USER_USERNAME_MSG);         //用户名不能为空
            return rs;
        }
        //2：用户名校验
        int u = userMapper.checkUsername(username);
        if (u <= 0){
            rs.setStatus(Const.USER_NO_CODE);       //101
            rs.setMsg(Const.USER_NO_MSG);            //用户名不存在
            return rs;
        }
        //3：查找密保问题
        String question = userMapper.selectQuestionByUsername(username);
        if (question == null || question.equals("")){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);
            rs.setMsg(Const.USER_NULLQUESTION_MSG);
            return rs;
        }
        rs.setStatus(Const.USER_TRUE_CODE);
        rs.setMsg(Const.USER_QUESTION_MSG);
        return rs;
    }

    /*
     * 提交问题和答案
     * */
    @Override
    public ResponstCode forget_check_answer(String username, String question, String answer) {
        ResponstCode rs = new ResponstCode();
        //1：对传进来的参数进行非空校验
        if (username == null || username.equals("")) {
            rs.setStatus(Const.USER_USERNAME_CODE);     //100
            rs.setMsg(Const.USER_USERNAME_MSG);         //用户名不能为空
            return rs;
        }
        if (question == null || question.equals("")) {
            rs.setStatus(Const.USER_PASSWORD_CODE);     //100
            rs.setMsg(Const.USER_QUESTIONANDNULL_MSG);         //问题不能为空
            return rs;
        }
        if (answer == null || answer.equals("")) {
            rs.setStatus(Const.USER_PASSWORD_CODE);     //100
            rs.setMsg(Const.USER_QUESTIONNULL_MSG);         //答案不能为空
            return rs;
        }
        //2：根据用户名密保和答案查询
        int result = userMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (result <= 0){
            rs.setStatus(Const.USER_PASSWORDERRORCODE);     //1
            rs.setMsg(Const.USER_NOQUESTION_MSG);       //问题答案错误
            return rs;
        }
        //服务端生成一个token保存并返回给客户端
        String forgettoken = UUID.randomUUID().toString();
        return null;
    }

    /*
     * 忘记密码的重设密码
     * */
    @Override
    public ResponstCode forget_reset_password(String username, String passwordnew, String forgettoken) {
        return null;
    }

}





