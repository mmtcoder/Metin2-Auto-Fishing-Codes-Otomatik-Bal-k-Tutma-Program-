
package com.mycompany.autofishing;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class PrepareFishing
{

    private GameObjectCoordinates detScreen;
    private FishingProcess fishingProcess;
    //private GameStatus GameStatus;
    private BufferedImage bufferImage;
    
    private int[] arrayRgbScanned;
    private int[] arrayRgbWormVal;
    private int[] array200WhiteIndex;
    private int[] arrayBalikciGreen;
    private int[] arrayKampAtesiGreen;
    private int[] array200WormsInfo = null;
    
    private int [] pixelDetectedValues = new int[2];
    private int [] pixelDetectedIndexes = new int[2];
            
    private boolean isGrillOperationSuccesfully = true;
    private boolean isInventorFull =false;
    
    private int balikciGreenCount;
    private int kampAtesiGreenCount;
    private int colorWhiteAraEkran;
    private int worm200Count;
    private int inventoryPage = 1;
    private final int INVENTOR_WIDTH_COUNT =5;
    private final int INVENTOR_HEIGHT_COUNT = 9;
    private final int INVENTOR_SLOT_WIDTH = 32;
    private final int NEEDED_WORM200_COUNT = 32;

    public static final int MOUSE_CLICK_RIGHT = InputEvent.BUTTON3_DOWN_MASK;
    public static final int MOUSE_CLICK_LEFT = InputEvent.BUTTON1_DOWN_MASK;
    
    private final int KAMP_FIRE_TIME = 39;
    
    public PrepareFishing( FishingProcess fishingProcess)
    {

        bufferImage = null;
        detScreen = new GameObjectCoordinates();
        this.fishingProcess = fishingProcess;
        //GameStatus = new GameStatus();
          
        arrayRgbScanned = fishingProcess.readBufCnvrtRGB(fishingProcess.getPathWorm200(), detScreen.getRectFirstSlotPlace(), 
                bufferImage, arrayRgbScanned);
        
        bufferImage =  fishingProcess.readImage(fishingProcess.getPathGameforge4());
        bufferImage = fishingProcess.getBufferSubImage(bufferImage, detScreen.getRectGameForgeOptionOynaButton());
        //printArrayAsRgb("oyna  button value = " ,getArrayRgbGameForgeOynaButton());
        AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(fishingProcess.transferRGBtoBufferedImage
        (fishingProcess.getArrayRgbChatOpen(),detScreen.getRectChatIsActive())));

           arrayRgbWormVal = DetectWorms(arrayRgbScanned);
           array200WhiteIndex = DetectWhiteNumIndex(arrayRgbScanned);
           arrayBalikciGreen = fishingProcess.RecordWantedColorIndex(fishingProcess.MAP_BALIKCI_GREEN, fishingProcess.getArrayRgbBalikci());
           balikciGreenCount = fishingProcess.CountWantedColorPixel(fishingProcess.MAP_BALIKCI_GREEN, fishingProcess.getArrayRgbBalikci());
        System.out.println("Balicki green count = " + balikciGreenCount);
           kampAtesiGreenCount = fishingProcess.CountWantedColorPixel(fishingProcess.MAP_BALIKCI_GREEN, 
                   fishingProcess.getArrayRgbKampAtesi());
           array200WormsInfo = new int[2*NEEDED_WORM200_COUNT +1];
           

           colorWhiteAraEkran = fishingProcess.CountWantedColorPixel(fishingProcess.COLOR_WHITE, fishingProcess.getArrayRgbBalikciAraEkran());
           
    }
    
    public void StartPrepareFishing()
    {
            FindFisher();
            BuyKampFireFromFisher();
            BuyWormsFromFisher();
            GoToFishPlace();
          //if(thisFishingProc.getGameStatusOrUserPreffer())
          if(GameStatus.GetGameStatusOrUserPrefer())
            {
             fishingProcess.fillWithZeroArray(array200WormsInfo);
             if(isInventorFull == false)
             {
                 fishingProcess.setShiftNumber(1);
             }
             fishingProcess.CheckShiftPageNumber();
            }
    }
    public void FindFisher()
    {
       
            int [] arrayRgb ;
        BufferedImage buf;
        int clickingSide =1;
        TimerGame timerGame = new TimerGame();
        timerGame.SetStartedSecondTime();


            try {
                //Robot robot = new Robot();
                RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);

                    robot.delay(2000);
                    System.out.println("com.mycompany.autofishing.PrepareFishing.FindFisher()");

                    //Check Character Button is active and close it
                    OpenOrCloseSettingButton(false);

                    //Change the View perspective of the character as bird view
                    robot.keyPress(KeyEvent.VK_F);
                    robot.delay(30);
                    robot.keyPress(KeyEvent.VK_G);
                    robot.delay(1500);
                    robot.keyRelease(KeyEvent.VK_F);
                    robot.delay(30);
                    robot.keyRelease(KeyEvent.VK_G);

                    OpenOrCloseInventory(false);

                    //while(CheckFisherIsThere() != true && thisFishingProc.getIsStopped() == false)
                    while (CheckFisherIsThere() != true) {


                        if(timerGame.CheckDelayTimeInSecond(30))
                        {
                        //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                        if (!GameStatus.GetGameStatusOrUserPrefer()) {
                            System.out.println("Returned from CheckFisherisThre while loop");
                            return;
                        }

                        buf = robot.createScreenCapture(detScreen.getRectWoodDetectinonArea());
                        arrayRgb = fishingProcess.transferBufferImgtoARGB(buf);
                        fishingProcess.convertARGBarrayToRGB(arrayRgb);
                        //  printArrayAsRgb("arrayRgb value = ", arrayRgb);

                        int[] arrayWoodColors = new int[2];
                        int[] arrayColorRed = new int[arrayWoodColors.length + 1];
                        int[] arrayColorGreen = new int[arrayWoodColors.length + 1];
                        int[] arrayColorBlue = new int[arrayWoodColors.length + 1];

                        int comparableArrayIndex = arrayWoodColors.length;
                        final int MINIMUM_INDEX = 0;
                        final int MAX_INDEX = 1;

                        int minDetectedIndex = 0xfffffff;
                        int maxDetectedIndex = 0;
                        int tempIndex = 0;
                        int mousePosition = 0;


                        arrayWoodColors[MINIMUM_INDEX] = fishingProcess.MIN_WOOD_VALUE;
                        arrayWoodColors[MAX_INDEX] = fishingProcess.MAX_WOOD_VALUE;

                        //This loop gets the wood colors to needed arrays
                        for (int k = 0; k < arrayWoodColors.length; k++) {
                            arrayColorRed[k] = (arrayWoodColors[k] & 0x00ff0000) >> 16;
                            arrayColorGreen[k] = (arrayWoodColors[k] & 0x0000ff00) >> 8;
                            arrayColorBlue[k] = arrayWoodColors[k] & 0x000000ff;
                        }

                        for (int i = 0; i < arrayRgb.length; i++) {

                            arrayColorRed[comparableArrayIndex] = (arrayRgb[i] & 0x00ff0000) >> 16;
                            arrayColorGreen[comparableArrayIndex] = (arrayRgb[i] & 0x0000ff00) >> 8;
                            arrayColorBlue[comparableArrayIndex] = arrayRgb[i] & 0x000000ff;

                            if (arrayColorRed[comparableArrayIndex] > arrayColorGreen[comparableArrayIndex]
                                    && arrayColorGreen[comparableArrayIndex] > arrayColorBlue[comparableArrayIndex]) {


                                if (arrayColorRed[comparableArrayIndex] > arrayColorRed[MINIMUM_INDEX] &&
                                        arrayColorGreen[comparableArrayIndex] > arrayColorGreen[MINIMUM_INDEX]
                                        && arrayColorBlue[comparableArrayIndex] > arrayColorBlue[MINIMUM_INDEX]) {
                                    if (arrayColorRed[comparableArrayIndex] < arrayColorRed[MAX_INDEX] &&
                                            arrayColorGreen[comparableArrayIndex] < arrayColorGreen[MAX_INDEX]
                                            && arrayColorBlue[comparableArrayIndex] < arrayColorBlue[MAX_INDEX]) {


                                        tempIndex = i - ((i / buf.getWidth()) * buf.getWidth());
                                        if (minDetectedIndex > tempIndex) {
                                            minDetectedIndex = tempIndex;
                                            //System.out.println("minDetectVal = " + minDetectedIndex + "index = " + i);
                                        }
                                        if (maxDetectedIndex < tempIndex) {
                                            maxDetectedIndex = tempIndex;
                                            //    System.out.println("maxDetecdVal = " + maxDetectedIndex + "index = " + i);
                                        }

                                    }
                                }
                            }

                        }

                        if (maxDetectedIndex != 0xfffffff) {

                            double chancingValue = 150.0;
                            if (clickingSide == 1) {
                                mousePosition = Math.abs((maxDetectedIndex + minDetectedIndex)) / 2 - (int) (Math.random() * chancingValue);
                                System.out.println("mousePosition = " + mousePosition);
                                clickingSide = 2;
                            } else {
                                mousePosition = Math.abs((maxDetectedIndex + minDetectedIndex)) / 2 + (int) (Math.random() * chancingValue);
                                System.out.println("mousePosition = " + mousePosition);
                                clickingSide = 1;
                            }

                            robot.mouseMove(detScreen.getRectWoodDetectinonArea().x + mousePosition,
                                    detScreen.getRectWoodDetectinonArea().y);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(20);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(400);
                            // CheckFisherIsThere();
                        }
                    }
                        else
                        {
                            fishingProcess.SettingButtonsPreferring(fishingProcess.CHAR_BUTTON_SETTING);
                            while (GameStatus.isCharScreenActive){}
                            robot.keyPress(KeyEvent.VK_A);
                            robot.delay(2000);
                            robot.keyRelease(KeyEvent.VK_A);
                            robot.delay(500);
                            robot.keyPress(KeyEvent.VK_S);
                            robot.delay(1000);
                            robot.keyRelease(KeyEvent.VK_S);
                            robot.delay(20);
                            OpenOrCloseSettingButton(false);
                            timerGame.SetStartedSecondTime();
                        }
                }


                //printArray("yabbie degerleri = ",  CheckWantedObjectFromInventory(getArrayRgbYabbieIcon()));
                //printArray("altin degerleri = ",  CheckWantedObjectFromInventory(getArrayRgbAltinIcon()));


            } catch (AWTException ex) {

            }



    }
    public void MakeCallibration()
    {
        //Kalibre ayari yapmak icin envanterin 1.sayfa en üst sol 
        //kosesinin bir altina ve bir sagina solucan koy
        Runnable r = ()
                ->
        {
            RobotMovingForMetin2 robot;
         int [] pixelScannedValues = new int[2];
         int [] pixelScannedIndex = new int[2];
        try {
             robot = new RobotMovingForMetin2(fishingProcess);
             //Get originall image from resources
             bufferImage = fishingProcess.readImage(fishingProcess.getPathWorm200());
             /*bufferImage= bufferImage.getSubimage(detScreen.getRectFirstSlotPlace().x, detScreen.getRectFirstSlotPlace().y , 
                     detScreen.getRectFirstSlotPlace().width,detScreen.getRectFirstSlotPlace().height);*/
             bufferImage = fishingProcess.getBufferSubImage(bufferImage, detScreen.getRectFirstSlotPlace());
             arrayRgbScanned = fishingProcess.transferBufferImgtoARGB(bufferImage);
            // labelFishingArea.setIcon(new ImageIcon(bufferImage));
             Thread.sleep(2000);
             
             fishingProcess.convertARGBarrayToRGB(arrayRgbScanned);
            // printArray("dsf", arrayRgbScanned);
            // printArrayAsRgb("Worm Rgb = ", arrayRgbScanned);
             ControlPixel(bufferImage, arrayRgbScanned, pixelDetectedValues, pixelDetectedIndexes);
             fishingProcess.printArrayAsRgb("global de kayitli olan = ", pixelDetectedValues);
             System.out.println("Kayitli index değerleri = " + pixelDetectedIndexes[0] + " " + 
                     pixelDetectedIndexes[1]);
                
             for (int xPos = -10; xPos < 10; xPos++)
             {
                bufferImage = robot.createScreenCapture(new Rectangle(detScreen.getRectSkillSlotSecondPlace().x  +xPos
                ,detScreen.getRectSkillSlotSecondPlace().y,detScreen.getRectSkillSlotSecondPlace().width
                ,detScreen.getRectSkillSlotSecondPlace().height));
                
                // labelFishingArea.setIcon(new ImageIcon(bufferImage));
                 Thread.sleep(500);
                arrayRgbScanned =fishingProcess.transferBufferImgtoARGB(bufferImage);
                fishingProcess.convertARGBarrayToRGB(arrayRgbScanned);
                 ControlPixel(bufferImage, arrayRgbScanned, pixelScannedValues, pixelScannedIndex);
                 if(pixelScannedValues[0] == pixelDetectedValues[0] && pixelScannedValues[1] == pixelDetectedValues[1]
                     && pixelDetectedIndexes[0] == pixelScannedIndex[0] && pixelDetectedIndexes[1] == pixelScannedIndex[1]  )
                 {
                     System.out.println("x koordinat kaydirmak icin gereken deger = " + xPos);
                     fishingProcess.printArrayAsRgb("global de kayitli olan = ", pixelScannedValues);
                     break;
                 }
             }
             /*for (int yCount = 0; yCount < 9; yCount++) 
             {
                 for (int xCount = 0; xCount < 5; xCount++) 
                 {
                      robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (32*xCount), detScreen.getRectFirstSlotPlace().y+ (32 * yCount));
                      robot.delay(500);
             
                 }
            }*/
            
        } catch (AWTException |InterruptedException ex) {
           
        }
        };
         
        Thread t = new Thread(r);
        if(!t.isAlive())
        {
              t.start();
        }
      
    }
    /*
    Brief : This functions combines worms,counts 200 worms count
    and records of the place in the inventory
    first [0]index is size of the 200 worm count
    second and ...+2 [1 + 2..] y coordinate of the 200 worm
    third and ...+2 [2 + 2..] x coordinate of the 200 worm
    */
    public int [] CombineAndCountWorms()
    {
        
        /* Runnable r = ()
                ->
        {*/
            
            BufferedImage imageScanned = null;
       // BufferedImage imageGetSubImage =null;
       
        int [] arrayScannedArray;
        int [] arrayScannedWorm;
        int [] arrayScannedWhiteNum;
        int [] rgbArray;
        int [] resultArray = new int[1+ (NEEDED_WORM200_COUNT *2)];
        int wormCounter = 0;
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
             
            Thread.sleep(2000);
            
            
                  OpenOrCloseInventory(true);
            
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                 for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                   // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return resultArray;
                    if(GameStatus.GetGameStatusOrUserPrefer() == false)return resultArray;

        /*    imageScanned = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));*/

            arrayScannedArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
      
          // arrayScannedArray = transferBufferImgtoARGB(imageScanned);
           // convertARGBarrayToRGB(arrayScannedArray);
            arrayScannedWorm = DetectWorms(arrayScannedArray);
            arrayScannedWhiteNum = DetectWhiteNumIndex(arrayScannedArray);
                    //printArray("scanned values = ", arrayScannedArray);
         
                    //Check if there is a worm at that place 
           // if(CompareArrays(arrayRgbWormVal, arrayScannedWorm))
           if(fishingProcess.CompareTwoArrayAdvanced(arrayScannedWorm, arrayRgbWormVal, fishingProcess.SENSIBILTY_HIGH))
            {
            
                //Check if worm count not equals to 200
                //if(CompareArrays(array200WhiteIndex, arrayScannedWhiteNum) == false)
                if(fishingProcess.CompareTwoArrayAdvanced(arrayScannedWhiteNum, array200WhiteIndex, fishingProcess.SENSIBILTY_HIGH) == false )
                {
                   
                     //System.out.println(" First Coordinates of first worm = "+ xPos +" " + yPos);
                     //Make a for loop for other worm
                    for (int y2Pos = yPos ; y2Pos < INVENTOR_HEIGHT_COUNT; y2Pos++) 
            {
                
                    for (int x2Pos = 0; x2Pos < INVENTOR_WIDTH_COUNT; x2Pos++) 
                     {
                               //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return resultArray;
                               if(GameStatus.GetGameStatusOrUserPrefer() == false)return resultArray;
                          //     System.out.println(" Second Coordinates of first worm = "+ x2Pos +" " + y2Pos);

                        /* imageScanned = robot.createScreenCapture(new Rectangle(
                         detScreen.getRectFirstSlotPlace().x +(32 *x2Pos), detScreen.getRectFirstSlotPlace().y +(32 *y2Pos)
                             ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));*/

                         arrayScannedArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                                 detScreen.getRectFirstSlotPlace().x +(32 *x2Pos), detScreen.getRectFirstSlotPlace().y +(32 *y2Pos)
                                 ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);

                        // imageGetSubImage = getBufferSubImage(imageScanned, detScreen.getRectFirstSlotPlace());
                         // arrayScannedArray = transferBufferImgtoARGB(imageScanned);
                          //convertARGBarrayToRGB(arrayScannedArray);
                          arrayScannedWorm = DetectWorms(arrayScannedArray);
                          arrayScannedWhiteNum = DetectWhiteNumIndex(arrayScannedArray);
                          
                          //If Second worm is been inventory
                          if(fishingProcess.CompareTwoArrayAdvanced(arrayRgbWormVal, arrayScannedWorm, fishingProcess.SENSIBILTY_HIGH))
                          {
                              //If worm count is not equal to 200 combine each other
                              if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) == false)
                              {
                                  if(x2Pos == xPos && y2Pos == yPos)
                                  {
                                      continue;
                                  }
                                  //Drag First worm 
                                  robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                          detScreen.getRectFirstSlotPlace().y + (yPos *32) + 16);
                                  
                                  robot.delay(20);
                                  robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                  robot.delay(20);
                                  
                                  robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (x2Pos * 32) +16,
                                          detScreen.getRectFirstSlotPlace().y + (y2Pos *32) + 16);
                                  
                                  robot.delay(20);
                                  robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                  robot.delay(600);
                                  robot.mouseMove(detScreen.getRectFirstSlotPlace().x -30 ,
                                         detScreen.getRectFirstSlotPlace().y + (y2Pos *32) + 16 );
                                  robot.delay(15);
                                  y2Pos = INVENTOR_HEIGHT_COUNT -1;
                                  
                                  xPos =0;
                                  yPos =0;
                                   break;
                              }
                          }
                     }
                   
            }
                }
            }
                }
            }
            }
           
            System.out.println("Solucanlar sayiliyor");
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                 for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                
                    // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return resultArray;
                     if(GameStatus.GetGameStatusOrUserPrefer() == false)return resultArray;

            /*imageScanned = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));*/

                    arrayScannedArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
      
         //  arrayScannedArray = transferBufferImgtoARGB(imageScanned);
            //convertARGBarrayToRGB(arrayScannedArray);
            arrayScannedWorm = DetectWorms(arrayScannedArray);
            arrayScannedWhiteNum = DetectWhiteNumIndex(arrayScannedArray);
                    //printArray("scanned values = ", arrayScannedArray);
         
                    //Check if there is a worm at that place 
           // if(CompareArrays(arrayRgbWormVal, arrayScannedWorm))
           if(fishingProcess.CompareTwoArrayAdvanced(arrayRgbWormVal, arrayScannedWorm, fishingProcess.SENSIBILTY_HIGH))
            {
            
                //Check if worm count not equals to 200
                //if(CompareArrays(array200WhiteIndex, arrayScannedWhiteNum) == false)
                if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH)  )
                {
                    if(wormCounter < NEEDED_WORM200_COUNT )
                    {
                        
                        resultArray[1 + (wormCounter * 2)] = yPos;
                        resultArray[2 + (wormCounter *2)] = xPos;
                        wormCounter++;
                    }
                  
                    
                }
                }
                }
            }
            }
            resultArray[0] = wormCounter;  
            fishingProcess.printArray("array200Worm Info = ", resultArray);
            
        } catch ( AWTException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        
        return resultArray;
       /* };
        
         Thread t = new Thread(r);
         if(t.isAlive() == false)
         {
             t.start();
         }*/
    }
    private void ControlPixel(BufferedImage buf,int []rgbArray,int [] pixDetVal,int [] pixDetIndx)
    {
         int colorRed =0;
         int colorGreen =0;
         int colorBlue =0;
         int detectCounter =0;
           for (int i = 0; i < buf.getHeight(); i++) 
             {
                 for (int j = 0; j < buf.getWidth(); j++) 
                 {
                     colorRed = (rgbArray[j] & 0x00ff0000) >> 16;
                     colorGreen = (rgbArray[j] & 0x0000ff00) >> 8;
                     colorBlue = rgbArray[j] & 0x000000ff;
                     
                     if(colorRed > 50 && colorGreen > 50 && colorBlue >40)
                   //  if(Math.abs(colorRed) > 80 && Math.abs(colorGreen)> 50
                    //        && Math.abs(colorBlue)> 40)
                     {
                         if(detectCounter == 1)
                         {

                             pixDetVal[1] = rgbArray[j];
                              pixDetIndx[1] = j;
                              System.out.println("algilanan deger 2 = " + rgbArray[j]);
                               System.out.println("algilanan index 2 = " + j);
                             return;
                         }
                         if(detectCounter == 0)
                         {
                             pixDetVal[0] = rgbArray[j];
                             pixDetIndx[0] = j;
                             detectCounter++;
                             System.out.println("algilanan deger = " + rgbArray[j]);
                               System.out.println("algilanan index = " + j);
                         }
                         
                     }
                 }
                
            }
    }
    
  
    
    private void CheckMiniMapIsVisible(int [] rgbArray)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);

            
            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(
                    detScreen.getRectMiniMapSymbolCoor(),true ),
                    fishingProcess.getArrayRgbMiniMapSymb(), fishingProcess.SENSIBILTY_MED) == false)
            {
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                robot.delay(30);
            }
           } catch (AWTException ex) {
            
        }
      
    }

    public void ClickAnItemFromInventory(int [] arrayFishCoordinates,int clickType)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);

            int yPos =0;
            int xPos =0;

            if(arrayFishCoordinates[0] > 0) {
                // System.out.println("arrayFishCoordinate Length = " + arrayFishCoordinates.length);
                for (int pageCounter = 1; pageCounter < 3; pageCounter++) {
                    ClickPage(pageCounter);
                    for (int yIndex = 1; yIndex < arrayFishCoordinates.length; yIndex += 2) {

                        // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                        if (GameStatus.GetGameStatusOrUserPrefer() == false)
                        {
                            System.out.println("returned from ClickAnItemFromInventory Method");
                            return;
                        }

                        yPos = arrayFishCoordinates[yIndex];
                        xPos = arrayFishCoordinates[yIndex + 1];



                            System.out.println("it clicked it");
                            robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                    detScreen.getRectFirstSlotPlace().y + (yPos *32));
                            robot.delay(20);
                            robot.mousePress(clickType);
                            robot.delay(50);
                            robot.mouseRelease(clickType);
                            robot.delay(500);
                            return;


                    }
                }
            }


        }
        catch (AWTException e)
        {
            System.out.println(e);
        }
    }

    public int [] DetectWorms(int [] rgbArray)
    {
        //TODO: BURADAYIM !!!
        int colorRed =0;
         int colorGreen =0;
         int colorBlue =0;
         int equalityVar =0;
         int arrayequalityVars[] ;
         int counter =0;
         
         for (int i = 0; i < rgbArray.length; i++) 
         {
             
              colorRed = (rgbArray[i] & 0x00ff0000) >> 16;
              colorGreen = (rgbArray[i] & 0x0000ff00) >> 8;
              colorBlue = rgbArray[i] & 0x000000ff;
                     
             if(colorRed > 48 && colorGreen > 48 && colorBlue >39)
             {
                 if(colorRed < 246 && colorGreen < 193 && colorBlue < 174)
                 {
                     equalityVar++;
                 }
             }
         }
         
         arrayequalityVars = new int[equalityVar];
         for (int k = 0; k < rgbArray.length; k++) 
         {
              colorRed = (rgbArray[k] & 0x00ff0000) >> 16;
              colorGreen = (rgbArray[k] & 0x0000ff00) >> 8;
              colorBlue = rgbArray[k] & 0x000000ff;
                     
             if(colorRed > 50 && colorGreen > 50 && colorBlue >40)
             {
                 if(colorRed < 246 && colorGreen < 193 && colorBlue < 174)
                 {
                     arrayequalityVars[counter++] = rgbArray[k];
                 }
             }
        }
         
         return arrayequalityVars;
    }

    public void ClickPage(int pageNumber)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
             
            for (int i = 0; i < 3; i++)
            {
        if(pageNumber == 1)
        {
            robot.mouseMove(detScreen.getPointInventPageOne().x, detScreen.getPointInventPageOne().y);
        }else if(pageNumber == 2)
        {
             robot.mouseMove(detScreen.getPointInventPageTwo().x, detScreen.getPointInventPageTwo().y);
        }else
        {
            System.out.println("Invalid Input !!!");
            return;
        }
            robot.delay(20);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);    
            
         
        }
             
        } catch (AWTException ex) {
            
        }
       
    }
    private int [] DetectWhiteNumIndex(int [] rgbArray)
    {
         int colorRed =0;
         int colorGreen =0;
         int colorBlue =0;
         int equalityVar =0;
         int arrayequalityVars[] ;
         int counter =0;
         
         for (int i = 0; i < rgbArray.length; i++) 
         {
             
              colorRed = (rgbArray[i] & 0x00ff0000) >> 16;
              colorGreen = (rgbArray[i] & 0x0000ff00) >> 8;
              colorBlue = rgbArray[i] & 0x000000ff;
                     
             
               if(colorRed == 0xff && colorGreen == 0xff && colorBlue == 0xff)
               {
                     equalityVar++;
               }
             
         }
         
         arrayequalityVars = new int[equalityVar];
         for (int k = 0; k < rgbArray.length; k++) 
         {
              colorRed = (rgbArray[k] & 0x00ff0000) >> 16;
              colorGreen = (rgbArray[k] & 0x0000ff00) >> 8;
              colorBlue = rgbArray[k] & 0x000000ff;
                     
             if(colorRed == 0xff && colorGreen == 0xff && colorBlue == 0xff)
             {
                 
                     arrayequalityVars[counter++] = k;
                 
             }
        }
         
         return arrayequalityVars;
    }
    
     
    
    private boolean CheckFisherIsThere()
    {
        BufferedImage buf;
        int [] rgbArray;
        
        
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
           // buf = robot.createScreenCapture(detScreen.getRectMetin2GameScreen());
            buf = robot.createScreenCapture(detScreen.scannableRectMt2Icon(fishingProcess,detScreen.getRectMetin2GameScreen()));
            rgbArray = fishingProcess.transferBufferImgtoARGB(buf);
            fishingProcess.convertARGBarrayToRGB(rgbArray);

            
            /*robot.keyPress(KeyEvent.VK_A);
            robot.delay(50);
            robot.keyRelease(KeyEvent.VK_A);
            robot.delay(500);*/
            
            System.out.println("rgbArray Length = " + rgbArray.length);
             int[] colorRed = new int [2];
             int [] colorGreen = new int[2];
             int [] colorBlue = new int[2];
             int totalBalikciGreenVal =0;
             int counter =0;
             int counter2 =0;
             int maxYValue = 0;
             int maxXValue = 0;
             int minYValue = 0xffffff;
             int minXValue = 0xffffff;
           
              colorRed[0] = (fishingProcess.MAP_BALIKCI_GREEN & 0x00ff0000) >> 16;
              colorGreen[0] = (fishingProcess.MAP_BALIKCI_GREEN & 0x0000ff00) >> 8;
              colorBlue[0] = fishingProcess.MAP_BALIKCI_GREEN & 0x000000ff;
              
              for (int yPos = 0;yPos < buf.getHeight(); yPos++) 
              {
                  for (int xPos = 0; xPos < buf.getWidth(); xPos++) 
                  {
                       colorRed[1] = (rgbArray[xPos + counter] & 0x00ff0000) >> 16;
                       colorGreen[1] = (rgbArray[xPos + counter] & 0x0000ff00) >> 8;
                       colorBlue[1] = rgbArray[xPos + counter] & 0x000000ff;
                       
                      // System.out.println("first loop value = " + (xPos + counter));
                       if(Math.abs(colorRed[0] - colorRed[1])< 3 && Math.abs(colorGreen[0] - colorGreen[1])< 3
                               && Math.abs(colorBlue[0] - colorBlue[1])< 3)
                       {
                           System.out.println("xPos value = " + xPos + "Ypos Value = " + yPos);
                           counter2 -= buf.getWidth()*(detScreen.getRectFisherSample().height/2);
                           for (int yPos2 = yPos - detScreen.getRectFisherSample().height/2; 
                                   yPos2 <yPos + 2*detScreen.getRectFisherSample().height ; yPos2++) 
                           {
                              
                               for (int xPos2 = xPos - detScreen.getRectFisherSample().width/2; 
                                       xPos2 < xPos + 2*detScreen.getRectFisherSample().width; xPos2++) 
                               {
                                           // System.out.println("x2Pos value = " + xPos2 + " Y2pos Value = " + yPos2);                      
                                  //System.out.println("second loop value = " + (xPos2 + counter + counter2));
                                   colorRed[1] = (rgbArray[xPos2 + counter + counter2] & 0x00ff0000) >> 16;
                                   colorGreen[1] = (rgbArray[xPos2 + counter + counter2] & 0x0000ff00) >> 8;
                                   colorBlue[1] = rgbArray[xPos2 + counter + counter2] & 0x000000ff;
                                   
                                if(Math.abs(colorRed[0] - colorRed[1])< 3 && Math.abs(colorGreen[0] - colorGreen[1])< 3
                             && Math.abs(colorBlue[0] - colorBlue[1])< 3)
                       {
                           //System.out.println("xPos value = " + xPos + "Ypos Value = " + yPos);
                          // System.out.println("x2Pos value = " + xPos2 + "Y2pos Value = " + yPos2);
                           if(maxYValue < yPos2 )
                           {
                               maxYValue = yPos2;
                           }
                           if(minYValue > yPos2)
                           {
                               minYValue = yPos2;
                           }
                           if(maxXValue < xPos2)
                           {
                               maxXValue = xPos2;
                                       
                           }
                           if(minXValue > xPos2)
                           {
                               minXValue = xPos2;
                           }
                           /*robot.mouseMove(detScreen.getRectMetin2GameScreen().x + minXValue
                                   , detScreen.getRectMetin2GameScreen().y + minYValue);
                           
                           robot.delay(500);
                           
                            robot.mouseMove(detScreen.getRectMetin2GameScreen().x + maxXValue
                                   , detScreen.getRectMetin2GameScreen().y + maxYValue);
                            
                            robot.delay(500);*/
                           totalBalikciGreenVal++;
                       }
                                  
                               }
                               counter2 += buf.getWidth();
                           }

                            System.out.println("maxY = " + maxYValue + "maxX = " + maxXValue + 
                                           "minY = " + minYValue + "minX = " + minXValue);
                           System.out.println("olculen yesil degeri = " + totalBalikciGreenVal);
                           if(Math.abs(totalBalikciGreenVal - balikciGreenCount) <3)
                           {
                               //Point point = new Point(xPos + detScreen.getRectFisherSample().width/2,
                                   //    yPos + detScreen.getRectFisherSample().height);
                               Point point = new Point((minXValue + maxXValue)/2 + detScreen.getRectMetin2GameScreen().x,
                                     (minYValue +  maxYValue)/2 + detScreen.getRectMetin2GameScreen().y );
                               System.out.println("tiklanan x coordinat = " + point.x + "y coor = " + point.y);
                               robot.mouseMove(point.x, point.y);
                               robot.delay(20);
                               robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                               robot.delay(20);
                               robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                               robot.delay(1200);
                               robot.mouseMove(detScreen.getRectFisherOptionsPage().x + 400, detScreen.getRectFisherOptionsPage().y);
                               robot.delay(10);
                               counter2 =0;
                               maxXValue =0;
                               maxYValue =0;
                               minXValue =0xffffff;
                               minYValue =0xffffff;
                              // buf = robot.createScreenCapture(detScreen.getRectFisherOptionsPage());
                               buf = robot.createScreenCapture(detScreen.scannableRectMt2Icon(fishingProcess,detScreen.getRectFisherOptionsPage()));
                                rgbArray = fishingProcess.transferBufferImgtoARGB(buf);
                                fishingProcess.convertARGBarrayToRGB(rgbArray);
                                
                                System.out.println("kiyaslama yapiliyor");
                                 
                               if(fishingProcess.CountWantedColorPixel(fishingProcess.COLOR_WHITE, rgbArray) == colorWhiteAraEkran)
                               {
                                   System.out.println("deger true");
                                  return true; 
                               }
                             
                               
                           }
                           else
                           {
                               // System.out.println("Before xPos value = " + xPos + "Before Ypos Value = " + yPos);
                               //counter -= buf.getWidth();
                               counter2 =0;
                               maxXValue =0;
                               maxYValue =0;
                               minXValue =0xffffff;
                               minYValue =0xffffff;
                               counter+= buf.getWidth() * (2*detScreen.getRectFisherSample().height-1);
                                    xPos += ((2*detScreen.getRectFisherSample().width-1));
                                     yPos += 2*detScreen.getRectFisherSample().height-1;
                                     if(yPos >= buf.getHeight())
                                     {
                                         return false;
                                     }
                                    // System.out.println("After xPos value = " + xPos + "After Ypos Value = " + yPos);
                                    totalBalikciGreenVal =0;
                                  //    System.out.println("array index value = "+ (counter + xPos));
                               
                              
                           }
                       }
                  }
                  counter += buf.getWidth();
                 //  System.out.println("Counter value = " + counter);
            }
                    
        } catch (AWTException ex) 
        {
            
        }
        return false;
    }
    
    public void BuyKampFireFromFisher()
    {
      int [] rgbArray ;
      BufferedImage buf;
      TimerGame timerGame = new TimerGame();
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            robot.mouseMove(detScreen.getRectFisherOptionsPage().x, detScreen.getRectFisherOptionsPage().y);
            robot.delay(15);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1500);

          
             //Check if fisher shop page is open or not 
            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(
                    detScreen.getRectFisherShopPage(),true
            ), fishingProcess.getArrayRgbfullEnvAndBlkShop(), fishingProcess.SENSIBILTY_MED) == false)
            {
                 //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
            robot.mouseMove(detScreen.getRectFisherOptionsPage().x, detScreen.getRectFisherOptionsPage().y);
            robot.delay(15);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1500);


           
            }
            int [] arrayKamp = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbKampIcon());
            
            while(arrayKamp[0] <= 0)
            {
                if(timerGame.CheckDelayTimeInSecond(60))
                {
                    if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed || GameStatus.isCharKilled)return;
                    robot.mouseMove(detScreen.getPointFisherShopKampAtesi().x, detScreen.getPointFisherShopKampAtesi().y);
                    robot.delay(20);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.delay(20);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    robot.delay(1000);

                    arrayKamp = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbKampIcon());

                    if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbinventIsFull(),
                            fishingProcess.takeScreenShotReturnRGBarray(detScreen.getRectInventIsFullPanel(),true),
                                    fishingProcess.SENSIBILTY_MED))
                    {
                        robot.keyPress(KeyEvent.VK_ESCAPE);
                        robot.delay(30);
                        robot.keyRelease(KeyEvent.VK_ESCAPE);
                        robot.delay(20);

                        ThrowAWorm();

                        robot.mouseMove(detScreen.getPointFisherShopKampAtesi().x, detScreen.getPointFisherShopKampAtesi().y);
                        robot.delay(20);
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(20);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(1000);

                        arrayKamp = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbKampIcon());
                    }
                }
                else
                {
                    fishingProcess.SettingButtonsPreferring(fishingProcess.CHAR_BUTTON_SETTING);
                    while(GameStatus.isCharScreenActive){if(GameStatus.isStopped)return;}
                    FindFisher();

                    //Check if fisher shop page is open or not
                    while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(
                            detScreen.getRectFisherShopPage(),true
                    ), fishingProcess.getArrayRgbfullEnvAndBlkShop(), fishingProcess.SENSIBILTY_MED) == false)
                    {
                        //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                        if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed || GameStatus.isCharKilled)return;
                        robot.mouseMove(detScreen.getRectFisherOptionsPage().x, detScreen.getRectFisherOptionsPage().y);
                        robot.delay(15);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.delay(20);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        robot.delay(1500);

                    }
                    timerGame.SetStartedSecondTime();
                }


            }
            
            CloseFisherShopPage();
            
            int arrayYabbie[] = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbYabbieIcon());
            int arrayAltin[] = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbAltinIcon());
            int arrayPalamut[] = CheckWantedObjectFromInventory(fishingProcess.getArrayRgbPalamutIcon());
            
            if(arrayYabbie[0] > 0 || arrayAltin[0] > 0 || arrayPalamut[0] > 0)
            {
                GrillFish(arrayKamp, arrayYabbie, arrayAltin,arrayPalamut);
            }
            
        } catch (AWTException ex) 
        {
        
        
    }
    }
    
    public void BuyWormsFromFisher()
    {
        int [] rgbArray ;
      BufferedImage buf;
        try {
            
            while(CheckFisherIsThere() == false)
            {
                 //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                 if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
            }
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            robot.mouseMove(detScreen.getRectFisherOptionsPage().x, detScreen.getRectFisherOptionsPage().y);
            robot.delay(15);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1500);
            
           /* buf = robot.createScreenCapture(detScreen.getRectFisherShopPage());
            rgbArray = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(rgbArray);*/
          
             //Check if fisher shop page is open or not 
            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(
                    detScreen.getRectFisherShopPage(),true
            ), fishingProcess.getArrayRgbfullEnvAndBlkShop(), fishingProcess.SENSIBILTY_MED) == false)
            {
                if(GameStatus.isStopped)return;
                 //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
            robot.mouseMove(detScreen.getRectFisherOptionsPage().x, detScreen.getRectFisherOptionsPage().y);
            robot.delay(15);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1500);
             
           /* buf = robot.createScreenCapture(detScreen.getRectFisherShopPage());
            rgbArray = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(rgbArray);*/
           
            }
            
           /* buf = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
            rgbArray = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(rgbArray);*/
            
          
            int [] resultArray = CombineAndCountWorms() ;
              int neededWormValue = resultArray[0];
            int wormsWeHave =0;
            //int wormsBought =0;
            while(neededWormValue < NEEDED_WORM200_COUNT)
            {
                // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                 if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
                //System.out.println("tespit edilen 200 lu solucan sayisi = " + worm200Count);
                //Combining worm da arttiriliyor en bastan bir daha basliyacagi icin
                //sifirliyoruz
                wormsWeHave = neededWormValue * 4 ;
                while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbFullEnvanterDialog(), 
                        fishingProcess.takeScreenShotReturnRGBarray(
                        detScreen.getRectYereAtmaAlgilama(),true
                ), fishingProcess.SENSIBILTY_MED) == false
                        && wormsWeHave < NEEDED_WORM200_COUNT *4)
            {
                // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                 if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
                 wormsWeHave++;
                robot.mouseMove(detScreen.getPointFisherShopFiftyWorm().x, detScreen.getPointFisherShopFiftyWorm().y);
                robot.delay(20);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(30);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(600);
                /*buf = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
                rgbArray = transferBufferImgtoARGB(buf);
                convertARGBarrayToRGB(rgbArray);*/
            }
                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbFullEnvanterDialog(), 
                        fishingProcess.takeScreenShotReturnRGBarray(
                        detScreen.getRectYereAtmaAlgilama(),true
                ), fishingProcess.SENSIBILTY_MED))
                {
                     robot.keyPress(KeyEvent.VK_ESCAPE);
                     robot.delay(30);
                     robot.keyRelease(KeyEvent.VK_ESCAPE);
                     robot.delay(10);
                 }
           
            
            resultArray = CombineAndCountWorms() ;
            neededWormValue = resultArray[0];
            
           /* buf = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
            rgbArray = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(rgbArray);*/
            }
             CloseFisherShopPage();
            PutWormsToSkillSlotPlace(resultArray);
            //array200WormsInfo[0] = NEEDED_WORM200_COUNT;
            //printArray("array200Worm Info = ", array200WormsInfo);
            //System.out.println("tespit edilen 200 lu solucan sayisi 222  = " + worm200Count);
           
            
          
            
        } catch (AWTException ex) 
        {
        
        
    }
    }
    
    public void CloseFisherShopPage()
    {
        int [] rgbArray ;
        BufferedImage buf;
        
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);

            //Check if fisher shop page is open or not 
            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(
                    detScreen.getRectFisherShopPage(),true
            ), fishingProcess.getArrayRgbfullEnvAndBlkShop(), fishingProcess.SENSIBILTY_MED))
            {
            robot.mouseMove(detScreen.getPointFisherShopCloseButton().x, detScreen.getPointFisherShopCloseButton().y);
            robot.delay(15);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(20);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(1500);

           
            }
        } catch (AWTException ex) {
           
        }
    }
     //brief : This function returns an array that how many amount
     // your wanted object is in your inventory and where they are located
    // as a coordinate system
    //first index of the array gives amount of the wanted object
    //second  ...+2..+2 index y coordinate of the inventory
    //third  ...+2..+2 index x coordinate of the inventory
    public int [] CheckWantedObjectFromInventory(int [] wantedObject)
    {

        BufferedImage buf = null;
        int rgbArray[];
         
        int arraySize =0;
        int arrayIndex =1;
        int arrayPageOne [] = new int[1];
        int arrayPageTwo [] = new int[1];
        arrayPageOne[0] = 0;
        arrayPageTwo[0] = 0;
        int resultArray [] = new int [1];
        resultArray[0] =0;
        
        
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            
            OpenOrCloseInventory(true);
                   
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                robot.mouseMove(detScreen.getPointInventPageOne().x -100, detScreen.getPointInventPageOne().y);
                robot.delay(50);
                //Count How many object does it havae
                 for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                   
           /* buf = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));*/


            // thisFishingProc.getLabelFishingArea().setIcon(new ImageIcon(buf));
             //robot.delay(1000);
          // rgbArray = transferBufferImgtoARGB(buf);
            //convertARGBarrayToRGB(rgbArray);

            rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
            
            if(fishingProcess.CompareTwoArrayAdvanced(wantedObject, rgbArray, fishingProcess.SENSIBILTY_HIGH))
            {
                
                arraySize++;
            }
                }
            }
                 if(arraySize != 0)
                 {
                      if(pageCounter == 1)
                 {
                     System.out.println("page 1 arraysize value = " + arraySize);
                     //2 multiply means x and y coordinate 
                     // +1 means size of the wanted array
                     arrayPageOne = new int[2*arraySize +1];
                     arrayPageOne[0] = arraySize;
                     arraySize =0;
                 }
                 else
                 {
                     System.out.println("page 2 arraysize value = " + arraySize);
                     arrayPageTwo = new int[2*arraySize +1];
                     arrayPageTwo[0] = arraySize;
                 }
                 //Record coordinate of the wanted object and return it
                  for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                   
           /* buf = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));
      
           rgbArray = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(rgbArray);*/
            rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
            
            if(fishingProcess.CompareTwoArrayAdvanced(wantedObject, rgbArray, fishingProcess.SENSIBILTY_HIGH))
            {
                if(pageCounter == 1)
                {
                    arrayPageOne[arrayIndex++] = yPos;
                    arrayPageOne[arrayIndex++] = xPos;
                    
                }
                else
                {
                    arrayPageTwo[arrayIndex++] = yPos;
                    arrayPageTwo[arrayIndex++] = xPos;
                }

            }
                }
            }
                 }
                
                  arrayIndex = 1;
            }
            // -1 ikisinde de boyutunu belli eden index yeri vardi onu cikardik
            resultArray = new int[arrayPageOne.length + arrayPageTwo.length -1];
            //ilk indekslerde aranan nesnenin kac adet oldugu yaziyor onlari topluyoruz
            resultArray[0] = arrayPageOne[0] + arrayPageTwo[0];
            for (int i = 1; i < arrayPageOne.length ; i++) 
            {
                resultArray[i] = arrayPageOne[i];
            }
            
            for (int i = 1; i < arrayPageTwo.length ; i++) 
            {
                resultArray[i + arrayPageOne.length -1] = arrayPageTwo[i];
            }
        } catch (AWTException ex) {
           
        }
          
        
        return resultArray;
    }
    
    public boolean CheckSlotSkill200WormsIsFull()
    {
        BufferedImage buf;
        int [] rgbArray;
        
     
        int [] arrayScannedWorm;
        int [] arrayScannedWhiteNum;
        
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            
            for (int shiftPageCounter = 1; shiftPageCounter < 5; shiftPageCounter++)
            {
                fishingProcess.setShiftNumber(shiftPageCounter);
                fishingProcess.CheckShiftPageNumber();
                for (int skillSlotPlace = 0; skillSlotPlace < 8; skillSlotPlace++) 
                {
                    if(skillSlotPlace < 4)
                    {
                         buf = robot.createScreenCapture(new Rectangle(
                               detScreen.getRectSkillSlotFirstPlace().x +(32 *skillSlotPlace), detScreen.getRectSkillSlotFirstPlace().y 
                              ,detScreen.getRectSkillSlotFirstPlace().width, detScreen.getRectSkillSlotFirstPlace().height));
                               
                               rgbArray = fishingProcess.transferBufferImgtoARGB(buf);
                                fishingProcess.convertARGBarrayToRGB(rgbArray);
                                arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                                arrayScannedWorm = DetectWorms(rgbArray);
                                
                                if(fishingProcess.CompareTwoArrayAdvanced(arrayRgbWormVal, arrayScannedWorm, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) == false)
                                    {
                                        return false;
                                    }
                                }
                                else
                                {
                                    return false;
                                }
                    }else
                    {
                         buf = robot.createScreenCapture(new Rectangle(
                               detScreen.getRectSkillSlotSecondPlace().x +(32 *(skillSlotPlace -4 )) , detScreen.getRectSkillSlotSecondPlace().y 
                              ,detScreen.getRectSkillSlotSecondPlace().width, detScreen.getRectSkillSlotSecondPlace().height));
                               
                               rgbArray = fishingProcess.transferBufferImgtoARGB(buf);
                                fishingProcess.convertARGBarrayToRGB(rgbArray);
                                arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                                arrayScannedWorm = DetectWorms(rgbArray);
                                
                                
                                if(fishingProcess.CompareTwoArrayAdvanced(arrayRgbWormVal, arrayScannedWorm, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) == false)
                                    {
                                        return false;
                                    }
                                }
                                else{
                                    return false;
                                }
                    }
                     
                }
    
            }
                fishingProcess.setShiftNumber(1);
                fishingProcess.CheckShiftPageNumber();
            
        } catch (AWTException ex) {
            
        }
        return true;
    }
    public void GrillFish(int [] kampAtesi, int [] yabbieFish , int [] altinFish, int [] palamutFish)
    {
        int arrayRgb[];
        BufferedImage buf;
        
             int[] colorRed = new int [2];
             int [] colorGreen = new int[2];
             int [] colorBlue = new int[2];
             int totalKampAtesiGreenVal =0;
             int counter =0;
             int maxYValue = 0;
             int maxXValue = 0;
             int minYValue = 0xffffff;
             int minXValue = 0xffffff;
             
        try {
            Robot robot = new RobotMovingForMetin2(fishingProcess);
            
            //Kamp atesini satir 1080 kadar yakmayi sagliyoruz
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                 for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                     //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                     if(GameStatus.GetGameStatusOrUserPrefer() == false)return;

                    yPos = kampAtesi[1];
                    xPos = kampAtesi[2];
         /*   buf = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));
      
           arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/

          /*  arrayRgb = takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);*/
            
            long starttime = System.currentTimeMillis()/1000;
            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbKampIcon(), 
                    fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true), fishingProcess.SENSIBILTY_HIGH) &&
                    System.currentTimeMillis()/1000 - starttime < 3)
            {
                // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                 if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
                robot.keyPress(KeyEvent.VK_S);
                robot.delay(100);
                robot.keyRelease(KeyEvent.VK_S);
                robot.delay(500);
                
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(20);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(30);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                robot.delay(20);
                
              /*  buf = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));
      
           arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/
            }
            
            //pageCounter = 3;
            xPos = INVENTOR_WIDTH_COUNT;
            yPos = INVENTOR_HEIGHT_COUNT;
                    
                }
            }
            }
            colorRed[0] = (fishingProcess.MAP_BALIKCI_GREEN & 0x00ff0000) >> 16;
            colorGreen[0] = (fishingProcess.MAP_BALIKCI_GREEN & 0x0000ff00) >> 8;
            colorBlue[0] = fishingProcess.MAP_BALIKCI_GREEN & 0x000000ff;
              
          /*  buf = robot.createScreenCapture(detScreen.getRectKampAtesiAsagiTarafKoordinat());
            arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/

            arrayRgb = fishingProcess.takeScreenShotReturnRGBarray(detScreen.getRectKampAtesiAsagiTarafKoordinat(),true);
            
            long startSecondTime = System.currentTimeMillis()/1000;
            while(Math.abs(totalKampAtesiGreenVal - kampAtesiGreenCount) > 4 && 
                    System.currentTimeMillis()/1000 - startSecondTime < 10)
            {
                
               for (int height = 0; height < detScreen.getRectKampAtesiAsagiTarafKoordinat().height; height++) 
            {
                for (int xPos = 0; xPos < detScreen.getRectKampAtesiAsagiTarafKoordinat().width; xPos++) 
                {
                     //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                     if(GameStatus.GetGameStatusOrUserPrefer() == false)return;

                         colorRed[1] = (arrayRgb[xPos + counter ] & 0x00ff0000) >> 16;
                         colorGreen[1] = (arrayRgb[xPos + counter ] & 0x0000ff00) >> 8;
                         colorBlue[1] = arrayRgb[xPos + counter ] & 0x000000ff;
                                   
                                if(Math.abs(colorRed[0] - colorRed[1])< 3 && Math.abs(colorGreen[0] - colorGreen[1])< 3
                             && Math.abs(colorBlue[0] - colorBlue[1])< 3)
                       {
                           if(maxYValue < height )
                           {
                               maxYValue = height;
                           }
                           if(minYValue > height)
                           {
                               minYValue = height;
                           }
                           if(maxXValue < xPos)
                           {
                               maxXValue = xPos;
                                       
                           }
                           if(minXValue > xPos)
                           {
                               minXValue = xPos;
                           }
                           
                           totalKampAtesiGreenVal++;
                       }
                }
                counter += detScreen.getRectKampAtesiAsagiTarafKoordinat().width;
            } 
               System.out.println("yesil sayisi = " + totalKampAtesiGreenVal);
               //Eger kamp atesi yakildi ise burada baliklari yakiyoruz.
            if(Math.abs(totalKampAtesiGreenVal - kampAtesiGreenCount) < 4)
            {
                 Point point = new Point((minXValue + maxXValue)/2 + detScreen.getRectKampAtesiAsagiTarafKoordinat().x,
                                     (minYValue +  maxYValue)/2 + detScreen.getRectKampAtesiAsagiTarafKoordinat().y );
                           
                // startSecondTime = System.currentTimeMillis()/1000;
                 
                     //System.out.println("balik surukleme ");
                     DragFishesToKampFire(point, yabbieFish, fishingProcess.getArrayRgbYabbieIcon());
                     DragFishesToKampFire(point, altinFish, fishingProcess.getArrayRgbAltinIcon());
                     DragFishesToKampFire(point, palamutFish, fishingProcess.getArrayRgbPalamutIcon());
         
            }
            while(isGrillOperationSuccesfully == false)
            {
                OpenOrCloseSettingButton(false);
                OpenOrCloseInventory(false);
                if(CheckFisherIsThere())
                {
                    isGrillOperationSuccesfully = true;
                    BuyKampFireFromFisher();
                }

            }
                System.out.println("frillfish fonksiyon sonu");
            totalKampAtesiGreenVal =0;
            counter =0;
            /*buf = robot.createScreenCapture(detScreen.getRectKampAtesiAsagiTarafKoordinat());
            arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/

            arrayRgb = fishingProcess.takeScreenShotReturnRGBarray(detScreen.getRectKampAtesiAsagiTarafKoordinat(),true);
            }
            
             OpenOrCloseInventory(false);
            
            
        } catch (AWTException ex) {
            
        }
    }
    
    public void PutWormsToSkillSlotPlace(int [] array200Worms)
    {
        int [] rgbArray;
        int pageComparerYValue =0;
        int worm200Counter =1;
        int slotCounter =0;
        int shiftPageNumber =1;
      //  int [] arrayScannedWorm;
        int [] arrayScannedWhiteNum;
        BufferedImage buf;
        
        int yIndex =1;
       // int xIndex = 2;
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            
            System.out.println("array200WormsInfo 0 index = " + array200WormsInfo[0]);
            if(array200Worms[0] == NEEDED_WORM200_COUNT)
            {
             int yPos =0;
             int xPos =0;
            
           // System.out.println("arrayFishCoordinate Length = " + arrayFishCoordinates.length);
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                if(pageCounter == 2)
                {
                    yIndex = worm200Counter;
                    //xIndex = worm200Counter +1;
                }              
                for ( ; yIndex < array200Worms.length ; yIndex+=2) 
                {
                     //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                     if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
                    //System.out.println("YIndex value = " + yIndex);
                    //System.out.println("pageCounter Value = " + pageCounter);
                    //System.out.println("slotCounter VAlue = " + slotCounter);
                    yPos = array200Worms[yIndex];
                    xPos = array200Worms[yIndex +1];
                    
                    robot.mouseMove(detScreen.getRectFirstSlotPlace().x -100, 
                            detScreen.getRectFirstSlotPlace().y +(32 *yPos));
                    robot.delay(100);
                    
     

            rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);

            //arrayScannedWorm = DetectWorms(rgbArray);
            arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
            
            fishingProcess.setShiftNumber(shiftPageNumber);
            fishingProcess.CheckShiftPageNumber();
            
            if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) &&
                    pageComparerYValue <= yPos)
            {
                pageComparerYValue = yPos;
                worm200Counter+=2;
                
                if(slotCounter >= 8)
                {
                    shiftPageNumber++;
                    fishingProcess.setShiftNumber(shiftPageNumber);
                    fishingProcess.CheckShiftPageNumber();
                    slotCounter =0;
                }
                 //Ilk dort slot bos ise birinci kisima konulacak
                            if(slotCounter < 4)
                            {
                          
                                rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                                        detScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter), detScreen.getRectSkillSlotFirstPlace().y
                                        ,detScreen.getRectSkillSlotFirstPlace().width, detScreen.getRectSkillSlotFirstPlace().height), true);
                                arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                               // arrayScannedWorm = DetectWorms(rgbArray);
                                
                                //Tespit edilen yer bos ise 200 lu solucani koy
                                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbEmptySlotPlace(), rgbArray, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    //Solucanin yerine git
                                    robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                    detScreen.getRectFirstSlotPlace().y + (yPos *32));
                                    robot.delay(20);
                                    robot.keyPress(KeyEvent.VK_CONTROL);
                                    robot.delay(40);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(10);
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
                                    robot.delay(500);
                                    
                                 /*   buf = robot.createScreenCapture(new Rectangle(
                               detScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter), detScreen.getRectSkillSlotFirstPlace().y 
                              ,detScreen.getRectSkillSlotFirstPlace().width, detScreen.getRectSkillSlotFirstPlace().height));
                               
                               rgbArray = transferBufferImgtoARGB(buf);
                                convertARGBarrayToRGB(rgbArray);*/

                                rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                                        detScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter), detScreen.getRectSkillSlotFirstPlace().y
                                        ,detScreen.getRectSkillSlotFirstPlace().width, detScreen.getRectSkillSlotFirstPlace().height),true);
                                //arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                                
                                //Eger solucan skill slota dolmadıysa tekrar ayni islemin yapilmasi icin degerleri
                                //su an ki degerine getiriyoruz.
                                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbEmptySlotPlace(), 
                                        rgbArray, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    yIndex-=2;
                                    slotCounter--;
                                }
                                slotCounter++;
                                }
                                //200 lu degil ise bosaltip 200 lu olani koy
                                else if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) == false)
                                {
                                    robot.mouseMove(detScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter) +16,
                                    detScreen.getRectSkillSlotFirstPlace().y );
                                    robot.delay(30);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseMove(detScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter) +16,
                                    detScreen.getRectSkillSlotFirstPlace().y -100);
                                    robot.delay(20);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(500);
                                    
                                    
                                    //Solucanin yerine git
                                    robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                    detScreen.getRectFirstSlotPlace().y + (yPos *32));
                                    robot.delay(20);
                                    robot.keyPress(KeyEvent.VK_CONTROL);
                                    robot.delay(40);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(10);
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
                                    robot.delay(500);
                                     
                                    slotCounter++;
                                }
                               else if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    slotCounter++;
                                    //yIndex +=2;
                                }
                            }
                            //slot 4 den 8 e kadar olan kisim
                            else
                            {
                               /* buf = robot.createScreenCapture(new Rectangle(
                               detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)), detScreen.getRectSkillSlotSecondPlace().y 
                              ,detScreen.getRectSkillSlotSecondPlace().width, detScreen.getRectSkillSlotSecondPlace().height)); 
                            
                                rgbArray = transferBufferImgtoARGB(buf);
                                convertARGBarrayToRGB(rgbArray);*/

                                rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                                        detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)), detScreen.getRectSkillSlotSecondPlace().y
                                        ,detScreen.getRectSkillSlotSecondPlace().width, detScreen.getRectSkillSlotSecondPlace().height),true);
                                //arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                                // arrayScannedWorm = DetectWorms(rgbArray);
                                
                                //Tespit edilen yer bos ise 200 lu solucani koy
                                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbEmptySlotPlace(), rgbArray, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    //Solucanin yerine git
                                    robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                    detScreen.getRectFirstSlotPlace().y + (yPos *32));
                                    robot.delay(20);
                                    robot.keyPress(KeyEvent.VK_CONTROL);
                                    robot.delay(40);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(10);
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
                                    robot.delay(500);
                                    
                                  /*  buf = robot.createScreenCapture(new Rectangle(
                               detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)), detScreen.getRectSkillSlotSecondPlace().y 
                              ,detScreen.getRectSkillSlotSecondPlace().width, detScreen.getRectSkillSlotSecondPlace().height));
                               
                               rgbArray = transferBufferImgtoARGB(buf);
                                convertARGBarrayToRGB(rgbArray);*/

                                rgbArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                                        detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)), detScreen.getRectSkillSlotSecondPlace().y
                                        ,detScreen.getRectSkillSlotSecondPlace().width, detScreen.getRectSkillSlotSecondPlace().height),true);
                                //arrayScannedWhiteNum = DetectWhiteNumIndex(rgbArray);
                                //Eger solucan skill slota dolmadıysa tekrar ayni islemin yapilmasi icin degerleri
                                //su an ki degerine getiriyoruz.
                                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbEmptySlotPlace(), rgbArray, fishingProcess.SENSIBILTY_HIGH))
                                {
                                    yIndex-=2;
                                    slotCounter--;
                                }
                                slotCounter++;
                                }
                               //Eger 200 lu yok ise 200 lu olani koy
                               else if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH) == false)
                                {
                                    robot.mouseMove(detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)) +16,
                                    detScreen.getRectSkillSlotSecondPlace().y );
                                    robot.delay(30);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseMove(detScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4)) +16,
                                    detScreen.getRectSkillSlotSecondPlace().y -100);
                                    robot.delay(20);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(500);
                                    
                                    
                                    //Solucanin yerine git
                                    robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                                    detScreen.getRectFirstSlotPlace().y + (yPos *32));
                                    robot.delay(20);
                                    robot.keyPress(KeyEvent.VK_CONTROL);
                                    robot.delay(40);
                                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(30);
                                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                    robot.delay(10);
                                    robot.keyRelease(KeyEvent.VK_CONTROL);
                                    robot.delay(500);
                                    slotCounter++;
                                }
                               else if(fishingProcess.CompareTwoArrayAdvanced(array200WhiteIndex, arrayScannedWhiteNum, fishingProcess.SENSIBILTY_HIGH))
                               {
                                   slotCounter++;
                                    //yIndex +=2;
                               }
                            }
            }else
            {
                yIndex = array200Worms.length;
                pageComparerYValue =0;
            }
                
           }
            }
            }
            
           // thisFishingProc.setShiftNumber(1);
           // thisFishingProc.CheckShiftPageNumber(); 
            
        } catch (AWTException ex) {
           
        }
    }
    public void DragFishesToKampFire(Point pointKampAtesi, int [] arrayFishCoordinates, int [] arrayRgbFishIcon)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            int [] arrayRgb;
            BufferedImage buf;
            int yPos =0;
            int xPos =0;
            
            if(arrayFishCoordinates[0] > 0)
            {
                // System.out.println("arrayFishCoordinate Length = " + arrayFishCoordinates.length);
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                 for (int yIndex = 1; yIndex < arrayFishCoordinates.length; yIndex+=2) 
            {
                
                    // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                     if(GameStatus.GetGameStatusOrUserPrefer() == false)return;

                    yPos = arrayFishCoordinates[yIndex];
                    xPos = arrayFishCoordinates[yIndex +1];
                    
                  /*  buf = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));
      
           arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/

            arrayRgb = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
            
            /*robot.mouseMove(detScreen.getRectFirstSlotPlace().x +(32 *xPos), 
                    detScreen.getRectFirstSlotPlace().y +(32 *yPos));
            robot.delay(600);
                    System.out.println("pageCount = " + pageCounter);*/
            
            if(fishingProcess.CompareTwoArrayAdvanced(arrayRgbFishIcon, arrayRgb, fishingProcess.SENSIBILTY_HIGH))
            {
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(20);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseMove(pointKampAtesi.x, pointKampAtesi.y);
                robot.delay(300);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(300);
                
                //Burada nesnenin yere suruklenip suruklenmedigini tespit et
                /*buf = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
                arrayRgb = transferBufferImgtoARGB(buf);
                convertARGBarrayToRGB(arrayRgb);*/
                if(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbYereAtmaEkrani(),
                        fishingProcess.takeScreenShotReturnRGBarray(
                        detScreen.getRectYereAtmaAlgilama(),true
                ), fishingProcess.SENSIBILTY_MED))
                {
                    System.out.println("balik yere atiliyor");
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.delay(40);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    robot.delay(700);
                    isGrillOperationSuccesfully = false;
                    return;
                }
                
            }
            
                }
            
            }
            }
           
        } catch (AWTException ex) {
            
        }
        
                
    }
    
    public void ThrowAWorm()
    {
         BufferedImage imageScanned;
         int [] arrayRgb;
         int [] arrayScannedArray;
         int [] arrayScannedWorm;
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
           

                    OpenOrCloseSettingButton(false);
                   
                    OpenOrCloseInventory(true);
            for (int pageCounter = 1; pageCounter < 3; pageCounter++) 
            {
                ClickPage(pageCounter);
                 for (int yPos = 0; yPos < INVENTOR_HEIGHT_COUNT; yPos++) 
            {
                for (int xPos = 0; xPos < INVENTOR_WIDTH_COUNT; xPos++) 
                {
                   // if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                    if(GameStatus.GetGameStatusOrUserPrefer() == false)return;
          /*  imageScanned = robot.createScreenCapture(new Rectangle(
            detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
            ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height));
      
           arrayScannedArray = transferBufferImgtoARGB(imageScanned);
            convertARGBarrayToRGB(arrayScannedArray);*/

            arrayScannedArray = fishingProcess.takeScreenShotReturnRGBarray(new Rectangle(
                    detScreen.getRectFirstSlotPlace().x +(32 *xPos), detScreen.getRectFirstSlotPlace().y +(32 *yPos)
                    ,detScreen.getRectFirstSlotPlace().width, detScreen.getRectFirstSlotPlace().height),true);
            arrayScannedWorm = DetectWorms(arrayScannedArray);
            
 
                    //Check if there is a worm at that place 
           
           if(fishingProcess.CompareTwoArrayAdvanced(arrayScannedWorm, arrayRgbWormVal, fishingProcess.SENSIBILTY_HIGH))
            {
            
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(20);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x - 150, detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(300);
                
                //Burada nesnenin yere suruklenip suruklenmedigini tespit et
               /* imageScanned = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
                arrayRgb = transferBufferImgtoARGB(imageScanned);
                convertARGBarrayToRGB(arrayRgb);*/
                
                while(!fishingProcess.CompareTwoArrayAdvanced(fishingProcess.getArrayRgbYereAtmaEkrani(), 
                        fishingProcess.takeScreenShotReturnRGBarray(
                        detScreen.getRectYereAtmaAlgilama(), true
                ), fishingProcess.SENSIBILTY_MED))
                {
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x + (xPos * 32) +16,
                detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(20);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseMove(detScreen.getRectFirstSlotPlace().x - 300, detScreen.getRectFirstSlotPlace().y + (yPos *32));
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                
                //Burada nesnenin yere suruklenip suruklenmedigini tespit et
                /*imageScanned = robot.createScreenCapture(detScreen.getRectYereAtmaAlgilama());
                arrayRgb = transferBufferImgtoARGB(imageScanned);
                convertARGBarrayToRGB(arrayRgb);*/
                robot.delay(300);
                }
                
                robot.mouseMove(detScreen.getPointYesButton().x, detScreen.getPointYesButton().y);
                robot.delay(50);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                return;
            }
                }
            }
            }
            
            
        
            
        } catch (AWTException ex) {
            Logger.getLogger(PrepareFishing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void GoToFishPlace()
    {
        BufferedImage buf;
        int [] arrayRgb;
        short clickingSide =1;
        short clickedValue =0;
        
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            robot.delay(1000);
            
           OpenOrCloseInventory(false);

            while(clickedValue <15)
            {
                 //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                 if(GameStatus.GetGameStatusOrUserPrefer() == false)return;

               /* buf = robot.createScreenCapture(detScreen.getRectReverseWoodDetectionArea());
            arrayRgb = transferBufferImgtoARGB(buf);
            convertARGBarrayToRGB(arrayRgb);*/

            buf = robot.createScreenCapture(detScreen.scannableRectMt2Icon(fishingProcess,
                    detScreen.getRectReverseWoodDetectionArea()));
            arrayRgb = fishingProcess.transferBufferImgtoARGB(buf);
            fishingProcess.convertARGBarrayToRGB(arrayRgb);

         int [] arrayWoodColors = new int [2];
         int [] arrayColorRed = new int[arrayWoodColors.length +1];
         int [] arrayColorGreen =  new int[arrayWoodColors.length +1];
         int [] arrayColorBlue =  new int[arrayWoodColors.length +1];
        
         int comparableArrayIndex =arrayWoodColors.length;
         final int  MINIMUM_INDEX =0;
         final int MAX_INDEX =1;
         
         int minDetectedIndex = 0xfffffff;
         int maxDetectedIndex = 0;
         int tempIndex =0;
         int mousePosition = 0;
        
         
         arrayWoodColors[MINIMUM_INDEX] = fishingProcess.MIN_WOOD_VALUE;
         arrayWoodColors[MAX_INDEX] = fishingProcess.MAX_WOOD_VALUE;
       
         //This loop gets the wood colors to needed arrays
         for (int k = 0; k < arrayWoodColors.length; k++) 
         {
          arrayColorRed[k] = (arrayWoodColors[k] & 0x00ff0000) >> 16;
          arrayColorGreen[k] = (arrayWoodColors[k] & 0x0000ff00) >> 8;
          arrayColorBlue[k] = arrayWoodColors[k] & 0x000000ff;
         }
         
              for (int i = 0; i < arrayRgb.length; i++) 
         {

              arrayColorRed[comparableArrayIndex] = (arrayRgb[i] & 0x00ff0000) >> 16;
              arrayColorGreen[comparableArrayIndex] = (arrayRgb[i] & 0x0000ff00) >> 8;
              arrayColorBlue[comparableArrayIndex] = arrayRgb[i] & 0x000000ff;
              
               if(arrayColorRed[comparableArrayIndex]> arrayColorGreen[comparableArrayIndex]
                       && arrayColorGreen[comparableArrayIndex] > arrayColorBlue[comparableArrayIndex])
              {
                  
              
              if(arrayColorRed[comparableArrayIndex] > arrayColorRed[MINIMUM_INDEX] &&
                    arrayColorGreen[comparableArrayIndex] > arrayColorGreen[MINIMUM_INDEX]
                     && arrayColorBlue[comparableArrayIndex] > arrayColorBlue[MINIMUM_INDEX])
             {
              if(arrayColorRed[comparableArrayIndex] < arrayColorRed[MAX_INDEX] &&
                    arrayColorGreen[comparableArrayIndex] < arrayColorGreen[MAX_INDEX]
                     && arrayColorBlue[comparableArrayIndex] < arrayColorBlue[MAX_INDEX])
              {
              
             
                 
                  tempIndex = i - ((i / buf.getWidth()) * buf.getWidth());
                      if(minDetectedIndex > tempIndex)
                      {
                          minDetectedIndex =tempIndex;
                          //System.out.println("minDetectVal = " + minDetectedIndex + "index = " + i);
                      }
                      if(maxDetectedIndex < tempIndex)
                      {
                          maxDetectedIndex =tempIndex;
                      //    System.out.println("maxDetecdVal = " + maxDetectedIndex + "index = " + i);
                      }

              }
             }
            }
              
         }
             
       
           if(maxDetectedIndex != 0xfffffff )
           {
               double chancingValue = 200.0;
               if(clickingSide == 1)
               {
                   mousePosition = Math.abs((maxDetectedIndex + minDetectedIndex))/2 - (int)(Math.random() *chancingValue);
                  // System.out.println("mousePosition = " + mousePosition);
                   clickingSide =2;
               }
               else
               {
                   mousePosition = Math.abs((maxDetectedIndex + minDetectedIndex))/2 + (int)(Math.random() *chancingValue);
                  // System.out.println("mousePosition = " + mousePosition);
                   clickingSide =1;
               }
                // mousePosition = Math.abs((maxDetectedIndex + minDetectedIndex))/2;
                 
               robot.mouseMove(detScreen.getRectReverseWoodDetectionArea().x + mousePosition, 
                       detScreen.getRectReverseWoodDetectionArea().y);
               robot.delay(20);
               robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
               robot.delay(20);
               robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
               robot.delay(500);
              // CheckFisherIsThere();
           }
            clickedValue++;
            }
             
            
        } catch (AWTException ex) {
            
        }
    }
    
    public void OpenOrCloseInventory(boolean state)
    {
        TimerGame timerGame = new TimerGame();
        timerGame.SetStartedSecondTime();
        RobotMovingForMetin2 robot;
       // long startMilliSecond =0;
        try {
             robot = new RobotMovingForMetin2(fishingProcess);

            while(fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(detScreen.getRectInventory()
                    ,true), fishingProcess.getInventColorArray(), fishingProcess.SENSIBILTY_MED) != state)
            {

                if(!GameStatus.isSettingButtonSeemed)return ;
                if(timerGame.CheckDelayTimeInSecond(2))
                {
                    robot.mouseMove(detScreen.getRectInventory().x - 100, detScreen.getRectInventory().y);
                    robot.delay(15);
                    robot.keyPress(KeyEvent.VK_I);
                    robot.delay(30);
                    robot.keyRelease(KeyEvent.VK_I);
                    robot.delay(300);

                }else
                {
                    if(!Thread.currentThread().getName().equals("Thread 22"))
                    {
                        fishingProcess.SettingButtonsPreferring(fishingProcess.CHAR_BUTTON_SETTING);
                        timerGame.SetStartedSecondTime();
                        while (GameStatus.isCharScreenActive)
                        {
                            if(!timerGame.CheckDelayTimeInSecond(15))return;
                        }
                    }
                    else
                    {
                        System.out.println("This function is called in wrong Thread named =  " + Thread.currentThread().getName() + " !!!");
                        return;
                    }


                }


            }
        }
        catch (AWTException e)
        {
            System.out.println(e);
        }



                  

                   
    }

    public void OpenOrCloseSettingButton(boolean state)
    {

        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);


            //Check Character Button and close or open it as depends on "state" param
            while (fishingProcess.CompareTwoArrayAdvanced(fishingProcess.takeScreenShotReturnRGBarray(detScreen.getRectCharButton(),true),
                    fishingProcess.getArrayRgbCharButton(), fishingProcess.SENSIBILTY_MED) != state) {
                //if(thisFishingProc.getGameStatusOrUserPreffer() == false)return;
                if (GameStatus.isStopped) return;

                if(GameStatus.isAnotherPlayer && GameStatus.isCharKilled == false)
                {
                    robot.mouseMove(detScreen.getRectCharButton().x - 150,
                            detScreen.getRectCharButton().y);
                    robot.delay(10);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.delay(50);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                    robot.delay(50);
                }else {
                    robot.delay(20);
                    robot.mouseMove(detScreen.getRectSettingButton().x, detScreen.getRectSettingButton().y);
                    robot.delay(40);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(40);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(200);
                }

            }
        }catch(AWTException e)
        {
            System.out.println(e);
        }
    }


    public int[] getArrayRgbWormVal() {
        return arrayRgbWormVal;
    }

    public int[] getArray200WhiteIndex() {
        return array200WhiteIndex;
    }

    public void setIsInventorFull(boolean isInventorFull) {
        this.isInventorFull = isInventorFull;
    }
    
    
}
