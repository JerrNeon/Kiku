package com.jn.kiku.net.retrofit.body;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：MediaType常量
 */
public interface MediaTypeConstants {

    /**
     * 数据是个普通表单
     */
    String FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * 数据里有文件
     */
    String FORM_DATA = "multipart/form-data";

    /**
     * 数据是个json
     */
    String JSON = "application/json; charset=UTF-8";

    /**
     * 上传的是图片类型
     */
    String IMAGE = "image/*";


}
