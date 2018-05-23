package com.jn.kiku.retrofit.body;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (MediaType常量)
 * @create by: chenwei
 * @date 2018/5/18 13:26
 */
public interface MediaTypeConstans {

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
    String JSON = "application/json; charset=utf-8";

    /**
     * 上传的是图片类型
     */
    String IMAGE = "image/*";


}
