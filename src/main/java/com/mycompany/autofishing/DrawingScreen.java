package com.mycompany.autofishing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

public class DrawingScreen extends JComponent
{

    private BufferedImage screenFullImage;
    private BufferedImage scannedImage;
    private Dimension size;

    private int xPressed;
    private int yPressed;

    private boolean imageIsScanned = false;
    private boolean IsScreenDetermined = false;
    public  Rectangle rectClickedArea;

    private static Map<String,Shape> shapeList = new HashMap<>();

    private JFrame frame;
    private ImageProcess imageProcess;
    private GameObjectCoordinates gameObjCoor;
    public DrawingScreen(JFrame frame,ImageProcess imageProcess)
    {
        this.frame = frame;
        this.imageProcess = imageProcess;
        gameObjCoor = new GameObjectCoordinates();
        size = Toolkit.getDefaultToolkit().getScreenSize();
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());

    }
    public void getScreenShot()
    {
        try {
            Robot robot = new Robot();
            System.out.println(" MouseComponent class getScreenShot method is run ");
            // Rectangle screenRect = new Rectangle(size);
            screenFullImage = robot.createScreenCapture(new Rectangle(size));
            screenFullImage.flush();
            // String path = "C://Users//Herkes//Desktop//Test.jpg";
            //  ImageIO.write(screenFullImage,"jpg" , new File(path));

        } catch (AWTException  ex) {
            System.out.println(ex);
        }



    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;


        if(screenFullImage != null)
        {
            g2.drawImage(screenFullImage,null,0,0);
        }

        if(rectClickedArea != null)
        {
           //rectClickedArea = new Rectangle(rectClickedArea.x,rectClickedArea.y,10,rectClickedArea.height);
           rectClickedArea = gameObjCoor.getRectMetin2Icon();
            g2.setColor(Color.red);
            g2.draw(rectClickedArea);
        }
    }

    public void scanBufferImage(JLabel label)
    {
        try {
            Robot robot = new Robot();

            BufferedImage scannedBuf =  robot.createScreenCapture(rectClickedArea);
            //BufferedImage scannedBuf = screenFullImage.getSubimage(111,576,25,13);
            if(scannedBuf != null)
            {
                int [] arrayScanned ;
                scannedImage = scannedBuf;
                label.setIcon(new ImageIcon(scannedBuf));
                String homePath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().toString();
                String path = homePath + File.separator + "Test.png";
                ImageIO.write(scannedImage,"png" , new File(path));
                arrayScanned = imageProcess.transferBufferImgtoARGB(scannedImage);
                imageProcess.convertARGBarrayToRGB(arrayScanned);
                imageProcess.printArrayAsRgb("Determined area RGB values = ", arrayScanned);
                scannedImage.flush();
            }
        } catch (AWTException | IOException ex) {

        }




    }
    @Override
    public Dimension getPreferredSize()
    { return new Dimension(size.width, size.height); }

   private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent event)
        {
            imageIsScanned = false;

            xPressed = event.getX();
            yPressed = event.getY();


            rectClickedArea = new Rectangle(xPressed, yPressed, 100, 11);
            //rectClickedArea = getRectDeterminedArea();
            repaint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {


            rectClickedArea = new Rectangle(rectClickedArea.x +1,rectClickedArea.y +1,rectClickedArea.width -1,rectClickedArea.height -1);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Clicked area coordinates = " + rectClickedArea.x + "," + rectClickedArea.y
                    + "," + rectClickedArea.width + "," + rectClickedArea.height);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            // scanBufferImage(thisLabel);
            imageIsScanned = true;
            scanBufferImage(AutoFishingJFrame.StaticLabelFishingArea);
            System.out.println("player yellow count = " + imageProcess.CountWantedColorPixel(imageProcess.PLAYER_YELLOW_COLOR,
                    imageProcess.takeScreenShotReturnRGBarray(rectClickedArea,false)));
            System.out.println("player white count = " + imageProcess.CountWantedColorPixel(imageProcess.CHAT_WHITE_COLOR,
                    imageProcess.takeScreenShotReturnRGBarray(rectClickedArea,false)));
            frame.setVisible(false);

        }
        }


    private class MouseMotionHandler implements MouseMotionListener
    {

        public void mouseDragged(MouseEvent e)
        {

            int x = e.getX();
            int y = e.getY();

            if(x > xPressed )
            {
                if(y > yPressed )
                {
                    rectClickedArea = new Rectangle(xPressed  , yPressed  ,
                            x - (xPressed ), y - (yPressed ) );

                }
                else
                {
                    rectClickedArea = new Rectangle(xPressed  , y   ,
                            x - (xPressed ), (yPressed ) - y );

                }
            }
            else
            {
                if(y > yPressed )
                {
                    rectClickedArea = new Rectangle(x , yPressed  ,
                            (xPressed ) - x, y - (yPressed ) );
                }
                else
                {
                    rectClickedArea = new Rectangle(x , y   ,
                            (xPressed ) - x, (yPressed ) - y );

                }
            }

            //screenRect = new Rectangle2D.Double(xPressed -7 , yPressed -30 ,x , y );
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    public void addNewShapeToList(String keyName,Shape shape)
    {
        shapeList.put(keyName,shape);
    }

    public Shape getWantedShapeFromMapList(String keyName)
    {
        try
        {
            Shape temppShape = shapeList.get(keyName);
            return temppShape;
        }catch (NullPointerException e)
        {
            System.out.println("It couldn't find wanted shape so returned null");
            return  null;
        }

    }
}







