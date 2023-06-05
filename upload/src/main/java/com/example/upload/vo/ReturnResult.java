package com.example.upload.vo;

import java.io.Serializable;

public class ReturnResult<T> implements Serializable {

    private static final long serialVersionUID = -1959544190118740608L;

    private String code;
    private String msg;
    private  T DATA ;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getDATA() {
        return DATA;
    }

    public void setDATA(T DATA) {
        this.DATA = DATA;
    }

    public ReturnResult(String code, String msg, T DATA) {
        this.code = code;
        this.msg = msg;
        this.DATA = DATA;
    }

    public ReturnResult() {
    }

    public ReturnResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
