package com.mycompany.autofishing;

import java.awt.*;

public class ChangeableGameObjCoor
{

    public static volatile Point metin2IconCoor = new Point(0,0);
    public Rectangle getRectInventory()
    {

       // return new Rectangle(687 ,227 ,25,11);
        return new Rectangle(697 ,229 ,25,11);
    }

    public Rectangle getRectFirstSlotPlace()
    {

        //return new Rectangle(635,286,30,15);
        //return new Rectangle(635 ,284 ,32,16);
        return new Rectangle(640 ,286 ,32,16);
    }

    public Rectangle getRectFisherShopPage()
    {
        //return new Rectangle(475 ,51,21,8);
        return new Rectangle(485 ,53,21,8);
    }
    public Rectangle getRectYabbieIcon()
    {
        return getRectFirstSlotPlace();
    }
    public Rectangle getRectAltinIcon()
    {
        return new Rectangle(getRectFirstSlotPlace().x +32,getRectFirstSlotPlace().y,
                getRectFirstSlotPlace().width,getRectFirstSlotPlace().height);
    }
    public Rectangle getRectKampIcon()
    {
        return new Rectangle(getRectFirstSlotPlace().x +64 ,getRectFirstSlotPlace().y ,
                getRectFirstSlotPlace().width,getRectFirstSlotPlace().height);
    }
    public Rectangle getRectEquipmentArea()
    {
        //return new Rectangle(690 ,284 ,16,6);
        return new Rectangle(700 ,286 ,16,6);
    }
    public Point getPointInventPageOne()
    {
       // return new Point(647,253 );
        return new Point(657,255 );
    }

    public Point getPointInventPageTwo()
    {
       // return new Point(688 ,253 );
        return new Point(698 ,255 );
    }

    public Point getPointFisherShopKampAtesi()
    {
        //return new Point(459 ,83 );
        return new Point(469 ,85 );
    }

    public Point getPointFisherShopCloseButton()
    {
        //return new Point(568 ,51 );
        return new Point(578 ,53 );
    }

    public Point getPointFisherShopFiftyWorm()
    {
        //return new Point(491 ,114);
        return new Point(501,116);
    }
    public Point getPointArmorPlace()
    {
        //return new Point(647 , 291 );
        return new Point(657 , 293);
    }


}
