package com.jn.kiku.mvp;


import com.jn.kiku.common.api.IUtilsView;
import com.jn.kiku.utils.QMUtil;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2017/11/17 15:33
 */
public class BaseModel implements IUtilsView {

    @Override
    public boolean isEmpty(Object obj) {
        return QMUtil.isEmpty(obj);
    }

    @Override
    public String checkStr(String str) {
        return QMUtil.checkStr(str);
    }

    @Override
    public <T extends Number> String objToStr(T object) {
        return String.valueOf(object);
    }

    @Override
    public <T extends Number> T strToObject(String str, Number defaultNumber) {
        return QMUtil.strToObject(str, defaultNumber);
    }
}
