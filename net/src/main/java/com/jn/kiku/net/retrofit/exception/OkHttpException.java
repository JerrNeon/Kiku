package com.jn.kiku.net.retrofit.exception;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：服务器Response返回的错误信息
 */
public class OkHttpException extends Exception {

    private String errorCode;
    private String errorMsg;

    public OkHttpException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public OkHttpException(Throwable cause) {
        super(cause);
    }

    public OkHttpException(Throwable cause, String errorMsg) {
        super(cause);
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
