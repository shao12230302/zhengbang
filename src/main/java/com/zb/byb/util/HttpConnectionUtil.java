package com.zb.byb.util;

import com.zb.byb.common.WxCache;
import it.sauronsoftware.jave.AudioUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpConnectionUtil {


    public static File downloadWxFile(String mediaId) {
        String accessToken = WxCache.getInstance().getAccessToken().getToken();
        String downloadUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        downloadUrl = downloadUrl.replaceAll("ACCESS_TOKEN", accessToken).replaceAll("MEDIA_ID", mediaId);
        File file = null;
        File file2 = null;
        try {
            // 统一资源
            URL url = new URL(downloadUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            String folder = System.getProperty("java.io.tmpdir");
            String path = folder + File.separatorChar + mediaId + ".amr";
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
            }
            bin.close();
            out.close();

            String path2 = folder + File.separator + mediaId + ".mp3";
            file2 = new File(path2);
            if (!file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            AudioUtils.amrToMp3(file, file2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return file2;
        }

    }

    public static File downloadWxImg(String mediaId) {
        String accessToken = WxCache.getInstance().getAccessToken().getToken();
        String downloadUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        downloadUrl = downloadUrl.replaceAll("ACCESS_TOKEN", accessToken).replaceAll("MEDIA_ID", mediaId);
        File file = null;
        File file2 = null;
        try {
            // 统一资源
            URL url = new URL(downloadUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("GET");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            String folder = System.getProperty("java.io.tmpdir");
            String path = folder + File.separatorChar + mediaId + ".jpg";
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }
    }

//    public static void main(String[] args) {
//        File source = new File("C:\\Users\\pc2\\Desktop\\3.amr");
//        File target = new File("3.mp3");
//        AudioUtils.amrToMp3(source,target);
//    }
}