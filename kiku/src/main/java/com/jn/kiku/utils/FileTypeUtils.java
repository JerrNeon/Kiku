package com.jn.kiku.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (文件类型)
 * @create by: chenwei
 * @date 2018/5/18 15:29
 */
public class FileTypeUtils {

    //MIME (Multipurpose Internet Mail Extensions) 是描述消息内容类型的因特网标准。
    private static Map<String, String> MIME_TYPE_MAP = new HashMap<>();
    private static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    static {
        MIME_TYPE_MAP.put("application/vnd.android.package-archive", "apk");
        MIME_TYPE_MAP.put("application/pdf", "pdf");
        MIME_TYPE_MAP.put("application/vnd.ms-excel", "xls");
        MIME_TYPE_MAP.put("application/vnd.ms-powerpoint", "ppt");
        MIME_TYPE_MAP.put("application/vnd.ms-works", "wps");
        MIME_TYPE_MAP.put("application/zip", "zip");
        MIME_TYPE_MAP.put("audio/mpeg", "mp3");
        MIME_TYPE_MAP.put("audio/x-wav", "wav");
        MIME_TYPE_MAP.put("image/bmp", "bmp");
        MIME_TYPE_MAP.put("image/gif", "gif");
        MIME_TYPE_MAP.put("image/jpeg", "jpg");
        MIME_TYPE_MAP.put("image/png", "png");
        MIME_TYPE_MAP.put("text/css", "css");
        MIME_TYPE_MAP.put("text/html", "html");
        MIME_TYPE_MAP.put("video/x-msvideo", "avi");

        FILE_TYPE_MAP.put("apk", "application/vnd.android.package-archive");
        FILE_TYPE_MAP.put("pdf", "application/pdf");
        FILE_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        FILE_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        FILE_TYPE_MAP.put("wps", "application/vnd.ms-works");
        FILE_TYPE_MAP.put("zip", "application/zip");
        FILE_TYPE_MAP.put("mp3", "audio/mpeg");
        FILE_TYPE_MAP.put("wav", "audio/x-wav");
        FILE_TYPE_MAP.put("bmp", "image/bmp");
        FILE_TYPE_MAP.put("gif", "image/gif");
        FILE_TYPE_MAP.put("jpg", "image/jpeg");
        FILE_TYPE_MAP.put("png", "image/png");
        FILE_TYPE_MAP.put("css", "text/css");
        FILE_TYPE_MAP.put("html", "text/html");
        FILE_TYPE_MAP.put("avi", "video/x-msvideo");
    }

    /**
     * 获取文件后缀名
     *
     * @param mimeType 描述消息内容类型的因特网标准。
     * @return
     */
    public static String getFileSuffix(String mimeType) {
        return MIME_TYPE_MAP.get(mimeType);
    }

    /**
     * 获取MimeType
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileMimeType(String filePath) {
        return MIME_TYPE_MAP.get(FileUtils.getFileSuffix(filePath).replace(".", ""));
    }
}
