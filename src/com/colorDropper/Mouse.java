package com.colorDropper;

import java.awt.AWTException;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Mouse extends JFrame {
    JFrame frame = new JFrame();

    public Mouse() {
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(600,400);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setContentPane(new BgPanel());
        frame.addMouseListener(new ClickRGB());
    }
    public static Path createFileWithDir(String directory, String filename) {
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();
        return Paths.get(directory + File.separatorChar + filename);
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
                String hex = String.format("%02x%02x%02x", r, g, b);
                System.out.println(hex);
                StringSelection stringSelection = new StringSelection(hex);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                setVisible(false);
                frame.dispose();
                try {
                    String format = "bmp";
                    String filePath = "#" + hex + "." + format;
                    Path path = createFileWithDir("bmp",filePath);
                    int width = 200, height = 200;
                    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
                    Graphics2D ig2 = bi.createGraphics();
                    ig2.setPaint(colors);
                    ig2.fillRect(0,0,width,height);
                    ImageIO.write(bi, format, new File(String.valueOf(path)));

                    Desktop desktop = null;
                    if (Desktop.isDesktopSupported()) {
                        desktop = Desktop.getDesktop();
                    }
                        desktop.open(new File("bmp"));
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
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

 