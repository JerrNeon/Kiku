package com.jn.kiku.retrofit.exception;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (服务器Response返回的错误信息)
 * @create by: chenwei
 * @date 2018/5/9 15:42
 */
public class OkHttpException extends Exception {

    private String errorMsg;

    public OkHttpException() {
    }

    public OkHttpException(String errorMsg) {
        super(errorMsg);
    }

    public OkHttpException(Throwable cause) {
        super(cause);
    }

    public OkHttpException(Throwable cause, String errorMsg) {
        super(cause);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
