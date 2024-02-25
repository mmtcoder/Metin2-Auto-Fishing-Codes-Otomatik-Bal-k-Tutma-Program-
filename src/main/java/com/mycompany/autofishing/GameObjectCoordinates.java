
package com.mycompany.autofishing;

import java.awt.Point;
import java.awt.Rectangle;


public class GameObjectCoordinates extends  ChangeableGameObjCoor
{

    public final int DISTANCE_BTWN_INV_SLOTS = 32;

    public static final int DISTANCE_BETW_CHAT_HEIGHT =15;
    private  TimerGame timerGame = new TimerGame();
    private boolean isScannedMt2Icon = false;

    public GameObjectCoordinates()
    {
    }

    public Rectangle getRectFishClickArea()
    {

        //return new Rectangle(181, 154, 124, 124);
        //return new Rectangle(186, 160, 113, 113);
        return new Rectangle(196, 162, 113, 113);
    }

    public Rectangle getRectChatArea()
    {
        //return rectChatArea;
        //return new Rectangle(111,576,25,11);//chat1 area
        //return new Rectangle(111+10 ,576+2 ,40,11);//chat2 area
        return new Rectangle(117 ,581 ,40,11);//chat2 area
    }

    public Rectangle getRectUpChatArea()
    {
        return new Rectangle(getRectChatArea().x,getRectChatArea().y - DISTANCE_BETW_CHAT_HEIGHT,
                getRectChatArea().width,getRectChatArea().height);
    }
    public Rectangle getRectExitChatArea()
    {
        return new Rectangle(120+10 ,576+2 ,30,11);
    }
    
    public Rectangle getRectCrossFish()
    {
        return new Rectangle(372 ,92 ,25,11);
    }
    public Rectangle getRectFishTitle()
    {
        return new Rectangle(222 ,89 ,10,2);
    }
    
    public Rectangle getRectEntryScreen()
    {
        return new Rectangle(236+10 ,120+2 ,10,2);
    }
    
    public Rectangle getRectCharScreen()
    {
        return new Rectangle(24,64,25,11);
    }
    
    public Rectangle getRectCharButton()
    {
        //return new Rectangle(359,350,25,11);
        return new Rectangle(351,360,25,11);
    }
    public Rectangle getRectDieScreen()
    {
        return new Rectangle(105,104,25,11);
    }
    
    public Rectangle getRectSaleCross()
    {
        return new Rectangle(206,444,25,11);
    }
    

    public Rectangle getRectSettingButton()
    {
        return new Rectangle(788,607,10,10);
    }
      
    /*public Rectangle getRectSkillSlotFirstPlace()
    {
        return new Rectangle(317,607,32,16);
    }*/
    public Rectangle getRectSkillSlotFirstPlace()
    {
        return new Rectangle(322,609,32,16);
    }
    /*public Rectangle getRectSkillSlotSecondPlace()
    {
        return new Rectangle(459,607,32,16);
    }*/
    public Rectangle getRectSkillSlotSecondPlace()
    {
        return new Rectangle(464,609,32,16);
    }
    /*public Rectangle getRectPageNumCoordinates()
    {
        return new Rectangle(589,604,11,8);
    }*/
    public Rectangle getRectPageNumCoordinates()
    {
        return new Rectangle(599,606,11,8);
    }
    /*public Rectangle getRectMetin2GameScreen()
    {
        return new Rectangle(2,26,800,598);
    }*/
    public Rectangle getRectMetin2GameScreen()
    {
        return new Rectangle(12,28,800,598);
    }
    /*public Rectangle getRectMiniMapSymbolCoor()
    {
        return new Rectangle(657,90,14,10);
    }*/
    public Rectangle getRectMiniMapSymbolCoor()
    {
        return new Rectangle(667,92,14,10);
    }
    /*public Rectangle getRectMiniMapArea()
    {
        return new Rectangle(678,36,117,117);
        //return new Rectangle(698,61,73,68);
    }*/
    public Rectangle getRectMiniMapArea()
    {
        return new Rectangle(688,38,117,117);
        //return new Rectangle(698,61,73,68);
    }
    /*public Rectangle getRectWoodDetectinonArea()
    {
       // return new Rectangle(290,430,209,2);
          return new Rectangle(2,430,800,2);
    }*/
    public Rectangle getRectWoodDetectinonArea()
    {
        // return new Rectangle(290,430,209,2);
        //return new Rectangle(12,432,800,2);
        return new Rectangle(22,434,800,2);
    }
    public Rectangle getRectReverseWoodDetectionArea()
    {
        //return new Rectangle(2,185,800,2);
        return new Rectangle(12,187,800,2);
    }
    
    public Rectangle getRectFisherSample()
    {
        //return new Rectangle(581,340,31,12);
        //return new Rectangle(591,342,31,12);
        return new Rectangle(419,407,40,14);
    }
    
    public Rectangle getRectFisherOptionsPage()
    {
        //return new Rectangle(378,242,20,11);
        return new Rectangle(388,244,20,11);
    }


    //Char asagi yone bakarken kamp atesinin yandigi koordinati
    public Rectangle getRectKampAtesiAsagiTarafKoordinat()
    {
        //return new Rectangle(370,328,64,60);
        return new Rectangle(380,330,64,60);
    }
    
    public Rectangle getRectYereAtmaAlgilama()
    {
       // return new Rectangle(438,287,48,16);
        return new Rectangle(448,289,48,16);
    }
   // public Rectangle getRectMetin2Icon(){return  new Rectangle(20,12 ,6,6);}
    public Rectangle getRectMetin2Icon(){return  new Rectangle(8+10,9+2 ,6,6);}
    //This and one below rectangle coordinates takes from "gameforge.png" image
    public Rectangle getRectGameForgeDesktopIcon()
    {
       // return new Rectangle(802,941,13,9);
        return new Rectangle(812,943,13,9);}

    public Rectangle getRectGameForgePlayButton()
    {
        //return new Rectangle(354,275,35,12);
        return new Rectangle(364,277,35,12);
    }
    public Rectangle getRectGameForgeMaximizeIcon()
    {
        //return  new Rectangle(1088,92,9,10);
        return  new Rectangle(1098,94,9,10);
    }
    public Rectangle getRectGameForgeOptionsButton()
    {
       // return new Rectangle(482,279,9,6);
        return new Rectangle(492,281,9,6);
    }
    public Rectangle getRectGameForgeOptionOynaButton()
    {
        //return new Rectangle(332,377,23,5);
        return new Rectangle(342,379,23,5);
    }
    public Rectangle getRectChatIsActive()
    {
        //return new Rectangle(113,571,36,10);
        return new Rectangle(123,573,36,10);
    }
    public Rectangle getRectCharNameArea(){return new Rectangle(304,304,210,15);}
    public Rectangle getRectInventIsFullPanel(){
       // return new Rectangle(346,311,9,3);
        return new Rectangle(356,313,9,3);
    }


    public Rectangle getRectLicenseImgCoord(){return new Rectangle(656,235,7,4);}
    public Point getPointChSix()
    {
       // return new Point(486,289);
        return new Point(496,291);
    }
    
    public Point getPointOkButton()
    {
       // return new Point(525,514);
        return new Point(535,516);
    }
    
    public Point getPointExitButton()
    {
       // return new Point(395,379);
         //return new Point(395,391);
        return new Point(405,393);
    }

     public Point getPointCharEnterButton()
     {
        // return new Point(353,570);
         return new Point(363,572);
     }
     

     public Point getPointYesButton()
     {
        // return new Point(358,346);
         return new Point(368,348);
     }

    public Rectangle scannableRectMt2Icon(ImageProcess classOrDerivatives,Rectangle oldPosition)
    {

        //if(metin2IconCoor != null)
        if(!isScannedMt2Icon)
        {
            Rectangle rectNewMetin2Icon =  classOrDerivatives.findWantedImageFromScreen(classOrDerivatives.getArrayRgbMetin2Icon(),
                    getRectMetin2Icon(),null);
            if(rectNewMetin2Icon != null)
            {

                metin2IconCoor = new Point(rectNewMetin2Icon.x - getRectMetin2Icon().x,
                        rectNewMetin2Icon.y - getRectMetin2Icon().y);
                // metin2IconCoor.x = rectNewMetin2Icon.x - getRectMetin2Icon().x;
                // metin2IconCoor.y = rectNewMetin2Icon.y - getRectMetin2Icon().y;

            }
            else
            {
                System.out.println("Program couldn't get the metin2 icon");
                return new Rectangle(0,0,oldPosition.width,oldPosition.height);
            }
            timerGame.SetStartedSecondTime();
            isScannedMt2Icon = true;
        }
        else
        {

            if(timerGame.CheckDelayTimeInSecond(60))
            {
                return new Rectangle(oldPosition.x + metin2IconCoor.x , oldPosition.y + metin2IconCoor.y,
                        oldPosition.width,oldPosition.height);
            }
            //Update screen poz values every 10 minutes.
            else {

                isScannedMt2Icon = false;
                timerGame.SetStartedSecondTime();
            }
        }
      /*  System.out.println((oldPosition.x + metin2IconCoor.x) +" yy "+ (oldPosition.y + metin2IconCoor.y )+
               "width " + oldPosition.width +"height" + oldPosition.height);*/
        return new Rectangle(oldPosition.x + metin2IconCoor.x , oldPosition.y + metin2IconCoor.y,
                oldPosition.width,oldPosition.height);

        //System.out.println("new Rect value = " + rectNewMetin2Icon);
        //System.out.println("old Rect value = " + oldPosition);
        //System.out.println("Metin2IconCoor value = " + metin2IconCoor);


    }
     
    //Yere atma dilaog ekranindan kesit alma = 438 287 48 16
    //Balikci "marketi ac" yazisi koordinatlari 375 240 42 15
    //Balikci yazisinin resimdeki koordinati = 581 340 31 12
    // Balik alanindaki taranacak tahta alani = 290 430 209 2
    // mini haritadaki karakterin koordinatı = 729 91 11 7
    // beceri koyma yerinin en sol koordinatı = 317 607 32 16
    // sayfa belirten sayının koordinatı = 589 604 11 8
    // envanter bir sayfa butonu = 651 253 15 20
    // envanter ikinci sayfa butonu = 688 253 15 20
    // envanter birinci sayfa butonu = 647 253 
    // envanterin en baş slot değeri 635,284
    // envanterin bir slot genişliği 32,32
    // setting button info = 788 607 25 11
    // exit button info = 395 379 25 11
    // die button info = 103 103 25 11
    // ch6 info = 486 289 25 11  diger ch ler arasindaki yukselik 17 pixel
    // enrty screen "tamam " button info = 525 514 25 11
    // character screen info = 24 64 25 11
    // character button info = 359 350 25 11
    // sale dialog close info = 206 444 25 11

    

   
}
