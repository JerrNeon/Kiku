package com.jn.kiku.entiy;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (分享实体)
 * @create by: chenwei
 * @date 2017/3/8 11:30
 */
public class ShareVO {

    private int img;
    private String title;

    public ShareVO(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
