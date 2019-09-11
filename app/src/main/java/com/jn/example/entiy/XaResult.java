package com.jn.example.entiy;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public class XaResult<T> {

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public XaResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public XaResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
