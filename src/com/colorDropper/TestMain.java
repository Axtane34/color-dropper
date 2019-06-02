package com.colorDropper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        String format = "jpg";
        String fileName = "FullScreenshot." + format;
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
        try {
            ImageIO.write(screenFullImage, format, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mouse mouse = new Mouse();


    }
}
