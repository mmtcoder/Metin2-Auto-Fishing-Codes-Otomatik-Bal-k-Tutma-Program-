package com.mycompany.autofishing;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FishingProcess extends ImageProcess{

    private GameObjectCoordinates thisDetScreen;
    private PrepareFishing prepareFishing;
    private TestDrawingFrame tb;
    private Chatting chattingClass;
    //private GameStatus GameStatus;

    private JButton buttonStartStop;
    private JLabel labelFishingText;
    private JLabel labelFishingArea;
    private Dimension size;
    
    private BufferedImage imageDetermined;
    private volatile BufferedImage  imageSecDeter;
    private BufferedImage imageEntryScrn;
    private BufferedImage imageKillScrn;
    private BufferedImage imageCharScreen;
    private BufferedImage imageFishTitle;
    private BufferedImage imageSaleTitle;
    private BufferedImage imageIsCharOnline;
    private BufferedImage imageIsAnotPlayer;
    private BufferedImage imageIsMetin2IconSeemed;
   
 
    private JFrame testFrame;
    private String[] stringTextFields;

    private int[] fishingAreaArray;
    private int[] arraySecondThread;
    private int[] arrayEntyScreen;
    private int[] arrayCharScreen;
    private int[] arrayDeath;
    private int[] arraySaleTitle;
    private int[] arrayIsCharOnline;
    private int[] arrayIsAnotPlayer;
    private int[] arrayIsMetin2IconSeemed;
    int pinkFishValue;

    //TODO: shifNumber 1 yap
    int shiftNumber =1;
    int channelValue = 2;
    int t3answerGenerator = 2;
    boolean ist3RingWearOn = false;

    private int[] wormCounts ;
    private int[] pinkFishIndex;

    int [] arrayStoredFishValue = new int[4];
    int storeAttempt = 0;
 
    private volatile boolean [] stateOfTheFishes = new boolean [5];
    private boolean isWormSlotHalf = false;
    private boolean isFirstTimePlyrDetected =false;
    private boolean isFirstTimeCharProblem = false;

    private volatile boolean isPhotoIsTaken = false;

    public final int SENSIBILTY_LOW = 3;
    public final int SENSIBILTY_MED = 2;
    public final int SENSIBILTY_HIGH = 1;

    public final int CHAR_BUTTON_SETTING = 10;
    public final int EXIT_BUTTON_SETTING = 11;


    
    public FishingProcess(JLabel label, JLabel labelFishArea, JButton button)
    {
        super();
        thisDetScreen = new GameObjectCoordinates();
        chattingClass = new Chatting(this);
        buttonStartStop = button;
        labelFishingArea = labelFishArea;
        labelFishingText = label;
        //GameStatus = new GameStatus();
        
        imageDetermined = null;
        imageSecDeter =null;
        imageFishTitle = null;
        imageEntryScrn = null;
        imageIsCharOnline =null;
       
        size = Toolkit.getDefaultToolkit().getScreenSize();
        
        prepareFishing = new PrepareFishing(this);
        pinkFishIndex = new int[110];
        testFrame = new JFrame();
        
      /* imageSecDeter = readImage(getPathWorm200(), imageSecDeter);
       imageSecDeter =imageSecDeter.getSubimage(thisDetScreen.getRectFirstSlotPlace().x, thisDetScreen.getRectFirstSlotPlace().y,
               thisDetScreen.getRectFirstSlotPlace().width, thisDetScreen.getRectFirstSlotPlace().height);
       labelFishingArea.setIcon(new ImageIcon(imageSecDeter));*/

     // imageSecDeter = readImage("palamutIcon.png");
      //imageSecDeter = getBufferSubImage(imageSecDeter, thisDetScreen.getRectKampIcon());
     // labelFishingArea.setIcon(new ImageIcon(imageSecDeter));
     // arrayIsCharOnline = transferBufferImgtoARGB(imageSecDeter);
      //  convertARGBarrayToRGB(arrayIsCharOnline);
        //printArrayAsRgb("character button values = ", arrayIsCharOnline);
       // printArrayAsRgb("balikci mini hartia yazisi ", arrayIsCharOnline);
    }
    /*public FishingProcess()
    {
        //super();

        imageDetermined = null;
        imageSecDeter =null;
        imageFishTitle = null;
        imageEntryScrn = null;
        imageIsCharOnline =null;

        size = Toolkit.getDefaultToolkit().getScreenSize();

        prepareFishing = new PrepareFishing(this);
        thisDetScreen = new GameObjectCoordinates();
        pinkFishIndex = new int[110];
        testFrame = new JFrame();



    }*/


    public void Start() 
    {

           Thread t1 = new Thread(() -> {

                stateOfTheFishes[0] = AutoFishingJFrame.checkBoxArray[0];//Yabbie Checkbox
                stateOfTheFishes[1] = AutoFishingJFrame.checkBoxArray[1];//Gold Sudak Checkbox
                stateOfTheFishes[2] = AutoFishingJFrame.checkBoxArray[2];//Palamut Checkbox
                stateOfTheFishes[3] = AutoFishingJFrame.checkBoxArray[3];//Kurbaga Checkbox
                stateOfTheFishes[4] = AutoFishingJFrame.checkBoxArray[4];//All of Them Checkbox
                
               /* for (int i = 0; i <AutoFishingJFrame.checkBoxArray.length ; i++) 
                    {
                        
                        System.out.println("AutoFishingJFrame.checkBoxArray = " +AutoFishingJFrame.checkBoxArray[i]
                        + " and index number = " + i);
                        
                        System.out.println("stateOfTheFishes value = " +stateOfTheFishes[i]
                        + " and index number = " + i);
                    }*/
               //if(isStopped ==true)
               if(GameStatus.isStopped ==true)
           // while(isStopped ==  false)
           {
               System.out.println(Thread.currentThread().getName() + " is started");
            labelFishingText.setText("Fish Bot is starting 3 seconds");
            Sleep(1000);
            labelFishingText.setText("Fish Bot is starting 2 seconds");
            Sleep(1000);
            labelFishingText.setText("Fish Bot is starting 1 seconds");
            Sleep(1000);
            labelFishingText.setText("Fish Bot is Running");



                

            while (!GameStatus.isSettingButtonSeemed && !GameStatus.isStopped){}//Wait until Game screen is active
                CheckShiftPageNumber();

             FishClicking();


               //chattingClass.WearOnorOffWantedItem(getArrayRgbLicenceInvent(),
              //         thisDetScreen.getRectFirstSlotPlace(),true);

               System.out.println(Thread.currentThread().getName() + " is died");
            GameStatus.isThreadOneActive = false;
               }
           });
           
           Thread t2 = new Thread(() -> {

               System.out.println(Thread.currentThread().getName() + " is started");
               Sleep(2500);
               GameStatus.isStopped = false;
               CheckGameStatus();
               System.out.println(Thread.currentThread().getName() + " is died");
               GameStatus.isThreadTwoActive = false;
           });

           Thread t3 = new Thread(() ->
           {
               System.out.println(Thread.currentThread().getName() + "is started");
               Sleep(3100);
               TimerGame timerGameT3thread = new TimerGame();
               TimerGame timerElseState = new TimerGame();

               boolean isTellAnotherPlayer =false;
               boolean firstEnemyPhoto = false;

               while(chattingClass.getArrayRGBcharName() == null)
               {
                   if(GameStatus.isStopped)return;
                   if(GameStatus.GetGameStatusOrUserPrefer())
                   {
                       prepareFishing.OpenOrCloseSettingButton(false);
                       chattingClass.detectCharacterName();
                   }

               }
               System.out.println("getArrayRGBcharName is not null");
               timerGameT3thread.SetStartedSecondTime();
               while (!GameStatus.isStopped)
              {
              while(!GameStatus.isStopped && GameStatus.isSettingButtonSeemed && !GameStatus.isCharScreenActive)
              {
                  if(isPhotoIsTaken)
                  {
                     // int [] rgbArray = transferBufferImgtoARGB(getBufferSubImage(imageSecDeter,
                       //       thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectMiniMapArea())));

                      int [] rgbArray = transferBufferImgtoARGB(getBufferSubImage(imageSecDeter,
                              new Rectangle(thisDetScreen.getRectMiniMapArea().x + GameObjectCoordinates.metin2IconCoor.x,
                                      thisDetScreen.getRectMiniMapArea().y + GameObjectCoordinates.metin2IconCoor.y
                              ,thisDetScreen.getRectMiniMapArea().width
                              ,thisDetScreen.getRectMiniMapArea().height)));
                      convertARGBarrayToRGB(rgbArray);
                      if(DetectMiniMapColors(rgbArray))
                      {

                          timerElseState.SetStartedSecondTime();
                          // System.out.println("Baska oyuncu tespit edildi");
                          //isAnotherPlayer = true;
                          GameStatus.isAnotherPlayer = true;

                          prepareFishing.OpenOrCloseSettingButton(false);

                          if(!firstEnemyPhoto)
                          {
                              imageFileWriteToDesktop("FirstDetection");
                              firstEnemyPhoto = true;
                          }
                          if(!ist3RingWearOn)
                          {
                              if(GameStatus.isEnemyDetected)
                              {
                                  GameStatus.isCharKilled = true;
                                  TimerGame.Sleep(1500);
                                  chattingClass.WearOnorOffWantedItem(getArrayRgbLicenceImage(),thisDetScreen.getRectLicenseImgCoord(),true);
                                    ist3RingWearOn = true;
                                  GameStatus.isCharKilled = false;
                              }
                          }


                          if(!timerGameT3thread.CheckDelayTimeInSecond(2));
                          {
                              if(!isTellAnotherPlayer)
                              {
                                if(t3answerGenerator == 0)
                                {
                                    chattingClass.answerTheDialog(null,"dd","kolay gelsin");
                                    t3answerGenerator++;
                                }
                                else if(t3answerGenerator == 1)
                                {
                                    chattingClass.answerTheDialog(null,"dd","iyi oyunlar");
                                    t3answerGenerator++;
                                }
                                else if(t3answerGenerator == 2)
                                {
                                    chattingClass.answerTheDialog(null,"dd","kg reis");
                                    t3answerGenerator = 0;
                                }

                                  imageFileWriteToDesktop("DetectedPlayer10Sec");
                                  isTellAnotherPlayer = true;
                              }

                          }
                          if(!timerGameT3thread.CheckDelayTimeInSecond(120))
                          {

                              chattingClass.answerTheDialog(null,"dd","cikiyorum iyi oyunlar");
                              imageFileWriteToDesktop("DetectedPlayerExit");
                              GameStatus.isCharKilled = true;
                              GameStatus.isPausedTheGame = true;
                              SettingButtonsPreferring(EXIT_BUTTON_SETTING);
                              TimerGame.Sleep(150000);// equals 2.5 minutes
                              GameStatus.isPausedTheGame = false;
                          }
                          //chattingClass.detectCharacterName();
                          chattingClass.detectChatTypings();

                      }
                      else
                      {
                          //If Char entered char menu for passing worms controlling,
                          //we must determine a time to pass from char menu to game screen like 25 second
                          //If we didn't do like that, when char detect a player
                          //while It had entried from char menu to game screen, Char will be chatting
                          //with player every time after It was passed to game screen from char menu
                          if(!timerElseState.CheckDelayTimeInSecond(25))
                          {
                              if( !CompareTwoArrayAdvanced(takeScreenShotReturnRGBarray(
                                      thisDetScreen.getRectInventory(),true)
                                              , getInventColorArray(),
                                                 SENSIBILTY_MED))
                              {
                                  //System.out.println("kimse tespit edilemedi");
                                  if(ist3RingWearOn)
                                  {
                                      chattingClass.WearOnorOffWantedItem(getArrayRgbLicenceImage(),
                                              thisDetScreen.getRectLicenseImgCoord(),
                                              false);
                                      ist3RingWearOn = false;

                                  }
                                  timerGameT3thread.SetStartedSecondTime();
                                  isTellAnotherPlayer = false;

                                  firstEnemyPhoto = false;
                              }

                          }
                          GameStatus.isAnotherPlayer = false;
                      }
                  }
              }
               /*AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(getBufImageScannedArea(
                       new Rectangle(chattingClass.getRectFullChatArea().x,chattingClass.getRectFullChatArea().y - (0 * GameObjectCoordinates.DISTANCE_BETW_CHAT_HEIGHT)
                               ,chattingClass.getRectFullChatArea().width,chattingClass.getRectFullChatArea().height)
               )));*/



           }
               System.out.println(Thread.currentThread().getName() + "is died");
               GameStatus.isThreadThreeActive = false;
           });
           
          
           t1.setName("Thread 11");
           t2.setName("Thread 22");
           t3.setName("Thread 33");
           if(!GameStatus.isThreadOneActive && !GameStatus.isThreadTwoActive && !GameStatus.isThreadThreeActive)
           {
           t1.start();
           t2.start();
           t3.start();

           GameStatus.isThreadOneActive = true;
           GameStatus.isThreadTwoActive = true;
           GameStatus.isThreadThreeActive = true;
           }

        System.out.println("Start active all threads = "+Thread.activeCount());
    }

    public void Stop() {


        GameStatus.isStopped =true;
        GameStatus.isActiveFishBoard =false;
        GameStatus.isSettingButtonSeemed = false;
        GameStatus.isCharKilled =false;
        GameStatus.isSaleTitleActive= false;
        GameStatus.isMetin2IconSeemed = true;
        GameStatus.isChatting = false;
        GameStatus.isPausedTheGame = false;
        GameStatus.isCharScreenActive = false;
        GameStatus.isEnemyDetected = false;


        new Thread(() ->
        {
            if(Thread.currentThread().getName() == "Thread 11")
            {
                GameStatus.isThreadOneActive = false;
            }
            else if(Thread.currentThread().getName() == "Thread 22")
            {
                GameStatus.isThreadTwoActive = false;
            }
            else if(Thread.currentThread().getName() == "Thread 33")
            {
                GameStatus.isThreadThreeActive = false;
            }
            while(GameStatus.isThreadOneActive || GameStatus.isThreadTwoActive || GameStatus.isThreadThreeActive)
            {
                buttonStartStop.setEnabled(false);

            }
            buttonStartStop.setEnabled(true);
            System.out.println("Bot is STOPPED !");
        }).start();


        
    }


   
    private void FishClicking()
    {

        //GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // GraphicsDevice screen = environment.getDefaultScreenDevice();
     
          TimerGame timer = new TimerGame();
         // long startMilliSecond;
          while(GameStatus.isStopped == false)
          {

            //  System.out.println(GameStatus.isCharScreenActive);
          //while( isStopped != true && isAnotherPlayer == false && isCharOnline == true && isCharKilled == false && isSaleTitleActive == false)
          while( GameStatus.GetGameStatusOrUserPrefer())
          {

             
            try {
              //  System.out.println("FishClicking()");
                RobotMovingForMetin2 robotFishing = new RobotMovingForMetin2(this);
                typeWormForFishing(robotFishing);
                timer.SetStartedSecondTime();
                
               
                 //long fishBoardTimerStart =  System.currentTimeMillis() / 1000;
                 //long fishBoardCurrentTime =0;
                //System.out.println("isActiveFishBoard " + GameStatus.isActiveFishBoard);
                while(GameStatus.isActiveFishBoard == false && timer.CheckDelayTimeInSecond(5))
                {if(GameStatus.isStopped)return;}
                robotFishing.delay(30);

                imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(this,
                        thisDetScreen.getRectChatArea()));

                fishingAreaArray = transferBufferImgtoARGB(imageDetermined);
                convertARGBarrayToRGB(fishingAreaArray);
                labelFishingArea.setIcon(new ImageIcon(imageDetermined));
                pinkFishValue= calculatePinkValue(fishingAreaArray,pinkFishIndex);
                if(GameStatus.isActiveFishBoard ==true)
                {


                    //imageProcess.printArray("Detected string values = ", fishingAreaArray);
                    // System.out.println("pink value = "+  pinkFishValue);

                    if(IsWantedFishes(pinkFishIndex))
                    {

                        //long starttime = System.currentTimeMillis();
                        // long starttimeSecond = starttime / 1000;

                        // long currentTimeSecond = 0;
                        short clikcCounter =0;
                        //while (isStopped != true && isActiveFishBoard == true)
                        while (GameStatus.isStopped == false && GameStatus.isActiveFishBoard == true)
                        {
                            imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(
                                    this
                                    ,thisDetScreen.getRectFishClickArea()));


                            //System.out.println("start time is = " + starttimeSecond);
                            if (imageDetermined != null) {


                                // labelFishingArea.setIcon(new ImageIcon(imageDetermined));

                                fishingAreaArray = transferBufferImgtoARGB(imageDetermined);


                            } else {
                                System.out.println(" array bos = ");
                            }

                            int countXPixel = 0;
                            int rgbFishPixel = 0;
                            for (int yPos = 0; yPos < imageDetermined.getHeight(); yPos++)
                            {
                                //int time =   LocalDateTime.now().getSecond();
                                // System.out.println(" time  = " + time);
                                //  System.out.println("fishArray value = "+fishArray[240]);
                                for (int xPos = 0; xPos < imageDetermined.getWidth(); xPos++) {

                                    // long nanoStartTime = System.nanoTime();
                                    // rgbFishPixel = (fishingAreaArray[countXPixel + xPos] & 0x00ffffff);
                                    rgbFishPixel = (fishingAreaArray[countXPixel + xPos] & 0x00ffffff);


                                    if (rgbFishPixel == getFishOnePixel() || rgbFishPixel == getFishSecondPixel()
                                            || rgbFishPixel == getFishThirdPixel() || rgbFishPixel == getFishFourthPixel()
                                            || rgbFishPixel == getFishFifthPixel() || rgbFishPixel == getFishSixthPixel())
                                    {


                                      /*
                                        robotFishing.mouseMoveFast((int) thisDetScreen.getRectFishClickArea().getX() + xPos,
                                                (int) thisDetScreen.getRectFishClickArea().getY() + yPos);


                                        robotFishing.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                        robotFishing.delay(5);
                                        robotFishing.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                        //robotFishing.delay(1000);

                                        clikcCounter++;
                                        if(clikcCounter >3)
                                        {
                                            robotFishing.delay(500);
                                            clikcCounter=0;
                                            fillWithZeroArray(fishingAreaArray);
                                            xPos = imageDetermined.getWidth();
                                            yPos = imageDetermined.getHeight();
                                            imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectFishClickArea()));
                                            fishingAreaArray = transferBufferImgtoARGB(imageDetermined);
                                        }
                                        else
                                        {
                                            xPos = 0;
                                            yPos = 0;
                                            countXPixel = 0;
                                            //currentTimeSecond = System.currentTimeMillis() / 1000;
                                            //imageDetermined = getBufImageScannedArea(thisDetScreen.getRectFishClickArea());
                                            imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectFishClickArea()));
                                            fishingAreaArray = transferBufferImgtoARGB(imageDetermined);
                                        }*/
                                        ClickingFish(robotFishing,yPos,xPos);
                                        countXPixel = 0;
                                        xPos = imageDetermined.getWidth();
                                        yPos = imageDetermined.getHeight();
                                        imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectFishClickArea()));
                                        fishingAreaArray = transferBufferImgtoARGB(imageDetermined);
                                    }

                                }
                                countXPixel += imageDetermined.getWidth();
                            }

                            // currentTimeSecond = System.currentTimeMillis() / 1000;

                        }
                        if(  comparePinkEquality(pinkFishIndex, getPinkTonIndex(), getPinkTon()))
                        {
                            robotFishing.delay(1000);
                            System.out.println("ALTIN TON BALIGI ALGILANDI");
                            for (int i = 0; i < 3; i++)
                            {
                                robotFishing.keyPress(KeyEvent.VK_ESCAPE);
                                robotFishing.delay(50);
                                robotFishing.keyRelease(KeyEvent.VK_ESCAPE);
                                robotFishing.delay(500);
                            }
                        }
                        //if(isStopped == false && isAnotherPlayer == false && isCharOnline == true && isCharKilled == false && isSaleTitleActive == false)
                        if(GameStatus.GetGameStatusOrUserPrefer())
                        {


                            WearOnOffArmor(robotFishing);

                            if(GameStatus.isAnotherPlayer)robotFishing.delay(TimerGame.MakeRandomIntValueBetweenParams(1,1500));
                            // getonAndOffHorse(robotFishing);

                        }
                    }
                    else
                    {
                        //if(isStopped == false && isAnotherPlayer == false && isCharOnline == true && isCharKilled == false && isSaleTitleActive == false)
                        if(GameStatus.GetGameStatusOrUserPrefer())
                        {

                            WearOnOffArmor(robotFishing);

                            if(GameStatus.isAnotherPlayer)robotFishing.delay(TimerGame.MakeRandomIntValueBetweenParams(1,1500));
                        }


                    }

                    prepareFishing.OpenOrCloseInventory(false);

                }
                else
                {

                      if(comparePinkEquality(pinkFishIndex, getPinkYerYokIndex(), getPinkYerYok()))
                {
                    System.out.println("Envanterde yer kalmadi baliklari yak");
                    prepareFishing.setIsInventorFull(true);
                    prepareFishing.ThrowAWorm();
                    prepareFishing.StartPrepareFishing();
                    prepareFishing.setIsInventorFull(false);
                }else
                      {
                          imageDetermined = getBufImageScannedArea(thisDetScreen.scannableRectMt2Icon(
                                  this,
                                  thisDetScreen.getRectUpChatArea()));
                          fishingAreaArray = transferBufferImgtoARGB(imageDetermined);
                          convertARGBarrayToRGB(fishingAreaArray);
                          //labelFishingArea.setIcon(new ImageIcon(imageDetermined));
                          pinkFishValue= calculatePinkValue(fishingAreaArray,pinkFishIndex);
                          if(comparePinkEquality(pinkFishIndex,getPinkCannotFishIndex(),getPinkCanNotFish()))
                          {
                              prepareFishing.OpenOrCloseInventory(false);

                              //Check Character Button is active and close it
                              prepareFishing.OpenOrCloseSettingButton(false);

                              System.out.println("Character can not fish here !!!");
                              Sleep(100);
                              while (GameStatus.isCharScreenActive);
                              prepareFishing.GoToFishPlace();
                          }
                      }

                }


                
               // System.out.println("active threads = "+Thread.activeCount());

                
            } 
         
                 catch (AWTException ex){
                System.out.println(ex);
            }
            //System.out.println(Thread.currentThread().getName() + " is active");
        }

          }

    }


     public void Sleep(int milliseconds) {
        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException ex) {
            System.out.println(ex);
        
    }
        }

    
    private void typeWormForFishing(RobotMovingForMetin2 robot) {

        BufferedImage buf;
        int [] rgbArray;
        int [] arrayScannedWorm;
        //long startMilliSecond ;
        
        for (int slotCounter = 0; slotCounter < 8; slotCounter++) 
        {
            if(!GameStatus.GetGameStatusOrUserPrefer())return;
            if(slotCounter < 4)
            {

               /* buf = robot.createScreenCapture(new Rectangle(
            thisDetScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter), thisDetScreen.getRectSkillSlotFirstPlace().y 
           ,thisDetScreen.getRectSkillSlotFirstPlace().width, thisDetScreen.getRectSkillSlotFirstPlace().height));*/


                buf = robot.createScreenCapture(thisDetScreen.scannableRectMt2Icon(this,new Rectangle(
                        thisDetScreen.getRectSkillSlotFirstPlace().x +(32 *slotCounter), thisDetScreen.getRectSkillSlotFirstPlace().y
                        ,thisDetScreen.getRectSkillSlotFirstPlace().width, thisDetScreen.getRectSkillSlotFirstPlace().height)));


             rgbArray = transferBufferImgtoARGB(buf);
             convertARGBarrayToRGB(rgbArray);
             arrayScannedWorm = prepareFishing.DetectWorms(rgbArray);
       
        if(CompareTwoArrayAdvanced(prepareFishing.getArrayRgbWormVal(), arrayScannedWorm, SENSIBILTY_HIGH))
        {
                robot.keyPress(KeyEvent.VK_1 + slotCounter);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_1 + slotCounter);
                robot.delay(50);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
        }
            }
            else
            {
                if(isWormSlotHalf == false)
                {
                    System.out.println("yari girdi)");
                    //TODO:enable refreshgame after test
                    RefreshGame();
                    Sleep(20);
                    System.out.println("Gamestatus isCharScreenActive = " + GameStatus.isCharScreenActive);
                    while (GameStatus.isCharScreenActive);
                    isWormSlotHalf = true;
                    //break;
                }
                /*buf = robot.createScreenCapture(new Rectangle(
            thisDetScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4 )) , thisDetScreen.getRectSkillSlotSecondPlace().y 
             ,thisDetScreen.getRectSkillSlotSecondPlace().width, thisDetScreen.getRectSkillSlotSecondPlace().height));*/


                buf = robot.createScreenCapture(thisDetScreen.scannableRectMt2Icon(this,new Rectangle(
                        thisDetScreen.getRectSkillSlotSecondPlace().x +(32 *(slotCounter -4 )) , thisDetScreen.getRectSkillSlotSecondPlace().y
                        ,thisDetScreen.getRectSkillSlotSecondPlace().width, thisDetScreen.getRectSkillSlotSecondPlace().height)));



             rgbArray = transferBufferImgtoARGB(buf);
             convertARGBarrayToRGB(rgbArray);
             arrayScannedWorm = prepareFishing.DetectWorms(rgbArray);
             
             if(CompareTwoArrayAdvanced(prepareFishing.getArrayRgbWormVal(), arrayScannedWorm, SENSIBILTY_HIGH))
        {
                robot.keyPress(KeyEvent.VK_F1 +slotCounter -4);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_F1 +slotCounter -4);
                robot.delay(50);
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_SPACE);
                break;
        }else if(CompareTwoArrayAdvanced(getArrayRgbEmptySlotPlace(), rgbArray, SENSIBILTY_HIGH) &&
                slotCounter >= 7)
        {
            shiftNumber++;
            if(shiftNumber > 4)
            {
                System.out.println("Second Worm Slot Refresh is Running");
                //TODO:enable refreshgame after test
                 RefreshGame();
                 


                //Wait Until Program is detected to exit Char Screen
                Sleep(200);
                System.out.println(" Shift > 4 Gamestatus isCharScreenActive = " + GameStatus.isCharScreenActive);
                while (GameStatus.isCharScreenActive)
                {

                    if (GameStatus.isStopped)
                    {
                        System.out.println("Game is Stopped");
                        return;
                    }
                }
                   //Check Character Button is active and close it
                prepareFishing.OpenOrCloseSettingButton(false);


                    //Check Inventory Page is Closed
                prepareFishing.OpenOrCloseInventory(false);



                    prepareFishing.StartPrepareFishing();
                    //Sleep(7000);
                    isWormSlotHalf = false;
            }
            else
            {
                 System.out.println("shift degisecek)");
                 RefreshGame();
                Sleep(20);
                System.out.println("Shift Degisecek Gamestatus isCharScreenActive = " + GameStatus.isCharScreenActive);
                while (GameStatus.isCharScreenActive);
                 //CheckShiftPageNumber();
                isWormSlotHalf = false;
            }
           
        }else
             {
                 System.out.println("algilamiyor");
             }
                
            }
            
        }
        
        

    }


 
 private void CheckGameStatus()
 {
       //while(isStopped == false )
       while(GameStatus.isStopped == false)
     {
         isPhotoIsTaken =false;
         while (GameStatus.isPausedTheGame)
         {
             if(GameStatus.isStopped)return;
         }
     imageSecDeter = getBufImageScannedArea(new Rectangle(size));
         Rectangle metin2IconDetect = thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectMetin2Icon());
         if(metin2IconDetect.x != 0 && metin2IconDetect.y != 0)
         {
             Point pointValues = new Point(metin2IconDetect.x -thisDetScreen.getRectMetin2Icon().x  ,
                     metin2IconDetect.y - thisDetScreen.getRectMetin2Icon().y  );
             isPhotoIsTaken =true;
             //Get Fish Title Screen
             // imageFishTitle = getBufferSubImage(imageSecDeter,thisDetScreen.getRectFishTitle());
             imageFishTitle = getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectFishTitle().x + pointValues.x,
                     thisDetScreen.getRectFishTitle().y + pointValues.y , thisDetScreen.getRectFishTitle().width,
                     thisDetScreen.getRectFishTitle().height));
             //Get Entry Screen
             //imageEntryScrn = getBufferSubImage(imageSecDeter,thisDetScreen.getRectEntryScreen());
             imageEntryScrn =getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectEntryScreen().x + pointValues.x,
                     thisDetScreen.getRectEntryScreen().y + pointValues.y , thisDetScreen.getRectEntryScreen().width,
                     thisDetScreen.getRectEntryScreen().height));

             //Get Killed Screen
             //imageKillScrn = getBufferSubImage(imageSecDeter,thisDetScreen.getRectDieScreen());
             imageKillScrn =getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectDieScreen().x + pointValues.x,
                     thisDetScreen.getRectDieScreen().y + pointValues.y , thisDetScreen.getRectDieScreen().width,
                     thisDetScreen.getRectDieScreen().height));

             //imageSaleTitle = getBufferSubImage(imageSecDeter,thisDetScreen.getRectSaleCross());
             imageSaleTitle = getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectSaleCross().x + pointValues.x,
                     thisDetScreen.getRectSaleCross().y + pointValues.y , thisDetScreen.getRectSaleCross().width,
                     thisDetScreen.getRectSaleCross().height));

             // imageIsCharOnline = getBufferSubImage(imageSecDeter,thisDetScreen.getRectSettingButton());
             imageIsCharOnline = getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectSettingButton().x + pointValues.x,
                     thisDetScreen.getRectSettingButton().y + pointValues.y , thisDetScreen.getRectSettingButton().width,
                     thisDetScreen.getRectSettingButton().height));

             //imageIsAnotPlayer = getBufferSubImage(imageSecDeter,thisDetScreen.getRectMiniMapArea());
             imageIsAnotPlayer =  getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectMiniMapArea().x + pointValues.x,
                     thisDetScreen.getRectMiniMapArea().y + pointValues.y , thisDetScreen.getRectMiniMapArea().width,
                     thisDetScreen.getRectMiniMapArea().height));

             //  imageCharScreen = getBufferSubImage(imageSecDeter,thisDetScreen.getRectCharScreen());
             imageCharScreen =getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectCharScreen().x + pointValues.x,
                     thisDetScreen.getRectCharScreen().y + pointValues.y , thisDetScreen.getRectCharScreen().width,
                     thisDetScreen.getRectCharScreen().height));

             //imageIsMetin2IconSeemed = getBufferSubImage(imageSecDeter,thisDetScreen.getRectMetin2Icon());
             imageIsMetin2IconSeemed = getBufferSubImage(imageSecDeter,new Rectangle(thisDetScreen.getRectMetin2Icon().x + pointValues.x,
                     thisDetScreen.getRectMetin2Icon().y + pointValues.y , thisDetScreen.getRectMetin2Icon().width,
                     thisDetScreen.getRectMetin2Icon().height));


             arrayEntyScreen = transferBufferImgtoARGB(imageEntryScrn);
             arrayDeath = transferBufferImgtoARGB(imageKillScrn);
             arraySaleTitle = transferBufferImgtoARGB(imageSaleTitle);
             arrayIsCharOnline = transferBufferImgtoARGB(imageIsCharOnline);
             arrayIsAnotPlayer = transferBufferImgtoARGB(imageIsAnotPlayer);
             arrayCharScreen = transferBufferImgtoARGB(imageCharScreen);
             arrayIsMetin2IconSeemed = transferBufferImgtoARGB(imageIsMetin2IconSeemed);

             convertARGBarrayToRGB(arrayEntyScreen);
             convertARGBarrayToRGB(arrayDeath);
             convertARGBarrayToRGB(arraySaleTitle);
             convertARGBarrayToRGB(arrayIsCharOnline);
             convertARGBarrayToRGB(arrayIsAnotPlayer);
             convertARGBarrayToRGB(arrayCharScreen);
             convertARGBarrayToRGB(arrayIsMetin2IconSeemed);

             if(compareTwoArraySimpleAlgorithm(arrayEntyScreen, getArrayRgbEntryScr(),SENSIBILTY_MED))
             {
                 // System.out.println("CheckEntryScreen() if  ");
                 ///isCharOnline = false;
                 GameStatus.isSettingButtonSeemed = false;
                 EntryGame();
             }
             else if(compareTwoArraySimpleAlgorithm(getArrayRgbYelChrtScr(), arrayCharScreen,SENSIBILTY_LOW) == true ||
                     compareTwoArraySimpleAlgorithm(getArrayRgbChrtRedScr(),arrayCharScreen ,SENSIBILTY_MED) == true  ||
                     compareTwoArraySimpleAlgorithm(getArrayRgbChrtBlueScr(),arrayCharScreen ,SENSIBILTY_MED) == true )
             {
                 GameStatus.isCharScreenActive = true;
                 CharacterScreen();
             }
             else if(compareTwoArraySimpleAlgorithm(arrayDeath, getArrayRgbKilledScr(),SENSIBILTY_MED))
             {
                 System.out.println("Entered Killed Status");
                 //isCharKilled =true;
                 GameStatus.isCharKilled =true;
                 KillStatus();
             }
             else if(compareTwoArraySimpleAlgorithm(arraySaleTitle, getArrayRgbSaleTitle(),SENSIBILTY_LOW))
             {
                 //isSaleTitleActive = true;
                 GameStatus.isSaleTitleActive = true;
                 CloseSaleTitle();
             }

             else if(compareTwoArraySimpleAlgorithm(arrayIsCharOnline, getArrayRgbSettingBut(),SENSIBILTY_MED))
             {
                 //  System.out.println("CheckEntryScreen() else ");

                 // System.out.println("isCharOnline = " + isCharOnline);


                 //isCharOnline = true;
                 GameStatus.isSettingButtonSeemed = true;
                 arraySecondThread = transferBufferImgtoARGB(imageFishTitle);
                 convertARGBarrayToRGB(arraySecondThread);
                 GameStatus.isActiveFishBoard = compareTwoArraySimpleAlgorithm(arraySecondThread, getFisherTitleRGBArray(),SENSIBILTY_MED);

                 // System.out.println(isActiveFishBoard);

             }
             else
             {
                 //isCharOnline = false;
                 GameStatus.isSettingButtonSeemed = false;

             }
         }else
         {
             GameStatus.isSettingButtonSeemed = false;
             OpenMetin2FromScreen();

         }

         //System.out.println(isCharOnline);
     
       //  System.out.println(Thread.currentThread().getName() + " is active");
     }

 }

 private boolean IsWantedFishes(int [] comparableArray)
 {
      
     //Fish All of them, don't separate any fish
     if(stateOfTheFishes[4])
     {
         return true;
     }//Yabbie
     if(stateOfTheFishes[0])
     {
          if(comparePinkEquality(comparableArray, getPinkYabbieIndex(), getPinkYabbie()))return true;
     }//Gold Sudak
     if(stateOfTheFishes[1])
     {
        if(comparePinkEquality(comparableArray, getPinkAltinIndex(), getPinkAltinSudak()))return true;
     }//Palamut
     if(stateOfTheFishes[2])
     {
         if(comparePinkEquality(comparableArray, getPinkPalamutIndex(), getPinkPalamut()))return true;
     }//Kurbaga
     if(stateOfTheFishes[3])
     {
         if(comparePinkEquality(comparableArray, getPinkKurbagaIndex(), getPinkKurbaga()))return true;
     }
     if(comparePinkEquality(comparableArray, getPinkTonIndex(), getPinkTon()))
      {
          return true;
      }
     return false;
 }
 private void EntryGame()
 {

     TimerGame timerGame = new TimerGame();
     //long startSecondTime = System.currentTimeMillis()/1000;
     BufferedImage buf =null;
     int [] compareArray;
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(this);
            if(channelValue > 3)
            {
                channelValue =0;
            }
            System.out.println("com.mycompany.autofishing.FishingProcess.EntryGame()");
                robot.mouseMove(thisDetScreen.getPointChSix().x, thisDetScreen.getPointChSix().y - (channelValue * 17));
                robot.delay(50);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseMove(thisDetScreen.getPointOkButton().x, thisDetScreen.getPointOkButton().y);
                robot.delay(50);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
            

                timerGame.SetStartedSecondTime();

                while(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectEntryScreen(),true),
                        getArrayRgbEntryScr(),SENSIBILTY_MED))
                {
                    //if(isStopped == true)return;
                    if(GameStatus.isStopped)return;

                 // if((System.currentTimeMillis() /1000 - startSecondTime ) > 4)
                  if(!timerGame.CheckDelayTimeInSecond(9))
                 {
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.delay(50);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                robot.delay(50);
                robot.mouseMove(thisDetScreen.getPointChSix().x, thisDetScreen.getPointChSix().y - (channelValue * 17));
                robot.delay(50);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseMove(thisDetScreen.getPointOkButton().x, thisDetScreen.getPointOkButton().y);
                robot.delay(50);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);


               // startSecondTime = System.currentTimeMillis()/1000;
                     timerGame.SetStartedSecondTime();
                     System.out.println("Checking Enrty Screen");
                 }

                }
                channelValue++;
                //CharacterScreen();
                //CheckShiftPageNumber();
                //isCharKilled = false;
                GameStatus.isCharScreenActive = true;
                GameStatus.isCharKilled = false;
            } catch (AWTException ex) {
            System.out.println(ex);
        }
             
 }
    public  void OpenMetin2FromScreen()
    {
        GameStatus.isMetin2IconSeemed = false;

        LocalDate dateNow = LocalDate.now();
        if(dateNow.getDayOfWeek() == DayOfWeek.WEDNESDAY &&
                LocalTime.now().getHour() >= 10
                && LocalTime.now().getHour() <= 12)
        {
            System.out.println("Metin2 is currently under maintenance");
            Stop();
            return;
        }else
        {
            Rectangle rectangleGameForgeDesktop ;
            Rectangle rectangleGameForgeOyna;
            //Check Gameforge Desktop Icon
            rectangleGameForgeDesktop = findWantedImageFromScreen(getArrayRgbGameForgeDeskIcon(),
                    thisDetScreen.getRectGameForgeDesktopIcon(),null);

            try {
                RobotMovingForMetin2 robot = new RobotMovingForMetin2(this);
                if(rectangleGameForgeDesktop != null)
                {
                    robot.mouseMoveIndependent(rectangleGameForgeDesktop.x,rectangleGameForgeDesktop.y);
                    robot.delay(20);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(20);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.delay(2000);

                    rectangleGameForgeOyna = findWantedImageFromScreen(getArrayRgbGameForgeOynaButton(),
                            thisDetScreen.getRectGameForgePlayButton(),null);
                    if(rectangleGameForgeOyna != null)
                    {
                        robot.mouseMoveIndependent(rectangleGameForgeOyna.x + rectangleGameForgeOyna.width/2
                                ,rectangleGameForgeOyna.y + rectangleGameForgeOyna.height/2);
                        robot.delay(20);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.delay(20);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        robot.delay(10000);

                        //If we couldn't get metin2 Icon, close the gameforge page

                        Rectangle rectMetin2Icon =thisDetScreen.scannableRectMt2Icon(this,thisDetScreen.getRectMetin2Icon());
                        if (rectMetin2Icon == null)
                        {

                            robot.mouseMoveIndependent(rectangleGameForgeDesktop.x,rectangleGameForgeDesktop.y);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(20);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(30);
                            robot.mouseMoveIndependent(rectangleGameForgeDesktop.x,rectangleGameForgeDesktop.y - 50);
                            robot.delay(2000);
                        }


                    }
                    else
                    {
                        Rectangle rectangleMaximaze = findWantedImageFromScreen(getArrayRgbGameForgeMaximizeButton(),
                                thisDetScreen.getRectGameForgeMaximizeIcon(),null);
                        if(rectangleMaximaze != null)
                        {
                            robot.mouseMoveIndependent(rectangleMaximaze.x + rectangleMaximaze.width/2,
                                    rectangleMaximaze.y + rectangleMaximaze.height/2);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(20);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(1000);
                        }
                    }
                    GameStatus.isMetin2IconSeemed = true;
                }
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }

        }

    }
 /*private void CancelFishing(Robot robot,Rectangle rect)
 {
     robot.mouseMove(rect.x, rect.y);
     robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
     robot.delay(50);
     robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
 }*/
 
private void CharacterScreen()
 {
     TimerGame timer = new TimerGame();
     timer.SetStartedSecondTime();
     long startSecondTime = System.currentTimeMillis()/1000;
     BufferedImage buf =null;
     int [] compareArray;
     RobotMovingForMetin2 robot;
        try {
             robot = new RobotMovingForMetin2(this);


            compareArray = takeScreenShotReturnRGBarray(thisDetScreen.getRectCharScreen(),true);
             System.out.println("com.mycompany.autofishing.FishingProcess.CharacterScreen()");
            // while( (System.currentTimeMillis()/1000) - startSecondTime <13 &&(compareTwoArray(compareArray, getArrayRgbYelChrtScr(),SENSIBILTY_MED) != true &&
              //       compareTwoArray(compareArray, getArrayRgbChrtRedScr(),SENSIBILTY_MED) != true ))

                //Check If Chararacter Menu is opened or not after Char countdown is finished
                 while( timer.CheckDelayTimeInSecond(13) &&(!compareTwoArraySimpleAlgorithm(getArrayRgbYelChrtScr(), compareArray, SENSIBILTY_LOW) &&
                         !compareTwoArraySimpleAlgorithm(getArrayRgbChrtRedScr(), compareArray, SENSIBILTY_MED) &&
                         !compareTwoArraySimpleAlgorithm(getArrayRgbChrtBlueScr(), compareArray, SENSIBILTY_MED)))
             {
                compareArray = takeScreenShotReturnRGBarray(thisDetScreen.getRectCharScreen(),true);
                 System.out.println("while loop");
             }
            
             if(timer.CheckDelayTimeInSecond(13))
             {
                  System.out.println("karakter menusu");
                
                //Press Enter Game button in the Char Menu
                robot.mouseMove(thisDetScreen.getPointCharEnterButton().x, thisDetScreen.getPointCharEnterButton().y);
                robot.delay(30);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(50);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                 robot.delay(20);
                 compareArray = takeScreenShotReturnRGBarray(thisDetScreen.getRectCharScreen(),true);
                 
                 timer.SetStartedSecondTime();


                 //Check Game Screen is active or not after Program press the Enter button in the Char button
                 //If it is not, check the char menu screen again and press Enter game button
                 while( timer.CheckDelayTimeInSecond(15) &&
                              (compareTwoArraySimpleAlgorithm(getArrayRgbYelChrtScr(), compareArray, SENSIBILTY_MED)
                         || compareTwoArraySimpleAlgorithm(getArrayRgbChrtRedScr(), compareArray, SENSIBILTY_MED)) ||
                              compareTwoArraySimpleAlgorithm(getArrayRgbChrtBlueScr(), compareArray,SENSIBILTY_MED))
             {
                 if(GameStatus.isStopped)return;;
                 System.out.println("char screen is active");
                 robot.mouseMove(thisDetScreen.getPointCharEnterButton().x, thisDetScreen.getPointCharEnterButton().y);
                 robot.delay(30);
                 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                 robot.delay(50);
                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                 robot.delay(2000);

                 compareArray = takeScreenShotReturnRGBarray(thisDetScreen.getRectCharScreen(),true);
             }

                 timer.SetStartedSecondTime();

                 //If While loop countdown was ended at the above, and It couldn't get the Game Screen
                 //It would stay the Char Screen and This is the fail of the game(Probably Char hands is up)
                // while (compareTwoArray(compareArray, getArrayRgbSettingBut(),SENSIBILTY_MED) == false ) 
                   while (!compareTwoArraySimpleAlgorithm(getArrayRgbSettingBut(),
                           takeScreenShotReturnRGBarray(thisDetScreen.getRectSettingButton(), true),
                           SENSIBILTY_MED))
                 {
                     if(GameStatus.isStopped)return;;
                     if(!timer.CheckDelayTimeInSecond(30))
                     {
                         System.out.println("oyuna giremedi karakterde kalmistir...");
                         if(!isFirstTimeCharProblem)
                         {
                             isFirstTimeCharProblem =  true;
                             return;
                         }
                         else
                         {
                             //We try to get the Entry Game Screen :)

                             //If screen equals to enrty game return
                             if((compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectEntryScreen(), true),
                                     getArrayRgbEntryScr(), SENSIBILTY_MED)))return;

                             //If Game is active, return
                             if(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectSettingButton(),true),
                                     getArrayRgbSettingBut(),SENSIBILTY_MED))
                             {
                                 GameStatus.isCharScreenActive = false;
                                 return;
                             }

                             System.out.println("the game stayed at the char screen");
                             Rectangle rectangleGameForgeDesktop ;

                             //Check Gameforge Desktop Icon
                             rectangleGameForgeDesktop = findWantedImageFromScreen(getArrayRgbGameForgeDeskIcon(),
                                     thisDetScreen.getRectGameForgeDesktopIcon(),null);


                                 if(rectangleGameForgeDesktop != null) {
                                     robot.mouseMoveIndependent(rectangleGameForgeDesktop.x, rectangleGameForgeDesktop.y);
                                     robot.delay(20);
                                     robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                     robot.delay(20);
                                     robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                     robot.delay(2000);
                                 }
                                 else
                                 {
                                     System.out.println("Program couldn't detect gameforge icon " + new Date());
                                     return;
                                 }
                             Rectangle rectGFOption = findWantedImageFromScreen(getArrayRgbGameForgeOptionButton(),
                                     thisDetScreen.getRectGameForgeOptionsButton(),null);

                                 while(rectGFOption == null)
                                 {
                                     Rectangle rectangleMaximaze = findWantedImageFromScreen(getArrayRgbGameForgeMaximizeButton(),
                                             thisDetScreen.getRectGameForgeMaximizeIcon(),null);
                                     if(rectangleMaximaze != null)
                                     {
                                         robot.mouseMoveIndependent(rectangleMaximaze.x + rectangleMaximaze.width/2,
                                                 rectangleMaximaze.y + rectangleMaximaze.height/2);
                                         robot.delay(20);
                                         robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                         robot.delay(20);
                                         robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                         robot.delay(2000);
                                         rectGFOption = findWantedImageFromScreen(getArrayRgbGameForgeOptionButton(),
                                                 thisDetScreen.getRectGameForgeOptionsButton(),null);
                                     }else
                                     {
                                         robot.mouseMoveIndependent(rectangleGameForgeDesktop.x, rectangleGameForgeDesktop.y);
                                         robot.delay(20);
                                         robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                         robot.delay(20);
                                         robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                         robot.delay(2000);
                                         rectGFOption = findWantedImageFromScreen(getArrayRgbGameForgeOptionButton(),
                                                 thisDetScreen.getRectGameForgeOptionsButton(),null);
                                     }
                                 }


                                 robot.mouseMoveIndependent(rectGFOption.x + rectGFOption.width/2,
                                         rectGFOption.y + rectGFOption.height/2);
                                 robot.delay(20);
                                 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                 robot.delay(20);
                                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                 robot.delay(2000);

                                 Rectangle rectGFoptionOyna = findWantedImageFromScreen(getArrayRgbGameForgeOptionOynaButton(),
                                         thisDetScreen.getRectGameForgeOptionOynaButton(),null);

                                 while(rectGFoptionOyna == null)
                                 {
                                     if(GameStatus.isStopped)return;
                                     robot.mouseMoveIndependent(rectGFOption.x + rectGFOption.width/2,
                                             rectGFOption.y + rectGFOption.height/2);
                                     robot.delay(20);
                                     robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                     robot.delay(20);
                                     robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                     robot.delay(2000);
                                     rectGFoptionOyna = findWantedImageFromScreen(getArrayRgbGameForgeOptionOynaButton(),
                                             thisDetScreen.getRectGameForgeOptionOynaButton(),null);
                                 }
                                 robot.mouseMoveIndependent(rectGFoptionOyna.x + rectGFoptionOyna.width/2,
                                         rectGFoptionOyna.y + rectGFoptionOyna.height/2);
                                 robot.delay(20);
                                 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                 robot.delay(30);
                                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                 robot.delay(10000);
                             isFirstTimeCharProblem = false;
                             return;
                         }

                     }

                 }
                   //Char need to wait to not get any error in the game.
                 //This time variable can changeable for the Computer core speed and ram
                 robot.delay(5000);
                 robot.keyPress(KeyEvent.VK_ESCAPE);
                 robot.delay(50);
                 robot.keyRelease(KeyEvent.VK_ESCAPE);
                 robot.delay(50);
                 robot.keyPress(KeyEvent.VK_ESCAPE);
                 robot.delay(50);
                 robot.keyRelease(KeyEvent.VK_ESCAPE);
                 robot.delay(50);
                 robot.keyPress(KeyEvent.VK_W);
                 robot.delay(500);
                 robot.keyRelease(KeyEvent.VK_W);

                   CheckShiftPageNumber();
                   GameStatus.isCharScreenActive = false;
                   isFirstTimeCharProblem =  false;


             
             }
             
            
        } catch (AWTException ex) {
           
        }



 }
 
 private void KillStatus()
 {
     SettingButtonsPreferring(EXIT_BUTTON_SETTING);

 }
 
 private void CloseSaleTitle()
 {

        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(this);
            robot.mouseMove(thisDetScreen.getRectSaleCross().x, thisDetScreen.getRectSaleCross().y);
            robot.delay(40);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(40);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(40);
            

            while(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectSaleCross(),true),
                    getArrayRgbSaleTitle(),SENSIBILTY_MED) == true)
            {
            robot.mouseMove(thisDetScreen.getRectSaleCross().x, thisDetScreen.getRectSaleCross().y);
            robot.delay(40);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(40);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(40);

            }
            
            GameStatus.isSaleTitleActive = false;
        } catch (AWTException ex) 
        {
        }
 }
 private void RefreshGame()
 {
     SettingButtonsPreferring(CHAR_BUTTON_SETTING);

 }
 private void WearOnOffArmor(RobotMovingForMetin2 robot)
 {
     robot.keyPress(KeyEvent.VK_ESCAPE);
     robot.delay(50);
     robot.keyRelease(KeyEvent.VK_ESCAPE);
     robot.delay(100);
     if(GameStatus.isAnotherPlayer)robot.delay(TimerGame.MakeRandomIntValueBetweenParams(1,1200));
     robot.keyPress(KeyEvent.VK_I);
     robot.delay(50);
     robot.keyRelease(KeyEvent.VK_I);
     robot.delay(50);
     robot.mouseMove(thisDetScreen.getPointArmorPlace().x, thisDetScreen.getPointArmorPlace().y);
     robot.delay(50);
     robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
     robot.delay(100);
     robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
     robot.delay(100);

     robot.keyPress(KeyEvent.VK_I);
     robot.delay(50);
     robot.keyRelease(KeyEvent.VK_I);
     robot.delay(50);
 }

 public void CheckShiftPageNumber()
 {

     TimerGame timerGame = new TimerGame();
     timerGame.SetStartedSecondTime();
      //long startSecondTime = System.currentTimeMillis()/1000;

        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(this);
            if(GameStatus.isStopped && !GameStatus.isSettingButtonSeemed && GameStatus.isPausedTheGame)return;
          
            
            if(shiftNumber == 1)
            {
                while(timerGame.CheckDelayTimeInSecond(15)
                        && compareTwoArraySimpleAlgorithm(getArrayRgbPageOne(),
                        takeScreenShotReturnRGBarray(thisDetScreen.getRectPageNumCoordinates()
                        ,true),
                        SENSIBILTY_HIGH) != true )
                 {
                      if(GameStatus.isStopped && !GameStatus.isSettingButtonSeemed && GameStatus.isPausedTheGame)return;
                      robot.delay(30);
                      robot.keyPress(KeyEvent.VK_SHIFT);
                      robot.delay(20);
                      robot.keyPress(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(100);
                      robot.keyRelease(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(20);
                      robot.keyRelease(KeyEvent.VK_SHIFT);
                      robot.delay(700);
                      

                 }
            }else if(shiftNumber == 2)
            {
                
                      
                 while(timerGame.CheckDelayTimeInSecond(15) && compareTwoArraySimpleAlgorithm(
                         takeScreenShotReturnRGBarray(thisDetScreen.getRectPageNumCoordinates()
                                 ,true),
                         getArrayRgbPageTwo(),SENSIBILTY_HIGH) == false )
                 {
                     System.out.println("222");
                     if(GameStatus.isStopped && !GameStatus.isSettingButtonSeemed && GameStatus.isPausedTheGame)return;
                      robot.delay(30);
                      robot.keyPress(KeyEvent.VK_SHIFT);
                      robot.delay(20);
                      robot.keyPress(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(100);
                      robot.keyRelease(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(20);
                      robot.keyRelease(KeyEvent.VK_SHIFT);
                      robot.delay(700);
                      

                 }
            }else if(shiftNumber == 3)
            {
                while(timerGame.CheckDelayTimeInSecond(15) && compareTwoArraySimpleAlgorithm(
                        takeScreenShotReturnRGBarray(thisDetScreen.getRectPageNumCoordinates()
                        ,true), getArrayRgbPageThree(),SENSIBILTY_HIGH) != true )
                 {
                     if(GameStatus.isStopped && !GameStatus.isSettingButtonSeemed && GameStatus.isPausedTheGame)return;
                      robot.delay(30);
                      robot.keyPress(KeyEvent.VK_SHIFT);
                      robot.delay(20);
                      robot.keyPress(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(100);
                      robot.keyRelease(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(20);
                      robot.keyRelease(KeyEvent.VK_SHIFT);
                      robot.delay(700);

                 }
            }else if(shiftNumber == 4)
            {
                 while((timerGame.CheckDelayTimeInSecond(15)) && compareTwoArraySimpleAlgorithm(
                takeScreenShotReturnRGBarray(thisDetScreen.getRectPageNumCoordinates()
                        ,true), getArrayRgbPageFour(),SENSIBILTY_HIGH) != true )
                 {
                     if(GameStatus.isStopped && !GameStatus.isSettingButtonSeemed && GameStatus.isPausedTheGame)return;
                      robot.delay(30);
                      robot.keyPress(KeyEvent.VK_SHIFT);
                      robot.delay(20);
                      robot.keyPress(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(100);
                      robot.keyRelease(KeyEvent.VK_1 + shiftNumber -1);
                      robot.delay(20);
                      robot.keyRelease(KeyEvent.VK_SHIFT);
                      robot.delay(700);

                 }
            }
        
        } catch (AWTException ex) {
            
        }
 }

 public void SettingButtonsPreferring(int buttonType)
 {
     TimerGame timerGame = new TimerGame();
     int [] array;
     int [] pinkIndex = new int[110];
     BufferedImage buf;
     RobotMovingForMetin2 robot;
     try
     {
          robot = new RobotMovingForMetin2(this);

         //Check Setting Option is active and get it
         prepareFishing.OpenOrCloseSettingButton(true);


         if(buttonType == CHAR_BUTTON_SETTING)
         {
             GameStatus.isCharScreenActive = true;
             robot.mouseMove(thisDetScreen.getRectCharButton().x, thisDetScreen.getRectCharButton().y);
         }else
         {
             robot.mouseMove(thisDetScreen.getPointExitButton().x,thisDetScreen.getPointExitButton().y);
         }
             robot.delay(50);
             robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
             robot.delay(50);
             robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);


             timerGame.SetStartedSecondTime();

             //Check char screen is opened
             while(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectSettingButton(),true),
                     getArrayRgbSettingBut(),SENSIBILTY_MED) != false )
             {
                 //if(isStopped == true)return;
                 if(GameStatus.isStopped == true)return;

                 if(timerGame.CheckDelayTimeInSecond(2))
                 {


                     array = takeScreenShotReturnRGBarray(thisDetScreen.getRectExitChatArea(),true);
                     calculatePinkValue(array, pinkIndex);
                     if(comparePinkEquality(pinkIndex, getPinkExitChatAreaIndex(), getPinkExitChatArea()))
                     {
                         //startSecondTime = System.currentTimeMillis()/1000;
                         timerGame.SetStartedSecondTime();
                         System.out.println("Exit or Char second typing is detected ");


                         //Check If it exits from Game Screen
                         while(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectSettingButton(),true),
                                 getArrayRgbSettingBut(),SENSIBILTY_MED) &&
                                 timerGame.CheckDelayTimeInSecond(11))  //(System.currentTimeMillis()/1000 - startSecondTime) < 11 )
                         {

                             if(GameStatus.isStopped == true)return;

                         }
                     }

                 }
                 else
                 {
                     while(compareTwoArraySimpleAlgorithm(takeScreenShotReturnRGBarray(thisDetScreen.getRectCharButton(),true)
                             , getArrayRgbCharButton(),SENSIBILTY_MED)!= true)
                     {
                         //if(isStopped == true)return;
                         if(GameStatus.isStopped == true)return;
                         //System.out.println("com.mycompany.autofishing.FishingProcess.typeWormForFishing()");
                         if(GameStatus.isAnotherPlayer && GameStatus.isCharKilled == false)
                         {
                             robot.mouseMove(thisDetScreen.getRectCharButton().x - 150,
                                     thisDetScreen.getRectCharButton().y);
                             robot.delay(10);
                             robot.keyPress(KeyEvent.VK_ESCAPE);
                             robot.delay(50);
                             robot.keyRelease(KeyEvent.VK_ESCAPE);
                             robot.delay(50);
                         }else {
                             robot.delay(20);
                             robot.mouseMove(thisDetScreen.getRectSettingButton().x, thisDetScreen.getRectSettingButton().y);
                             robot.delay(40);
                             robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                             robot.delay(40);
                             robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                             robot.delay(1000);
                         }

                     }

                     robot.delay(50);
                     if(buttonType == CHAR_BUTTON_SETTING)
                     {

                         robot.mouseMove(thisDetScreen.getRectCharButton().x, thisDetScreen.getRectCharButton().y);
                     }else
                     {
                         robot.mouseMove(thisDetScreen.getPointExitButton().x,thisDetScreen.getPointExitButton().y);
                     }

                     robot.delay(50);
                     robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                     robot.delay(50);
                     robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                     //startSecondTime = System.currentTimeMillis()/1000;
                     timerGame.SetStartedSecondTime();
                 }

             }
     }catch (AWTException e)
     {
         System.out.println(e);
     }

     if (buttonType == CHAR_BUTTON_SETTING)
     {
         GameStatus.isCharScreenActive = true;
     }
     else
     {
         GameStatus.isCharKilled = true;
     }


 }

 private void ClickingFish(RobotMovingForMetin2 robotFishing,int yPos,int xPos)
 {

     if(storeAttempt == 0)
     {
         robotFishing.mouseMoveFast((int) thisDetScreen.getRectFishClickArea().getX() + xPos ,
                 (int) thisDetScreen.getRectFishClickArea().getY() + yPos );
         storeAttempt++;
     }
     else if(storeAttempt == 1)
     {
         //n = x index
         //n+1 = y index
         arrayStoredFishValue[0] = xPos;
         arrayStoredFishValue[1] = yPos;
         robotFishing.mouseMoveFast((int) thisDetScreen.getRectFishClickArea().getX() + xPos ,
                 (int) thisDetScreen.getRectFishClickArea().getY() + yPos );
         storeAttempt++;
     }
   /*  else if(storeAttempt == 1)
     {
         //n = x index
         //n+1 = y index
         arrayStoredFishValue[2] = xPos;
         arrayStoredFishValue[3] = yPos;
         storeAttempt++;
     }*/
     else
     {
         //n = x index
         //n+1 = y index
         arrayStoredFishValue[2] = xPos;
         arrayStoredFishValue[3] = yPos;

         int yResult = arrayStoredFishValue[1] - arrayStoredFishValue[3] ;
         int xResult = arrayStoredFishValue[0] - arrayStoredFishValue[2] ;

         if((yResult > -15 && yResult < 15) && (xResult > -15 && xResult < 15))
         {
             robotFishing.mouseMoveFast((int) thisDetScreen.getRectFishClickArea().getX() + xPos + xResult,
                     (int) thisDetScreen.getRectFishClickArea().getY() + yPos + yResult);
             robotFishing.mousePress(InputEvent.BUTTON1_DOWN_MASK);
             robotFishing.delay(5);
             robotFishing.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
             robotFishing.delay(500);
         }

         storeAttempt =0;


     }
 }
    /*public boolean compareTwoArray(int [] comparableArray, int [] sourceArray , int sensibilityLevel)
    {
        int equalityVar =0;
        
        int ColorSourceRed =0;
        int ColorSourceGreen =0;
        int ColorSourceBlue =0;
        
        int ColorCompareRed =0;
        int ColorCompareGreen =0;
        int ColorCompareBlue =0;
       
         
        for (int i = 0; i < sourceArray.length; i++) {

            ColorSourceRed = (sourceArray[i] & 0x00ff0000) >> 16;
            ColorSourceGreen = (sourceArray[i] & 0x0000ff00) >> 8;
            ColorSourceBlue = sourceArray[i] & 0x000000ff;
            
            
            ColorCompareRed = (comparableArray[i] & 0x00ff0000) >> 16;
            ColorCompareGreen = (comparableArray[i] & 0x0000ff00) >> 8;
            ColorCompareBlue = comparableArray[i] & 0x000000ff;
            
           // System.out.println("count = "+ count +" red = "+ ColorRed + " green = " + ColorGreen + " blue = " + ColorBlue);
            if(Math.abs(ColorSourceRed - ColorCompareRed) < 6  && Math.abs(ColorSourceGreen - ColorCompareGreen) <6
                    && Math.abs(ColorSourceBlue - ColorCompareBlue) < 6)
             {
                 equalityVar++;       
             }
             
            
         
    }
        return equalityVar >= sourceArray.length -1;
    }*/

    



    public int[] getfishingAreaArray() {
        return fishingAreaArray;
    }

    public void setLengthFishingAreaArray(Rectangle2D rect) {
        fishingAreaArray = new int[(int) rect.getWidth() * (int) rect.getHeight()];
    }

    /*public boolean getIsActiveFishingClass() {
        return isActiveFishingClass;

    }*/

    public JLabel getLabelFishingArea() {
        return labelFishingArea;
    }

    public int getShiftNumber() {
        return shiftNumber;
    }

    public void setShiftNumber(int shiftNumber) {
        this.shiftNumber = shiftNumber;
    }

    public PrepareFishing getPrepareFishing() {
        return prepareFishing;
    }
}
