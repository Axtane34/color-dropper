package com.colorDropper;

import java.awt.AWTException;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Mouse extends JFrame {
    JFrame frame = new JFrame();

    public Mouse() {

        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(600,400);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setContentPane(new BgPanel());
        frame.addMouseListener(new ClickRGB());



    }

    public class ClickRGB extends MouseAdapter implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent color) {
            try {
                Robot robot = new Robot();
                Color colors = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
                int r = colors.getRed();
                int g = colors.getGreen();
                int b = colors.getBlue();

                String hex = String.format("#%02x%02x%02x", r, g, b);
                System.out.println(hex);
                StringSelection stringSelection = new StringSelection(hex);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                setVisible(false);
                frame.dispose();
            } catch (AWTException e) {
                System.err.println(e.getMessage() + " выбор цвета");
                System.exit(0);
            }
        }
    }

    class BgPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Image im = null;
            try {
                im = ImageIO.read(new File("FullScreenshot.bmp"));
            } catch (IOException e) {
                System.out.println("Файл отсутствует");
            }
            JLabel picLabel = new JLabel(new ImageIcon(im));
            add(picLabel);
            g.drawImage(im,0,0,this);
        }
    }
}

 