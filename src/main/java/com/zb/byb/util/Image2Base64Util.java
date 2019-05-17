package com.zb.byb.util;


import com.zb.byb.entity.FileEntry;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
* @Function:  File互转Base64
* @Author: shaoys
* @Date: Created in 10:41 2019/5/14
**/
public class Image2Base64Util {

//    public static void main(String[] args) throws Exception {
//        File file = new File("C:\\Users\\pc2\\Desktop\\3.jpg");//待处理的图片
//        String imgbese = fileToBase64(file);
//        System.out.println(imgbese);
//////        System.out.println(imgbese.length());
////        System.out.println(imgbese);
////    decoderBase64File(imgbese,"C:\\Users\\pc2\\Desktop\\4.jpg","C:\\Users\\pc2\\Desktop");
//    }

    /**
     * 将图片转换成Base64编码
     *
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgStr(File imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr      图片数据
     * @param imgFilePath 保存图片全路径地址
     * @return
     */
    public static boolean generateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) //图像数据为空
            return false;

        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static FileEntry subBase64(FileEntry fileEntry){
        String base64Str=fileEntry.getImgContent().split(",")[1];
         fileEntry.setImgContent(base64Str);
         return fileEntry;
    }

    /**
    * @Function: 将音频流转换为base64字符串
    * @Author: shaoys
    * @Date: Created in 14:41 2019/5/7
    **/
    public static String getBase64FromInputStream(InputStream in) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.encodeBase64(data));
    }


    /**
     * 字符串加密
     */

    public static String getBase64Encoder(String str) throws IOException {
        String encode = new BASE64Encoder().encode(str.getBytes());
        return encode;
    }

    /**
     * 字符串解密
     */

    public static String getBase64Decoder(String str) throws IOException {
        if(str==null ){
            return null;
        };
        String s = new String(new BASE64Decoder().decodeBuffer(str));
        return s;
    }
    /**
    * @Function: base64解码为文件
    * @Author: shaoys
    * @Date: Created in 16:24 2019/5/10
    **/
    public static void base64ToFile(String base64Code, String targetPath,String catalogue)
            throws Exception {
        File file = new File(catalogue);
        if(file.exists()==false){
            file.mkdirs();
        }
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * @Function: 文件转base64
     * @Author: shaoys
     * @Date: Created in 11:30 2019/5/10
     **/
    public static String fileToBase64(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        return new BASE64Encoder().encode(data);
    }
    /**
    * @Function: 图片
    * @Author: shaoys
    * @Date: Created in 19:15 2019/5/10
    **/
    public static String imgToBase64(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1){
            bos.write(b,0,n);
        }
        fis.close();
        return new BASE64Encoder().encode(bos.toByteArray());
    }

    /**
     * @Description： 图片转化成base64字符串
     * @param:    path
     * @Return:
     */
    public static String GetImageStr(String path)
    {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //待处理的图片
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

//    public static void main(String[] args) {
//        String abc=GetImageStr("C:\\fakepath\\企业微信截图_1557473165743.png");
//        System.out.println(abc);
//    }
}