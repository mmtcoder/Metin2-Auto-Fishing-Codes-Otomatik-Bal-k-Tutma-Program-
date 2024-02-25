package com.mycompany.autofishing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;

public class Chatting 
{
    private GameObjectCoordinates coordinates;
    private FishingProcess fishingProcess;

    private static int[] arrayYellowCharName;
    private static boolean[] arrayBoolCharName;
    private static File [] FILE_CHAT_PNG;
    private static BufferedImage[] BUFFER_IMAGE_CHAT;
    private static int [][] ARRAYS_CHAT_RGB;
    private static boolean [][] ARRAYS_BOOL_SOURCE ;
    private static Rectangle rectChatNameArea;
    private static Rectangle rectFullChatArea = new Rectangle(113,577,500,12);
    private static Rectangle rectMySentencesArea ;

    private final String pathChatpng = "chatPng";

    private int answerCounter =0;
    private static int saveAnotherPlayerChat = 0;
    private boolean answerElseBoolStatement = false;

    private boolean [] oldWhiteWordArray;
    public Chatting(FishingProcess fishingProcess)
    {
        coordinates = new GameObjectCoordinates();
        this.fishingProcess = fishingProcess;

        FILE_CHAT_PNG = getFilesFromWantedFolder(pathChatpng);
        BUFFER_IMAGE_CHAT = getBufImagesFromFiles(FILE_CHAT_PNG);
        ARRAYS_CHAT_RGB = initializeChatRgbArrays(getBufImagesFromFiles(getFilesFromWantedFolder(pathChatpng)));
        ARRAYS_BOOL_SOURCE = new boolean[ARRAYS_CHAT_RGB.length][];
        for(int i=0; i < ARRAYS_CHAT_RGB.length; i++)
        {
            ARRAYS_BOOL_SOURCE[i] = fishingProcess.RecordWantedColorAsBool(fishingProcess.CHAT_WHITE_COLOR,ARRAYS_CHAT_RGB[i]);
        }

        /*for(int i=0; i < arraysChatWhiteIndex.length; i++)
        {
            printArray("arraysChatWhiteIndex index = " + String.format("%d",i) + "and values = "
                    , arraysChatWhiteIndex[i]);
        }*/
    }
    public void detectCharacterName()
    {

        if(arrayYellowCharName != null)
        {
            //System.out.println("arrayRGBcharName is already initialized ");
            return;
        }
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            robot.keyPress(KeyEvent.VK_F);
            robot.delay(30);
            robot.keyPress(KeyEvent.VK_G);
            robot.delay(1500);
            robot.keyRelease(KeyEvent.VK_F);
            robot.delay(30);
            robot.keyRelease(KeyEvent.VK_G);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        Rectangle tempRect = fishingProcess.findBorderAreaForWantedColor(fishingProcess.PLAYER_YELLOW_COLOR,
                coordinates.scannableRectMt2Icon(fishingProcess,coordinates.getRectCharNameArea()));
        if(tempRect  == null)
        {
            System.out.println("detectCharName method couldn't find char name rectangle area...");
            return;
        }

        rectChatNameArea = new Rectangle(113,577,tempRect.width,tempRect.height);
        rectMySentencesArea = new Rectangle(rectChatNameArea.x + rectChatNameArea.width + 10,
                rectChatNameArea.y,500,12);

       int [] comparableArray = fishingProcess.takeScreenShotReturnRGBarray(tempRect,true);

        arrayYellowCharName = fishingProcess.RecordWantedColorIndex(FishingProcess.PLAYER_YELLOW_COLOR,comparableArray);
        arrayBoolCharName = fishingProcess.RecordWantedColorAsBool(FishingProcess.PLAYER_YELLOW_COLOR,comparableArray);
        //printArray("CharNamearray index = ",arrayRGBcharName);

    }

    public File[] getFilesFromWantedFolder(String folderPathName)
    {
        File folder = new File(folderPathName);
        File[] files = new File[folder.listFiles().length];
        int counter =0;
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getFilesFromWantedFolder(fileEntry.getPath());
            } else {
               // System.out.println(fileEntry.getName());
                files[counter++] = fileEntry;
            }
        }
    return files;

}
public BufferedImage[] getBufImagesFromFiles(File [] files)
{
    BufferedImage[] bufferedImages = new BufferedImage[files.length];
    int counter =0;
    for(final File file: files)
    {
        try {

            bufferedImages[counter++] =  ImageIO.read((getClass().getClassLoader().getResource(file.getPath())));
        } catch (IOException |IllegalArgumentException |NullPointerException ex ) 
        {
             counter =0;
             for( final File file2: files)
        {
             try
            {
                //System.out.println("file path = " + file2.getPath());
                bufferedImages[counter++] = ImageIO.read(file2);
                 
            }
            catch(IOException e)
            {
                System.out.println(e);
                 return null;
            }
               
         }
             break;
        }
        
        }
    
    return bufferedImages;
}



public int [][] initializeChatRgbArrays(BufferedImage [] bufIMages)
{
    int [][] arraysRgbChat = new int[bufIMages.length][];
    int [] arrayComparable ;
    int counter =0;

    for (final BufferedImage image : bufIMages)
    {
        arrayComparable = fishingProcess.transferBufferImgtoARGB(image);
        fishingProcess.convertARGBarrayToRGB(arrayComparable);
        arraysRgbChat[counter++] =arrayComparable;

    }
    return arraysRgbChat;
}
    public boolean chatIsActive()
    {
        int[] comparableRgbArray = fishingProcess.takeScreenShotReturnRGBarray(coordinates.getRectChatIsActive(),true);
        boolean [] sourceChatOpenArray = fishingProcess.RecordWantedColorAsBool(fishingProcess.CHAT_WHITE_COLOR,fishingProcess.getArrayRgbChatOpen());
        boolean [] compChatOpenArray = fishingProcess.RecordWantedColorAsBool(fishingProcess.CHAT_WHITE_COLOR,comparableRgbArray);
        for (int i =0; i < sourceChatOpenArray.length; i++)
        {
            if(sourceChatOpenArray[i] != compChatOpenArray[i])
            {
                return false;
            }
        }
        return true;
    }

    public void detectChatTypings()
    {
        if(!chatIsActive())
        {

            for (int chatHeight = 0 ; chatHeight < 4 ; chatHeight++)
            {
                if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed)return;

                Rectangle [] rectWordArray = fishingProcess.parseWordsFromOneLineArea(fishingProcess.CHAT_WHITE_COLOR,
                        fishingProcess.findBorderAreaForWantedColor(fishingProcess.CHAT_WHITE_COLOR,
                                new Rectangle(rectFullChatArea.x,rectFullChatArea.y - (chatHeight * GameObjectCoordinates.DISTANCE_BETW_CHAT_HEIGHT)
                                ,rectFullChatArea.width,rectFullChatArea.height)));

                if(rectWordArray != null)
                {

                    int [][] arrayRgbValues  = new int[rectWordArray.length][];
                    boolean [][] boolArrayValues = new boolean[arrayRgbValues.length][];

                    // System.out.println("Detected boolArrayValues length = " + boolArrayValues.length);

                    for (int i =0; i < rectWordArray.length; i++)
                    {
                        // System.out.println(" arrayRgbValues index value = " + i );
                        if(rectWordArray[i] != null && rectWordArray[i].width > 0 && rectWordArray[i].height >0)
                        {
                            arrayRgbValues[i] = fishingProcess.takeScreenShotReturnRGBarray(rectWordArray[i],true);
                        }else
                        {
                            arrayRgbValues[i] = null;
                        }


                    }

                    for (int i =0; i < arrayRgbValues.length; i++)
                    {

                        if(arrayRgbValues[i] != null)
                        {
                            boolArrayValues[i] = fishingProcess.RecordWantedColorAsBool(fishingProcess.CHAT_WHITE_COLOR,
                                    arrayRgbValues[i]);
                        }

                    }

                    //If Program didn't detect char name before, detect it here
                    while (arrayBoolCharName == null)
                    {
                        if(GameStatus.isStopped)return;
                        detectCharacterName();
                        System.out.println("Detection char name in the while loop" + new Date());
                        TimerGame.Sleep(200);
                    }

                    boolean isNametheSame = true;


                        if(boolArrayValues[0] != null)
                        {

                                for (int arrayBoolIndex =0; arrayBoolIndex < arrayBoolCharName.length ; arrayBoolIndex++)
                                {
                                if(boolArrayValues[0][arrayBoolIndex] != arrayBoolCharName[arrayBoolIndex])
                                {
                                  //  imageFileWriteToDesktop("AnotherChatTyping" + saveAnotherPlayerChat);
                                   // saveAnotherPlayerChat++;
                                    System.out.println("Detected char name is not the same with my char name" + new Date());
                                    isNametheSame = false;
                                    break;
                                }
                            }

                    } else
                        {
                            return;
                        }

                    if (isNametheSame)
                    {
                        System.out.println("If for condition is not broken, it means name equals to my name " +
                                "and we returned from our detectChatTypings method" + new Date());
                        return;
                    }


                    //Check if the first sentence of the another player is not the same
                    //with previous one

                        boolean isOldWhiteWordTheSame = true;
                        if(boolArrayValues.length >= 3 )
                        {
                            if( boolArrayValues[2] != null)
                            {
                                if((oldWhiteWordArray != null) && oldWhiteWordArray.length >= boolArrayValues[2].length )
                                {
                                    for(int counter =0; counter < boolArrayValues[2].length; counter++)
                                    {
                                        if(oldWhiteWordArray[counter] != boolArrayValues[2][counter])
                                        {
                                            oldWhiteWordArray = boolArrayValues[2];
                                            isOldWhiteWordTheSame = false;
                                            break;
                                        }

                                    }
                                    if(isOldWhiteWordTheSame)
                                    {
                                        //System.out.println("This sentence is the same with previous ");
                                        return;
                                    }
                                }
                                else
                                {
                                    oldWhiteWordArray = boolArrayValues[2];
                                }
                            }else
                            {
                                return;
                            }

                    }else
                        {
                            System.out.println("boolArrayValues[2] is NULL!! \n Detected rectangle length = " + rectWordArray.length
                                    + "arrayRgb length = " + arrayRgbValues.length + new Date());
                            return;
                        }


                    for (int arraySourceBufIndex =0; arraySourceBufIndex < ARRAYS_BOOL_SOURCE.length; arraySourceBufIndex++)
                    {
                        // System.out.println("Image is processing = " + FILE_CHAT_PNG[arraySourceBufIndex].getName() );

                        // for(int indexCompRgbIndex =0; indexCompRgbIndex <boolArrayValues.length; indexCompRgbIndex++ )
                        // {

                        for(int boolCompArrayIndex =0; boolCompArrayIndex < boolArrayValues.length; boolCompArrayIndex++)
                        {
                            boolean detectedWord = true;
                            if(arrayRgbValues[boolCompArrayIndex] != null)
                            {
                                for(int secondIndex =0; secondIndex < boolArrayValues[boolCompArrayIndex].length ; secondIndex++)
                                {
                                   // System.out.println(FILE_CHAT_PNG[0].getName().split("."));
                                    if( ARRAYS_BOOL_SOURCE[arraySourceBufIndex].length
                                            == boolArrayValues[boolCompArrayIndex].length )
                                    {
                                        if(ARRAYS_BOOL_SOURCE[arraySourceBufIndex][secondIndex] !=
                                                boolArrayValues[boolCompArrayIndex][secondIndex])
                                        {

                                            detectedWord = false;
                                            break;
                                        }
                                    }else
                                    {
                                               /* System.out.println(  FILE_CHAT_PNG[arraySourceBufIndex].getName() +
                                                        " length is not the same with ARRAYS_BOOL_SOURCE [" + arraySourceBufIndex
                                                + "]");*/
                                        detectedWord = false;
                                        break;
                                    }

                                }


                            } else
                            {
                                // System.out.println("ARRAYS_BOOL_SOURCE[ " + boolCompArrayIndex + "] is empty");
                                detectedWord = false;

                            }
                            if(detectedWord)
                            {

                                ChatHandler(arraySourceBufIndex,true);


                                return;
                            }else
                            {
                                if(GameStatus.isAnotherPlayer)
                                {
                                    if(!answerElseBoolStatement)
                                    {
                                        if(chatHeight == 3 && arraySourceBufIndex == ARRAYS_BOOL_SOURCE.length -1)
                                        {
                                           ChatHandler(arraySourceBufIndex,false);
                                            answerElseBoolStatement = true;
                                        }
                                    }
                                }else
                                {
                                    answerElseBoolStatement = false;
                                }

                            }

                        }

                    }
                }
                else
                {
                  //  System.out.println("It couldn't detect any words height index = " + chatHeight);
                }
            }
           /* Rectangle [] rectWordArray = parseWordsFromOneLineArea(CHAT_WHITE_COLOR,
                    findBorderAreaForWantedColor(CHAT_WHITE_COLOR,rectFullChatArea));*/




        }

    }

    public void answerTheDialog(File file,String filePreName,String answer)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);

            if(file != null)
            {

                String [] prefix = file.getName().split("\\.");
                if(Objects.equals(prefix[0], filePreName))
                {
                    GameStatus.isChatting = true;
                    TimerGame.Sleep(TimerGame.MakeRandomIntValueBetweenParams(1500,2500));

                    System.out.println("Detected name = " + file.getName() + new Date());
                    //Open the Chat
                    while (!chatIsActive())
                    {
                        //TODO: Don't forget the activate this if
                        if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed)return;
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.delay(30);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        robot.delay(500);
                    }

                    typingWordToChat(answer);

                    //Close the Chat
                    while (chatIsActive())
                    {
                        //TODO: Don't forget the activate this if
                        if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed)return;
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.delay(30);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                        robot.delay(500);
                    }

                    GameStatus.isChatting = false;
                }
            }else
            {
                GameStatus.isChatting = true;
                TimerGame.Sleep(TimerGame.MakeRandomIntValueBetweenParams(1500,2500));

                while (!chatIsActive())
                {
                    //TODO: Don't forget the activate this if
                    if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed)return;
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.delay(30);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.delay(500);
                }

                typingWordToChat(answer);

                //Close the Chat
                while (chatIsActive())
                {
                    //TODO: Don't forget the activate this if
                    if(GameStatus.isStopped || !GameStatus.isSettingButtonSeemed)return;
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.delay(30);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.delay(500);
                }

                GameStatus.isChatting = false;
            }

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }

    private void ChatHandler(int arraySourceBufIndex,boolean isDetected)
    {
        if(isDetected)
        {
            if(answerCounter ==0)
            {
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"bot","hayir ben bot degilim");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"hile","ben hile kullanmiyorum");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"kg"," sagol reis sanada");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"rastgele","sagol iyi oyunlar");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"sa","as");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"selam","selam");
                answerCounter++;
            }
            else if(answerCounter == 1)
            {
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"bot","ben ustume alinmiyorum");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"hile","hile sensin");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"kg"," sagolasin");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"rastgele","tesekkurler");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"sa","a. selam");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"selam","merhaba");
                answerCounter++;
            }
            else if(answerCounter == 2)
            {
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"bot","maalesef burada bot bulunmamaktadir");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"hile","nereden cikardin hileyi");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"kg"," tsk");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"rastgele","sanada iyi oyunlar");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"sa","aleykum selam");
                answerTheDialog(FILE_CHAT_PNG[arraySourceBufIndex],"selam","slm");
                answerCounter =0;
            }
        }
        else
        {
            if(answerCounter == 0)
            {
                answerTheDialog(null,"df","su an konsantre olmaya calisiyorum");
                answerTheDialog(null,"df","bu yuzden cevap vermiyecegim");
                answerCounter++;
            }
            else if(answerCounter == 1)
           {
            answerTheDialog(null,"sf","balik tutmaya calisiyorum cevap vermeyecegim");
            answerCounter++;
           }
            else if(answerCounter == 2)
            {
                answerTheDialog(null,"sd","balik tutuyorum senin ile sohbet etmiyecegim");
                answerCounter =0;
            }
        }


    }

    private void typingWordToChat(String sentence)
    {
        try {
            RobotMovingForMetin2 robot = new RobotMovingForMetin2(fishingProcess);
            sentence = sentence.toUpperCase();
            for(int i=0; i<sentence.length(); i++)
            {
                if(sentence.charAt(i) == 'İ')
                {

                    robot.keyPress(KeyEvent.VK_I);
                    robot.delay(30);
                    robot.keyRelease(KeyEvent.VK_I);
                    robot.delay(30);
                }
                else
                {
                    robot.keyPress(sentence.codePointAt(i));
                    robot.delay(30);
                    robot.keyRelease(sentence.codePointAt(i));
                    robot.delay(30);
                }

            }
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalArgumentException i)
        {
            System.out.println("We used illegal alphabet for robot.Keypress function");
        }
    }

    public void WearOnorOffWantedItem(int [] wantedRgbSourceArray,Rectangle rectSourceArray,boolean state)
    {
        Rectangle rectangleOldValue ;
        RobotMovingForMetin2 robot ;
        int attemption =0;
        try {
            robot = new RobotMovingForMetin2(fishingProcess);
            fishingProcess.getPrepareFishing().OpenOrCloseInventory(true);

            Rectangle rectEquipmantPage = fishingProcess.findWantedImageFromScreen(fishingProcess.getArrayRgbIsEquipPageOpen(),
                    coordinates.getRectEquipmentArea(),null);

            while (rectEquipmantPage == null)
            {
                robot.mouseMove(coordinates.getRectInventory().x+30,coordinates.getRectInventory().y - 30);
                robot.delay(15);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(30);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.delay(400);

                rectEquipmantPage = fishingProcess.findWantedImageFromScreen(fishingProcess.getArrayRgbIsEquipPageOpen(),
                        coordinates.getRectEquipmentArea(),null);
            }
                //It can detect equipment area and open it until here
                //It try to detect wanted shape and make wear on or wear off operation
            Rectangle  rectWantedShape = fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,
                    rectSourceArray,null);

            if(state == true)
            {
                if(rectWantedShape == null)
                {
                    fishingProcess.getPrepareFishing().OpenOrCloseInventory(true);
                    robot.delay(1000);

                    fishingProcess.getPrepareFishing().ClickPage(1);
                    rectangleOldValue = fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null);

                    if(rectangleOldValue != null)
                    {
                         robot.mouseMoveIndependent(rectangleOldValue.x + rectangleOldValue.width/2,rectangleOldValue.y + rectangleOldValue.height/2);
                        robot.delay(20);
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(30);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(500);

                        //If The program wear on this item, this loop must not entry while
                        //else it try to wear on it again...
                        while (rectangleOldValue == fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null))
                        {
                            if(GameStatus.isStopped)return;
                            if(!GameStatus.isSettingButtonSeemed)return;

                            rectangleOldValue = fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null);

                            robot.mouseMoveIndependent(rectangleOldValue.x + rectangleOldValue.width/2,rectangleOldValue.y + rectangleOldValue.height/2);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            robot.delay(30);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            robot.delay(500);
                        }
                    }
                    else
                    {
                        fishingProcess.getPrepareFishing().ClickPage(2);
                        rectangleOldValue = fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null);
                        if(rectangleOldValue != null)
                        {
                            robot.mouseMoveIndependent(rectangleOldValue.x + rectangleOldValue.width/2,rectangleOldValue.y + rectangleOldValue.height/2);
                            robot.delay(20);
                            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                            robot.delay(30);
                            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            robot.delay(500);

                            while (rectangleOldValue == fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null))
                            {
                                if(!GameStatus.isSettingButtonSeemed)return;

                                rectangleOldValue = fishingProcess.findWantedImageFromScreen(wantedRgbSourceArray,rectSourceArray,null);

                                robot.mouseMoveIndependent(rectangleOldValue.x + rectangleOldValue.width/2,rectangleOldValue.y + rectangleOldValue.height/2);
                                robot.delay(20);
                                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                                robot.delay(30);
                                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                                robot.delay(500);
                            }
                            fishingProcess.getPrepareFishing().ClickPage(1);
                        }
                        else
                        {
                            System.out.println("Char doesn't has a licence ring");
                        }


                    }

                }

              //  fishingProcess.getPrepareFishing().CheckWantedObjectFromInventory()

            }else
            {
                if(rectWantedShape != null)
                {
                    System.out.println("Licence ring is founded at  = " + rectWantedShape );
                    robot.mouseMoveIndependent(rectWantedShape.x + rectWantedShape.width/2,
                            rectWantedShape.y + rectWantedShape.height/2);
                    robot.delay(20);
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.delay(30);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    robot.delay(500);

                    //Close the Inventory
                    robot.keyPress(KeyEvent.VK_I);
                    robot.delay(50);
                    robot.keyRelease(KeyEvent.VK_I);
                    robot.delay(30);
                }else
                {
                    System.out.println("It is already take off from Inventory");
                }
            }

            fishingProcess.getPrepareFishing().OpenOrCloseInventory(false);

        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }
    public int[] getArrayRGBcharName() {
        return arrayYellowCharName;
    }

    public Rectangle getRectChatAreaNameCoor() {
        return rectChatNameArea;
    }

    public  Rectangle getRectFullChatArea() {
        return rectFullChatArea;
    }

    public  Rectangle getRectMySentencesArea() {
        return rectMySentencesArea;
    }
}
