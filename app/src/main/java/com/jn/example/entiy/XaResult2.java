package com.jn.example.entiy;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/21 17:57
 */
public class XaResult2<T> {

    private int code;
    private String message;
    private T object;

    public int getCode() {
        return code;
    }

    public XaResult2<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public XaResult2<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getObject() {
        return object;
    }

    public XaResult2<T> setObject(T object) {
        this.object = object;
        return this;
    }
}
