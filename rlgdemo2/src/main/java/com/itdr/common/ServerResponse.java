package com.itdr.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private Integer status;
    private T data;
    private String msg;

    //获取成功的对象，包括状态码和数据
    private ServerResponse(T data){
        this.status = 0;
        this.data = data;
    }

    //获取成功的对象，包括状态码和数据
    private ServerResponse(Integer status,T data){
        this.status = status;
        this.data = data;
    }

    //获取成功的对象，包括状态码和数据,和信息
    private ServerResponse(Integer status,T data,String msg){
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    //获取失败的对象，包括状态码和信息
    private ServerResponse(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }

    //获取失败的对象，包括失败信息
    private ServerResponse(String msg){
        this.status = 100;
        this.msg = msg;
    }

    //成功的时候只传入状态码
    public static <T> ServerResponse sucessRS(T data){
        return new ServerResponse(data);
    }

    //成功的时候只传入状态码和数据
    public static <T> ServerResponse sucessRS(Integer status,T data){
        return new ServerResponse(status,data);
    }


    //成功的时候只传入状态码和数据和信息
    public static <T> ServerResponse sucessRS(Integer status,T data,String msg){
        return new ServerResponse(status,data,msg);
    }

    //失败的时候只传入状态码和数据和信息
    public static <T> ServerResponse errorRS(Integer status,String msg){
        return new ServerResponse(status,msg);
    }

    //失败的时候只传入信息
    public static <T> ServerResponse errorRS(String msg){
        return new ServerResponse(msg);
    }


    //判断是否登录成功
    @JsonIgnore
    public boolean isSucess(){

        return this.status == 0;
    }
}
