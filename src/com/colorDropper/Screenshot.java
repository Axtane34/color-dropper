package com.colorDropper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Screenshot {
    Screenshot(){
        Robot robot = null;
        try {
            robot = new Robot();
            String format = "bmp";
            String fileName = "FullScreenshot." + format;
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }


    }
}
