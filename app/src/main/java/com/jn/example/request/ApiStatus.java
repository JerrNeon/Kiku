package com.jn.example.request;

/**
 * Author：Stevie.Chen Time：2019/7/9
 * Class Comment：Api Response Code
 */
public interface ApiStatus {

    /**
     * Success
     */
    String CODE_200 = "200";

    /**
     * Created
     */
    String CODE_201 = "201";

    /**
     * Failure
     */
    String CODE_400 = "400";

    /**
     * Unauthorized
     */
    String CODE_401 = "401";

    /**
     * Forbidden
     */
    String CODE_403 = "403";

    /**
     * Not Found
     */
    String CODE_404 = "404";

    /**
     * Token Invalid
     */
    String CODE_10002 = "10002";

    /**
     * Need Purchase Vip
     */
    String CODE_10013 = "10013";


}
