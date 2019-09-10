package com.itdr.common;

public class ResponstCode<T> {
    private Integer status;     //状态码
    private T data;     //任何数据类型类型d的值
    private String msg;     //错误信息

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":" + status +
                ", \"data\":" + data +
                ", \"msg\":" + msg  +
                "}";
    }
}