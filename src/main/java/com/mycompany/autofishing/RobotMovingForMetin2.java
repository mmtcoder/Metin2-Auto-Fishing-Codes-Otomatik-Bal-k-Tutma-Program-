package com.mycompany.autofishing;

import java.awt.*;

public class RobotMovingForMetin2 extends Robot
{
     ImageProcess imageProcess ;
    public RobotMovingForMetin2(ImageProcess imageProcess) throws AWTException
    {
        super();
        this.imageProcess = imageProcess;
    }

    @Override
    public synchronized void mouseMove(int x, int y)
    {
        Rectangle rect = imageProcess.thisGameObjCoord.scannableRectMt2Icon
                (imageProcess,imageProcess.thisGameObjCoord.getRectMetin2Icon());

        Point pointDifference = new Point(0,0);

        if(rect != null)
        {
             pointDifference = new Point(rect.x - imageProcess.thisGameObjCoord.getRectMetin2Icon().x,
                    rect.y - imageProcess.thisGameObjCoord.getRectMetin2Icon().y);
        }


        super.mouseMove(x + pointDifference.x ,y + pointDifference.y);
    }

    public synchronized void mouseMoveIndependent(int x,int y)
    {
        super.mouseMove(x,y);
    }

    public synchronized void mouseMoveFast(int x, int y)
    {
        super.mouseMove(x + GameObjectCoordinates.metin2IconCoor.x ,
                y +GameObjectCoordinates.metin2IconCoor.y);
    }
}
