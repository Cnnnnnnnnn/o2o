package com.ning.o2o.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil {
    //获得当前系统的文件路径分割符
    private static String seperator = System.getProperty("file.separator");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static Random r = new Random();
    //获取图片的基本路径
    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        //将文字全部转换为小写，然后检查前缀
        if(os.toLowerCase().startsWith("win")){
            basePath = "E:/o2o/image/";
        }else {
            basePath = "home/o2o/image/";
        }
        //将“/”替换为当前系统的分割符
        basePath = basePath.replace("/",seperator);
        return basePath;
    }
    //获取店铺图片的路径
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/"+shopId+"/";
        return imagePath.replace("/",seperator);
    }

    public static String getRandomFileName() {
        int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
        String nowTimeStr = sdf.format(new Date()); // 当前时间
        return nowTimeStr + rannum;
    }
}
