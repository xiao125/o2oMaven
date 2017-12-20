package com.imooc.o2o.util;

/**
 *
 */
public class PathUtil {

    private static String seperator = System.getProperty("file.separator"); //获取系统文件属性

    public static String getImgBasePath(){

        String os = System.getProperty("os.name");
        String basePath="";
        if (os.toLowerCase().startsWith("win")){ //如果是windows,保存在一个目录下

            basePath = "F:/IdeaProjects/img";


        }else {
            basePath = "/Users/baidu/work/image";
        }
        basePath =basePath.replace("/",seperator);//替换“/”分隔符
        return basePath;
    }

    //项目图片的子路径
    public static String getShopImagePath(long shopId){
        // 获取shop图片目录的相对值路径
        String imagePath = "/upload/images/item/shop/"+shopId+"/";
        return imagePath.replace("/",seperator);


    }



}
