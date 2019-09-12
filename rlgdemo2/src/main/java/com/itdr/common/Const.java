package com.itdr.common;

public class Const {

    public static final String USER_ONE = "USER";
    public static final String USER_TOKEN = "TOKEN";

    //用户相关状态相关信息以及状态码
    //1：参数的非空判断
    public static final String USER_NOLL_MSG = "参数为空";
    public static final String USER_NULL_MSG = "没有权限";

    //2：成功统一返回0
    public static final Integer USER_SUCESS_CODE = 0;
    public static final String USER_LOGINTRUE_MSG = "登录成功";
    public static final String USER_REGISTER_MSG = "用户注册成功";
    public static final String USER_JIAOYAN_MSG = "校验成功";
    public static final String USER_QUESTION_MSG = "这里是问题";
    public static final String USER_UPDATEPASSWORD_MSG = "修改密码成功";
    public static final String USER_UPDATEMSG_MSG = "更新个人信息成功";
    public static final String USER_DROPOUT_MSG = "退出成功";

    //3：失败统一返回100
    public static final Integer USER_ERROR_CODE = 100;
    public static final String USER_NULLUSER_MSG = "用户不存在";
    public static final String USER_NOUSERNAME_MSG = "用户名不存在";
    public static final String USER_USERNAMENULL_MSG = "用户名不能为空";
    public static final String USER_PASSWORD_MSG = "密码不能为空";
    public static final String USER_QUESTIONNULL_MSG = "问题不能为空";
    public static final String USER_ANSWERISNULL_MSG = "答案不能为空";
    public static final String USER_MSGISNULL_MSG = "注册信息不能为空";
    public static final String USER_UPDATEMSGERROR_MSG = "更新个人信息失败";
    public static final Integer USER_TOKENISNULL_CODE = 103;
    public static final String USER_TOKENISNULL_MSG = "token已经失效";
    public static final Integer USER_FEIFATOKEN_CODE = 104;
    public static final String USER_FEIFATOKEN_MSG = "输入非法的token";

    //4：失败的错误信息
    public static final Integer USER_ERRORS_CODE = 1;
    public static final String USER_USERNOLOGIN_MSG = "用户未登录";
    public static final String USER_NOLOGIN_MSG = "用户未登录,无法获取当前用户信息";
    public static final String USER_YESUSER_MSG = "用户已存在";
    public static final String USER_YESUSERNAME_MSG = "用户名已存在";
    public static final String USER_PASSWORDERROR_MSG = "密码错误";
    public static final String USER_NOPASSWORDQUESTION_MSG = "该用户未设置找回密码问题";
    public static final String USER_QUESTIONANSWER_MSG = "问题答案错误";
    public static final String USER_UPDATEPASSWORDERROR_MSG = "修改密码失败";
    public static final String USER_OLDPASSWORD_MSG = "旧密码输入错误";
    public static final Integer USER_EMAIL_CODE = 2;
    public static final String USER_EMAIL_MSG = "邮箱已注册";
    public static final Integer USER_USERNOLOGIN_CODE = 3;
    public static final String USER_USERNOLOGINMSG_MSG = "用户未登录，无法获取当前用户信息,status=10强制退出";

}


















