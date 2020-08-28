package com.marvel.tank.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Description ImageUtil
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/8/26 3:45 下午
 **/
public class ImageUtil {

    /**
     * 旋转图片
     * @param bufferedImage 待旋转图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedImage, final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D graphics2d = img.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedImage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
}
