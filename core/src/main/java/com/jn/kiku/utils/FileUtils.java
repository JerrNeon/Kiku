package com.jn.kiku.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (文件操作类)
 * @create by: chenwei
 * @date 2016/10/10 18:29
 */
public class FileUtils {

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }


    /**
     * 方法描述：将系统限定的路径转换为绝对正确的路径
     *
     * @param originalPath
     * @return
     */
    public static String getGeneralFilePath(String originalPath) {
        if ((null != originalPath) && !("".equals(originalPath))) {
            String strPath[] = originalPath.split("\\\\|/");
            originalPath = "";
            // 拼接jar路径
            for (int i = 0; i < strPath.length; i++) {
                if (!("".equals(strPath[i])) && !("".equals(strPath[i].trim()))) {
                    originalPath = originalPath + strPath[i].trim();
                    if (i < strPath.length - 1) {
                        originalPath = originalPath + File.separator;
                    }
                }
            }
        }
        return originalPath;
    }

    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFile  源文件File
     * @param destFile 目标目录File
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    public static long copyFile(File srcFile, File destFile) throws Exception {
        long copySizes = 0;
        FileChannel fcin = new FileInputStream(srcFile).getChannel();
        FileChannel fcout = new FileOutputStream(destFile).getChannel();
        long size = fcin.size();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        copySizes = size;
        return copySizes;
    }

    /**
     * 获取当前时间当作文件名称
     *
     * @return
     */
    public static String getToDayStrAsFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * 删除指定目录下的所有文件
     *
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 获取文件后缀
     *
     * @param filePath
     * @return
     */
    public static String getFileSuffix(String filePath) {
        try {
            File file = new File(filePath);
            String fileName = file.getName();
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            return prefix;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
