package com.zb.byb.test;

import com.zb.byb.controller.TouMiaoController;
import com.zb.byb.entity.TouMiao;
import com.zb.byb.service.TouMiaoService;
import com.zb.byb.service.impl.TouMiaoServiceImpl;
import com.zb.byb.util.BeanLocator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 作者：谢李
 */
public class TestData {


    public static void main(String[] args) throws Exception {
        String proFilePath = System.getProperty("user.dir");
        String newFilePath = proFilePath + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "static"+ File.separator + "static" +
                "img" + File.separator + "banner.6a3a9e5.jpg";
        System.out.println(newFilePath);

//        createImage("请在这里输入文字", new Font("微软雅黑", Font.PLAIN, 32), new File("d:/a.png"), 500, 64);
    }

    // 根据str,font的样式以及输出文件目录
    public static void createImage(String str, Font font, File outFile,
                                   Integer width, Integer height) throws Exception {
        // 创建图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.setClip(0, 0, width, height);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
        g.setColor(Color.black);// 在换成黑色
        g.setFont(font);// 设置画笔字体
        /** 用于获得垂直居中y */
        Rectangle clip = g.getClipBounds();
        FontMetrics fm = g.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (clip.height - (ascent + descent)) / 2 + ascent;
        for (int i = 0; i < 6; i++) {// 256 340 0 680
            g.drawString(str, i * 680, y);// 画出字符串
        }
        g.dispose();
        ImageIO.write(image, "png", outFile);// 输出png图片
    }


//ImageProducerUtil
//        public static void main(String[] args) throws Exception {
//            String rootPath = "/Users/xxx/image_test";
//            createImage("ssss中华人民共和国", new Font("宋体", Font.PLAIN, 100), Paths.get(rootPath, "b" + 4000 + ".png").toFile());
//        }
//
//        private static int[] getWidthAndHeight(String text, Font font) {
//            Rectangle2D r = font.getStringBounds(text, new FontRenderContext(
//                    AffineTransform.getScaleInstance(1, 1), false, false));
//            int unitHeight = (int) Math.floor(r.getHeight());//
//            // 获取整个str用了font样式的宽度这里用四舍五入后+1保证宽度绝对能容纳这个字符串作为图片的宽度
//            int width = (int) Math.round(r.getWidth()) + 1;
//            // 把单个字符的高度+3保证高度绝对能容纳字符串作为图片的高度
//            int height = unitHeight + 3;
//            System.out.println("width:" + width + ", height:" + height);
//            return new int[]{width, height};
//        }
//
//        // 根据str,font的样式以及输出文件目录
//        public static void createImage(String text, Font font, File outFile)
//                throws Exception {
//            // 获取font的样式应用在str上的整个矩形
//            int[] arr = getWidthAndHeight(text, font);
//            int width = arr[0];
//            int height = arr[1];
//            // 创建图片
//            BufferedImage image = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_BGR);//创建图片画布
//            Graphics g = image.getGraphics();
//            g.setColor(Color.WHITE); // 先用白色填充整张图片,也就是背景
//            g.fillRect(0, 0, width, height);//画出矩形区域，以便于在矩形区域内写入文字
//            g.setColor(Color.black);// 再换成黑色，以便于写入文字
//            g.setFont(font);// 设置画笔字体
//            g.drawString(text, 0, font.getSize());// 画出一行字符串
//            g.drawString(text, 0, 2 * font.getSize());// 画出第二行字符串，注意y轴坐标需要变动
//            g.dispose();
//            ImageIO.write(image, "png", outFile);// 输出png图片
//        }


}
