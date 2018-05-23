package com.jn.example.entiy;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (CMS)
 * @create by: chenwei
 * @date 2018/05/09 14:20
 */
public class XaResult<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public XaResult<T> setCode(int code) {
        this.code = code;
        return this;
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
