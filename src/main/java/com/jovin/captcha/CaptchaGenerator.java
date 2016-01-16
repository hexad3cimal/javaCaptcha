package com.jovin.captcha;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Created by jovin on 16/1/16.
 */
public class CaptchaGenerator {

    private static final String preText = "data:image/png;base64,";

    public String generateImage(HttpServletRequest request) {

//        Generating a random 5 digit number
        Random random = new Random(System.currentTimeMillis());
        Integer captcha = ((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));

//        Setting the captcha value for comparing
        request.getSession().setAttribute("captchaValue", captcha);

//        creating image
        String captchaString = String.valueOf(captcha);
        JLabel label = new JLabel(captchaString);
        label.setSize(45, 30);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        BufferedImage image = new BufferedImage(
                label.getWidth(), label.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        label.paint(g);
        g.dispose();

//        converting into byte stream
        ByteArrayOutputStream imageByte = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", imageByte);

        } catch (Exception e) {

        }
        byte[] bytes = imageByte.toByteArray();

//        encoding from byte to base64 string
        String base64String = new sun.misc.BASE64Encoder().encode(bytes);

        return preText + base64String;

    }
}
