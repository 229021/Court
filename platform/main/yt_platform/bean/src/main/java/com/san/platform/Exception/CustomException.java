package com.san.platform.Exception;

import java.util.HashMap;

public class CustomException extends  Exception{
    private static final long serialVersionUID = 1L;

    private int code;

    // 提供无参数的构造方法
    public CustomException() {
    }

    // 提供一个有参数的构造方法，可自动生成
    public CustomException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
    }
    public CustomException(Integer code) {
        this.code = code;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
