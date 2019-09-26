package com.ning.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static Random r = new Random();

    /**
     * 用户上传图片处理
     * @param thumbnailInputStream spring自带的文件流
     * @param targetAddr 文件保存的位置
     * @return
     */
    public static String generateThumbnails(InputStream thumbnailInputStream, String targetAddr,String fileName){
        String realFileName = FileUtil.getRandomFileName();//获取上传文件的名
        String extension = getFileExtension(fileName);//获取上传文件的后缀
        makeDirPath(targetAddr);//如果文件路径不存在就创建路径
        String relativeAddr = targetAddr+realFileName+extension;//文件的路径加名字
        File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
        try{
            Thumbnails.of(new File(basePath+ "/timg.jpg")).size(40,40).toFile(basePath+"/timg2.jpg");
            Thumbnails.of(thumbnailInputStream).size(200,200).
                    watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/timg2.jpg")),0.5f).
                    outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            throw new RuntimeException("创建缩略图失败"+e.toString());
        }
        return relativeAddr;
    }
    /*
    创建保存图片的文件夹
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }
    /*
    获取文件名，截取文件名的后缀
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));//获取文件的后缀名
    }


//    public static void main(String[] args) throws IOException {
//        /*
//        读取resource文件夹中文件路径的几种方式
//         */
//        /*
//        watermark：为图片生成水印的方法
//        参数1：让水印图片位于主图片的右下方
//        参数2：水印图片的读入路径
//        参数3：水印图片的透明度
//         */
//        Thumbnails.of(new File(basePath+"/timg.jpg")).size(40, 40)
//                .toFile(basePath+"/timg2.jpg");
//        Thumbnails.of(new File("E:/logs/o2o/tu/test.jpg")).size(200, 200)
//                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/timg2.jpg")), 0.5f).
//                outputQuality(0.8f).toFile("E:/logs/o2o/tu/test2.jpg");
//    }
}
