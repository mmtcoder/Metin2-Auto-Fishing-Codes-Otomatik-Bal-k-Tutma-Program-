
package com.mycompany.autofishing;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author wadwa a wad aw
 */
public class ImageResources
{
 
    protected static GameObjectCoordinates thisGameObjCoord;
     
    private static BufferedImage tempBuffer;
    
    private static final String pathFish = "resources/metin2FishIcon.png";//no need
   // private final String pathFish = "fish7.png";
    private static final String pathYabbie = "resources/yabbie.png";//ok
    private static final String pathPalamut = "resources/palamut.png";//ok
    private static final String pathKurbaga = "resources/kurbaga.png";//ok
    private static final String pathAltin = "resources/altin.png";//ok
    private static final String pathTon = "resources/altinton.png";//ok
    private static final String pathEntryScreen = "resources/entryImage.png";//ok
    private static final String pathCharYellowScr = "resources/character.png";//ok
    private static final String pathCharRedScr = "resources/charred.png";//ok
    private static final String pathCharBlueScr = "resources/charblue.png";//ok
    private static final String pathInventory = "resources/envanter.png";//ok
    private static final String pathDieScreen = "resources/dieScreen.png";//ok
    private static final String pathSaleTitle = "resources/sale.png";//ok
    private static final String pathWorm200 = "resources/worm200.png";//ok
    private static final String pathPageOne = "resources/pageone.png";//ok
    private static final String pathPageTwo = "resources/pagetwo.png";//ok
    private static final String pathPageThree = "resources/pagethree.png";//ok
    private static final String pathPageFour = "resources/pagefour.png";//ok
    private static final String pathFisher = "resources/balikci.png";//no need
    private static final String pathBalikciAraEkran = "resources/balikciaraekran.png";//ok
    private static final String pathfullEnvAndBlkShop = "resources/fullEnvanter.png";//ok
    private static final String pathyabaltkamp = "resources/yabaltkamp.png";//ok
    private static final String pathKampAtesi = "resources/kampatasi.png";//no need
    private static final String pathYereAtma = "resources/yereatmadialog.png";//ok
    private static final String pathFullEnvanterDialog = "resources/fullEnvanter.png";//ok
    private static final String pathpalaIcon = "resources/palamutIcon.png";//ok
    private static final String pathYerYok= "resources/yeryok.png";//ok
    private static final String pathExitChatArea = "resources/exitChat.png";//ok
    private static final String pathCanNotFishHere ="resources/cannotFish.png";//ok
    private static final String pathGameforge = "resources/gameforge.png";//no need
    private static final String pathGameforge2 = "resources/gameforge2.png";//no need
    private static final String pathGameforge3 = "resources/gameforge3.png";//no need
    private static final String pathGameforge4 = "resources/gameforge4.png";//no need
    private static final String pathChatOpen = "resources/chatopen.png";//ok
    private static final String pathLicenseAndEquip = "resources/licence.png";
    private static final String pathInventIsFull = "resources/inventfull.png";


    
    private static int[] inventColorArray;
    private static int[] fisherTitleRGBArray;
    private static int[] arrayRgbEntryScr;
    private static int[] arrayRgbChrtYellowScr;
    private static int[] arrayRgbChrtRedScr;
    private static int[] arrayRgbChrtBlueScr;
    private static int[] arrayRgbChrButton;
    private static int[] arrayRgbKilledScr;
    private static int[] arrayRgbSaleTitle;
    private static int[] arrayRgbSettingBut;
    private static int[] arrayRgbWorm200;
    private static int[] arrayRgbPageOne;
    private static int[] arrayRgbPageTwo;
    private static int[] arrayRgbPageThree;
    private static int[] arrayRgbPageFour;
    private static int[] arrayRgbMiniMapSymb;
    private static int[] arrayRgbBalikci;
    private static int[] arrayRgbBalikciAraEkran;
    private static int[] arrayRgbfullEnvAndBlkShop;
    private static int[] arrayRgbYabbieIcon;
    private static int[] arrayRgbAltinIcon;
    private static int[] arrayRgbPalamutIcon;
    private static int[] arrayRgbKampIcon;
    private static int[] arrayRgbKampAtesi;
    private static int[] arrayRgbYereAtmaEkrani;
    private static int[] arrayRgbFullEnvanterDialog;
    private static int[] arrayRgbEmptySlotPlace;
    private static int[] arrayRgbMetin2Icon;
    private static int[] arrayRgbGameForgeDeskIcon;
    private static int[] arrayRgbGameForgeOynaButton;
    private static int[] arrayRgbGameForgeMaximizeButton;
    private static int[] arrayRgbGameForgeOptionButton;
    private static int[] arrayRgbGameForgeOptionOynaButton;
    private static int[] arrayRgbChatOpen;
    private static int [] arrayRgbLicenceImage;
    private static int [] arrayRgbIsEquipPageOpen;
    private static int [] arrayRgbinventIsFull;
    
    private static int[] fishingAreaArray;
    private static int[] fishArray;
    private static int[] fishYabbie;
    private static int[] fishPalamut;
    private static int[] fishKurbaga;
    private static int[] fishAltinSudak;
    private static int[] fishTon;
    private static int[] fishYerYok;
    private static int[] arrayRgbExitChatArea;

    private static int[] arrayRgbCannotFish;
  
    private static int[] pinkYabbieIndex;
    private static int[] pinkPalamutIndex;
    private static int[] pinkKurbagaIndex;
    private static int[] pinkAltinIndex;
    private static int[] pinkTonIndex;
    private static int[] pinkYerYokIndex;
    private static int[] pinkExitChatAreaIndex;
    private static int[] pinkCannotFishIndex;


    final  int fishOnePixel;
    final int fishSecondPixel;
    final int fishThirdPixel;
    final int fishFourthPixel;
    final int fishFifthPixel;
    final int fishSixthPixel;
    
    final int pinkYabbie;
    final int pinkPalamut;
    final int pinkKurbaga;
    int pinkHamsi;
    final int pinkAltinSudak;
    final int pinkTon;
    final int pinkYerYok;
    final int pinkExitChatArea;
    final int pinkCanNotFish;
    
   protected final static int COLOR_WHITE = 0xffffff;
    
    //Mini haritadaki oyuncunun yonunu gosteren 2 deger
//Determined area RGB values = index = 35 Red Value = 178 Green Value = 177 Blue Value = 162  hex value = 0x00b2b1a2
//Determined area RGB values = index = 36 Red Value = 255 Green Value = 255 Blue Value = 231  hex value = 0x00ffffe7
    
    //Balikcinin mini haritadaki yesil kare gorus disi rengi
    //Determined area RGB values = index = 27 Red Value = 81 Green Value = 154 Blue Value = 62  hex value = 0x00519a3e
    //Balikcinin mini haritadaki yesil kare gorusundeki rengi
    // Determined area RGB values = index = 36 Red Value = 122 Green Value = 227 Blue Value = 93  hex value = 0x007ae35d
    //mini haritadaki yesil balikci karesinin ustune getirdigi zamanki yazan "balikci" yazisi renk degeri
    // balikci mini hartia yazisi index = 190 Red Value = 122 Green Value = 231 Blue Value = 93  hex value = 0x007ae75d
    
    //Balikcinin normal haritadaki "balikci" yazisinin yesil reng degerleri
    //Determined area RGB values = index = 268 Red Value = 122 Green Value = 231 Blue Value = 93  hex value = 0x007ae75d
   
    //Mini haritada gorulen gorus disi npc kirmizi rengi
    //Determined area RGB values = index = 149 Red Value = 157 Green Value = 15 Blue Value = 6  hex value = 0x009d0f06
    //Mini haritada gorulen gorus ici npc kirmizi rengi
    //Determined area RGB values = index = 124 Red Value = 235 Green Value = 22 Blue Value = 9  hex value = 0x00eb1609
    //Mini haritada gorulen gorus disi baska online sari rengi
    //Determined area RGB values = index = 53 Red Value = 170 Green Value = 143 Blue Value = 51  hex value = 0x00aa8f33
    //Determined area RGB values = index = 55 Red Value = 170 Green Value = 141 Blue Value = 51  hex value = 0x00aa8d33
    //Mini haritada gorulen gorus disi baska online sari rengi
    //Determined area RGB values = index = 64 Red Value = 255 Green Value = 215 Blue Value = 76  hex value = 0x00ffd74c
    //Determined area RGB values = index = 66 Red Value = 255 Green Value = 212 Blue Value = 76  hex value = 0x00ffd44c
    //Mini haritada gorulen gorus disi dusman oyuncu 
    //Determined area RGB values = index = 25 Red Value = 159 Green Value = 35 Blue Value = 149  hex value = 0x009f2395
    //Mini haritada gorulen gorus içi dusman oyuncu 
    //Determined area RGB values = index = 43 Red Value = 238 Green Value = 54 Blue Value = 223  hex value = 0x00ee36df
    
    protected final static int MINPINKVALUE =0x009A7F7E;
    protected final static int MAXPINKVALUE =0x00ffc8c9;
    protected final static int PLAYER_YELLOW_COLOR = 0xffd74c;
    protected final static int CHAT_WHITE_COLOR = COLOR_WHITE;
    
    protected final static int MINIMAP_DARKGREEN = 0x00519a3e;
    protected final static int MINIMAP_GREEN = 0x007ae35d;
    protected final static int MINIMAP_BALIKCI_GREEN =0x007ae75d;
    protected final static int  MAP_BALIKCI_GREEN =0x007ae75d;
    protected final static int  MAP_KAMP_ATESİ_GREEN =0x007ae75d;
    
    protected final static int MINIMAP_NPC_DARKRED = 0x009d0f06;
    protected final static int MINIMAP_NPC_OPENRED =0x00eb1609;
    
    protected final static int MINIMAP_PLAYER_DARKYELLOW =0x00aa8f33;
    protected final static int MINIMAP_PLAYER_OPENYELLOW =0x00ffd44c;
    
    protected final static int MINIMAP_PLAYER_DARKPINK = 0x009f2395;
    protected final static int MINIMAP_PLAYER_OPENPINK = 0x00ee36df;
    
    protected final static int MIN_WOOD_VALUE = 0x00191108;
    protected final static int MAX_WOOD_VALUE = 0x00a58b75;


    public ImageResources()
    {

         thisGameObjCoord = new GameObjectCoordinates();
        tempBuffer= readImage(pathFish);
        fishArray = new int[tempBuffer.getWidth()* tempBuffer.getHeight()];
        fishArray = transferBufferImgtoARGB(tempBuffer);
        convertARGBarrayToRGB(fishArray);
      //  printArray("fish result ", fishArray);
        pinkYabbieIndex = new int[110];
        pinkKurbagaIndex = new int[110];
        pinkAltinIndex = new int[110];
        pinkPalamutIndex = new int[110];
        pinkTonIndex = new int[110];
        pinkYerYokIndex = new int[110];
        pinkExitChatAreaIndex = new int[110];
        pinkCannotFishIndex = new int[110];
       
        
        fishYabbie = readBufCnvrtRGB(pathYabbie, thisGameObjCoord.getRectChatArea(), tempBuffer, fishYabbie);
        fishPalamut = readBufCnvrtRGB(pathPalamut, thisGameObjCoord.getRectChatArea(), tempBuffer, fishPalamut);
        fishKurbaga = readBufCnvrtRGB(pathKurbaga, thisGameObjCoord.getRectChatArea(), tempBuffer, fishKurbaga);
        fishAltinSudak = readBufCnvrtRGB(pathAltin, thisGameObjCoord.getRectChatArea(), tempBuffer, fishAltinSudak);
        fishTon = readBufCnvrtRGB(pathTon, thisGameObjCoord.getRectChatArea(), tempBuffer, fishTon);
        fishYerYok = readBufCnvrtRGB(pathYerYok, thisGameObjCoord.getRectChatArea(), tempBuffer, fishYerYok);
        inventColorArray = readBufCnvrtRGB(pathInventory, thisGameObjCoord.getRectInventory(), tempBuffer, inventColorArray);
        fisherTitleRGBArray = readBufCnvrtRGB(pathYabbie, thisGameObjCoord.getRectFishTitle(), tempBuffer, fisherTitleRGBArray);
        arrayRgbEntryScr = readBufCnvrtRGB(pathEntryScreen, thisGameObjCoord.getRectEntryScreen(), tempBuffer, arrayRgbEntryScr);
        arrayRgbChrtYellowScr = readBufCnvrtRGB(pathCharYellowScr, thisGameObjCoord.getRectCharScreen(), tempBuffer, arrayRgbChrtYellowScr);
        arrayRgbChrtBlueScr = readBufCnvrtRGB(pathCharBlueScr, thisGameObjCoord.getRectCharScreen(), tempBuffer, arrayRgbChrtBlueScr);
        arrayRgbChrtRedScr = readBufCnvrtRGB(pathCharRedScr, thisGameObjCoord.getRectCharScreen(), tempBuffer, arrayRgbChrtRedScr);
        arrayRgbChrButton = readBufCnvrtRGB(pathDieScreen, thisGameObjCoord.getRectCharButton(), tempBuffer, arrayRgbChrButton);
        arrayRgbKilledScr = readBufCnvrtRGB(pathDieScreen, thisGameObjCoord.getRectDieScreen(), tempBuffer, arrayRgbKilledScr);
        arrayRgbSaleTitle = readBufCnvrtRGB(pathSaleTitle, thisGameObjCoord.getRectSaleCross(), tempBuffer, arrayRgbSaleTitle);
        arrayRgbSettingBut = readBufCnvrtRGB(pathSaleTitle, thisGameObjCoord.getRectSettingButton(), tempBuffer, arrayRgbSettingBut);
        arrayRgbWorm200 = readBufCnvrtRGB(pathWorm200, thisGameObjCoord.getRectFirstSlotPlace(), tempBuffer, arrayRgbWorm200);
        arrayRgbPageOne = readBufCnvrtRGB(pathPageOne, thisGameObjCoord.getRectPageNumCoordinates(), tempBuffer, arrayRgbPageOne);
        arrayRgbPageTwo = readBufCnvrtRGB(pathPageTwo, thisGameObjCoord.getRectPageNumCoordinates(), tempBuffer, arrayRgbPageTwo);
        arrayRgbPageThree = readBufCnvrtRGB(pathPageThree, thisGameObjCoord.getRectPageNumCoordinates(), tempBuffer, arrayRgbPageThree);
        arrayRgbPageFour = readBufCnvrtRGB(pathPageFour, thisGameObjCoord.getRectPageNumCoordinates(), tempBuffer, arrayRgbPageFour);
        arrayRgbMiniMapSymb = readBufCnvrtRGB(pathDieScreen, thisGameObjCoord.getRectMiniMapSymbolCoor(), tempBuffer, arrayRgbMiniMapSymb);
        arrayRgbBalikci = readBufCnvrtRGB(pathFisher, thisGameObjCoord.getRectFisherSample(), tempBuffer, arrayRgbBalikci);
        arrayRgbBalikciAraEkran = readBufCnvrtRGB(pathBalikciAraEkran, thisGameObjCoord.getRectFisherOptionsPage(), tempBuffer, arrayRgbBalikciAraEkran);
        arrayRgbfullEnvAndBlkShop = readBufCnvrtRGB(pathfullEnvAndBlkShop, thisGameObjCoord.getRectFisherShopPage(), tempBuffer, arrayRgbfullEnvAndBlkShop);
        arrayRgbYabbieIcon = readBufCnvrtRGB(pathyabaltkamp, thisGameObjCoord.getRectYabbieIcon(), tempBuffer, arrayRgbYabbieIcon);
        arrayRgbAltinIcon = readBufCnvrtRGB(pathyabaltkamp, thisGameObjCoord.getRectAltinIcon(), tempBuffer, arrayRgbAltinIcon);
        arrayRgbPalamutIcon= readBufCnvrtRGB(pathpalaIcon, thisGameObjCoord.getRectKampIcon(), tempBuffer, arrayRgbPalamutIcon);
        arrayRgbKampIcon = readBufCnvrtRGB(pathyabaltkamp, thisGameObjCoord.getRectKampIcon(), tempBuffer, arrayRgbKampIcon);
        arrayRgbKampAtesi = readBufCnvrtRGB(pathKampAtesi, new Rectangle(0,0,53,13), tempBuffer, arrayRgbKampAtesi);
        arrayRgbYereAtmaEkrani = readBufCnvrtRGB(pathYereAtma, thisGameObjCoord.getRectYereAtmaAlgilama(), tempBuffer, arrayRgbYereAtmaEkrani);
        arrayRgbFullEnvanterDialog = readBufCnvrtRGB(pathFullEnvanterDialog, thisGameObjCoord.getRectYereAtmaAlgilama(), tempBuffer, arrayRgbFullEnvanterDialog);
        arrayRgbEmptySlotPlace = readBufCnvrtRGB(pathyabaltkamp, thisGameObjCoord.getRectSkillSlotSecondPlace(), tempBuffer, arrayRgbEmptySlotPlace);
        arrayRgbExitChatArea = readBufCnvrtRGB(pathExitChatArea, thisGameObjCoord.getRectExitChatArea(), tempBuffer, arrayRgbExitChatArea);
        arrayRgbCannotFish = readBufCnvrtRGB(pathCanNotFishHere, thisGameObjCoord.getRectChatArea(),tempBuffer,arrayRgbCannotFish);
        arrayRgbMetin2Icon = readBufCnvrtRGB(pathEntryScreen,thisGameObjCoord.getRectMetin2Icon(),tempBuffer,arrayRgbMetin2Icon);
        arrayRgbGameForgeDeskIcon = readBufCnvrtRGB(pathGameforge,thisGameObjCoord.getRectGameForgeDesktopIcon(),tempBuffer,arrayRgbGameForgeDeskIcon);
        arrayRgbGameForgeOynaButton = readBufCnvrtRGB(pathGameforge2,thisGameObjCoord.getRectGameForgePlayButton(),tempBuffer,arrayRgbGameForgeOynaButton);
        arrayRgbGameForgeMaximizeButton = readBufCnvrtRGB(pathGameforge3,thisGameObjCoord.getRectGameForgeMaximizeIcon(),tempBuffer,arrayRgbGameForgeMaximizeButton);
        arrayRgbGameForgeOptionButton = readBufCnvrtRGB(pathGameforge2,thisGameObjCoord.getRectGameForgeOptionsButton(),tempBuffer,arrayRgbGameForgeOptionButton);
        arrayRgbGameForgeOptionOynaButton = readBufCnvrtRGB(pathGameforge4,thisGameObjCoord.getRectGameForgeOptionOynaButton(),tempBuffer,arrayRgbGameForgeOptionOynaButton);
        arrayRgbChatOpen = readBufCnvrtRGB(pathChatOpen,thisGameObjCoord.getRectChatIsActive(),tempBuffer,arrayRgbChatOpen);
        arrayRgbLicenceImage = readBufCnvrtRGB(pathLicenseAndEquip,thisGameObjCoord.getRectLicenseImgCoord(),tempBuffer,arrayRgbLicenceImage);
        arrayRgbIsEquipPageOpen = readBufCnvrtRGB(pathLicenseAndEquip,thisGameObjCoord.getRectEquipmentArea(),tempBuffer,arrayRgbIsEquipPageOpen);
        arrayRgbinventIsFull = readBufCnvrtRGB(pathFullEnvanterDialog,thisGameObjCoord.getRectInventIsFullPanel(),tempBuffer,arrayRgbinventIsFull);

//printArray("test = ", arrayRgbWorm200);
        //printArrayAsRgb("worm Values = ", arrayRgbWorm200);
        
        pinkYabbie = calculatePinkValue(fishYabbie,pinkYabbieIndex);
        pinkPalamut = calculatePinkValue(fishPalamut,pinkPalamutIndex);
        pinkKurbaga = calculatePinkValue(fishKurbaga,pinkKurbagaIndex);
        pinkAltinSudak = calculatePinkValue(fishAltinSudak,pinkAltinIndex);
        pinkTon = calculatePinkValue(fishTon, pinkTonIndex);
        pinkYerYok = calculatePinkValue(fishYerYok, pinkYerYokIndex);
        pinkExitChatArea = calculatePinkValue(arrayRgbExitChatArea, pinkExitChatAreaIndex);
        pinkCanNotFish = calculatePinkValue(arrayRgbCannotFish,pinkCannotFishIndex);
        
        
        
        
        fishOnePixel = 3430266; //top
        fishSecondPixel = 3824762; //bottom
        fishSixthPixel = 3627387 ;//middle
        fishThirdPixel = 3299708; //top
        fishFourthPixel =3890296; //bottom
        fishFifthPixel = 3824763; //middle

        System.out.println("ImageResources is written");

    }

     public int[] transferBufferImgtoARGB(BufferedImage bufImg) {
        int[] array;
        array = bufImg.getRGB(0, 0, bufImg.getWidth(), bufImg.getHeight(),
                null, 0, bufImg.getWidth());
        return array;
    }

    public BufferedImage transferRGBtoBufferedImage(int [] sourceRGBarray, Rectangle belongToSourceArray)
    {
        int []arrayARGB = convertRGBarrayToARGB(sourceRGBarray);
       BufferedImage tempBuf = new BufferedImage(belongToSourceArray.width,belongToSourceArray.height,
               BufferedImage.TYPE_INT_ARGB);
       tempBuf.setRGB(0,0,belongToSourceArray.width,belongToSourceArray.height,
               arrayARGB,0,belongToSourceArray.width);
       return tempBuf;
    }

    public void convertARGBarrayToRGB(int[] array)
    {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] & 0x00ffffff;
        }
    }
    private int [] convertRGBarrayToARGB(int[] sourceRGBarray)
    {
        int [] tempArray = sourceRGBarray;
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = tempArray[i] | 0xff000000;
        }
        return  tempArray;
    }
    public void IOwriteImage(String destPath,Rectangle rect)
    {
        try {
            Robot robot = new Robot();
           // BufferedImage buf = ImageIO.read(new File(sourcePath));
            BufferedImage buf = robot.createScreenCapture(rect);
            //buf = buf.getSubimage(rect.x,rect.y,rect.width,rect.height);
          ImageIO.write(buf,"png" , new File(destPath));
                     
        } catch (AWTException | IOException ex) 
        {
            System.out.println(ex);
        }
          
    }
    
    
    public int [] readBufCnvrtRGB(String path,Rectangle rect,BufferedImage buf,int [] array)
    {
        buf = readImage(path);
       // buf= buf.getSubimage(rect.x+30, rect.y +145, rect.width,rect.height);
       buf= buf.getSubimage(rect.x, rect.y , rect.width,rect.height);
       // labelFishingArea.setIcon(new ImageIcon(buf));
        array = new int[buf.getWidth()*buf.getHeight()];
        //System.out.println("buf.width = "+ buf.getWidth() + "height = " + buf.getHeight());
        array = transferBufferImgtoARGB(buf);
        convertARGBarrayToRGB(array);
        return array;
    }
    
    public BufferedImage readImage(String path) {

        //This try for running as exe or jar file 
        try {

             return ImageIO.read((getClass().getClassLoader().getResource(path)));
            
        } catch (IOException |IllegalArgumentException |NullPointerException ex) {
            
            try
            {
                return ImageIO.read(new File(path));
                 
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
                return null;
            }
    }
     
     public BufferedImage getBufferSubImage(BufferedImage sourceImage,Rectangle rectangle)
     {
         return sourceImage.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
     }
     
      public int calculatePinkValue(int [] rgbArray,int [] pinkArrayIndex)
    {
        long starttime = System.currentTimeMillis();
        int pinkCount =0;
        int pinkIndex =0;
        int ColorRed =0;
        int ColorGreen =0;
        int ColorBlue =0;
       
        //int maxValue =0;
        //int maxIndex =0;
   
    
        for (int i = 0; i < rgbArray.length; i++) {
            
            
            if(rgbArray[i] > MINPINKVALUE && rgbArray[i] < MAXPINKVALUE)
            {
               
            ColorRed = (rgbArray[i] & 0x00ff0000) >> 16;
            ColorGreen = (rgbArray[i] & 0x0000ff00) >> 8;
            ColorBlue = rgbArray[i] & 0x000000ff;
            
           // System.out.println("count = "+ count +" red = "+ ColorRed + " green = " + ColorGreen + " blue = " + ColorBlue);
            if(Math.abs(ColorGreen - ColorBlue) < 6  && Math.abs(ColorGreen - ColorRed) >40
                    && Math.abs(ColorGreen - ColorRed) <59)
             {
                 pinkArrayIndex[pinkIndex++] =i;
                   pinkCount++;        
             }
              //pinkCount++;  
            }
           /*if(maxValue < rgbArray[i])
           {
               maxValue = rgbArray[i];
               maxIndex = i;
           }*/
          
//10059645,10387840,10125439,10125182
        }
      
        //System.out.println("max value = " + String.format("0x%08x", maxValue)+ " index = " + maxIndex);
        //System.out.println("millis value = " +(  System.currentTimeMillis()- starttime));
        return pinkCount;
    }
      
       public int[] getPinkYabbieIndex() {
        return pinkYabbieIndex;
    }

    public int[] getPinkPalamutIndex() {
        return pinkPalamutIndex;
    }

    public int[] getPinkKurbagaIndex() {
        return pinkKurbagaIndex;
    }

    public int[] getPinkAltinIndex() {
        return pinkAltinIndex;
    }

    public int[] getPinkTonIndex() {
        return pinkTonIndex;
    }

    public int[] getPinkYerYokIndex() {
        return pinkYerYokIndex;
    }

    public int[] getPinkCannotFishIndex() { return pinkCannotFishIndex; }

    public int getPinkYabbie() {
        return pinkYabbie;
    }

    public int getPinkPalamut() {
        return pinkPalamut;
    }

    public int getPinkKurbaga() {
        return pinkKurbaga;
    }

    public int getPinkAltinSudak() {
        return pinkAltinSudak;
    }
    
    public int getPinkTon() {
        return pinkTon;
    }

    public int getPinkYerYok() {
        return pinkYerYok;
    }

    public int getPinkCanNotFish() {
        return pinkCanNotFish;
    }

    public int[] getFishYerYok() {
        return fishYerYok;
    }

    public int getFishOnePixel() {
        return fishOnePixel;
    }

    public int getFishSecondPixel() {
        return fishSecondPixel;
    }

    public int getFishThirdPixel() {
        return fishThirdPixel;
    }

    public int getFishFourthPixel() {
        return fishFourthPixel;
    }

    public int getFishFifthPixel() {
        return fishFifthPixel;
    }

    public int getFishSixthPixel() {
        return fishSixthPixel;
    }
     
    
    public int[] getInventColorArray()
    {
        return inventColorArray;
    }

    public int[] getFisherTitleRGBArray() {
        return fisherTitleRGBArray;
    }

    public int[] getArrayRgbYelChrtScr() {
        return arrayRgbChrtYellowScr;
    }

    public int[] getArrayRgbChrtRedScr() {
        return arrayRgbChrtRedScr;
    }

    public int[] getArrayRgbChrtBlueScr() {
        return arrayRgbChrtBlueScr;
    }

    public int[] getArrayRgbCharButton() {
        return arrayRgbChrButton;
    }
    
    public int[] getArrayRgbEntryScr() {
        return arrayRgbEntryScr;
    }

    public int[] getArrayRgbKilledScr() {
        return arrayRgbKilledScr;
    }

    public int[] getArrayRgbSaleTitle() {
        return arrayRgbSaleTitle;
    }

    public int[] getArrayRgbSettingBut() {
        return arrayRgbSettingBut;
    }

    public int[] getArrayRgbWorm200() {
        return arrayRgbWorm200;
    }

    public int[] getArrayRgbPageOne() {
        return arrayRgbPageOne;
    }

    public int[] getArrayRgbPageTwo() {
        return arrayRgbPageTwo;
    }

    public int[] getArrayRgbPageThree() {
        return arrayRgbPageThree;
    }

    public int[] getArrayRgbPageFour() {
        return arrayRgbPageFour;
    }

    public int[] getArrayRgbMiniMapSymb() {
        return arrayRgbMiniMapSymb;
    }

    public int[] getArrayRgbBalikci() {
        return arrayRgbBalikci;
    }

    public int[] getArrayRgbBalikciAraEkran() {
        return arrayRgbBalikciAraEkran;
    }

    public int[] getArrayRgbfullEnvAndBlkShop() {
        return arrayRgbfullEnvAndBlkShop;
    }

    public int[] getArrayRgbYabbieIcon() {
        return arrayRgbYabbieIcon;
    }

    public int[] getArrayRgbAltinIcon() {
        return arrayRgbAltinIcon;
    }

    public int[] getArrayRgbPalamutIcon() {
        return arrayRgbPalamutIcon;
    }

    public int[] getArrayRgbKampIcon() {
        return arrayRgbKampIcon;
    }

    public int[] getArrayRgbKampAtesi() {
        return arrayRgbKampAtesi;
    }
    
     public int[] getArrayRgbYereAtmaEkrani() {
        return arrayRgbYereAtmaEkrani;
    }

    public int[] getArrayRgbMetin2Icon() {
        return arrayRgbMetin2Icon;
    }

    public int[] getArrayRgbCannotFish() {
        return arrayRgbCannotFish;
    }

    public int[] getArrayRgbGameForgeDeskIcon() {
        return arrayRgbGameForgeDeskIcon;
    }

    public int[] getArrayRgbGameForgeOynaButton() {
        return arrayRgbGameForgeOynaButton;
    }

    public int[] getArrayRgbGameForgeMaximizeButton() {
        return arrayRgbGameForgeMaximizeButton;
    }

    public int[] getArrayRgbGameForgeOptionButton() {
        return arrayRgbGameForgeOptionButton;
    }

    public int[] getArrayRgbGameForgeOptionOynaButton() {
        return arrayRgbGameForgeOptionOynaButton;
    }

    public int[] getArrayRgbFullEnvanterDialog() {
        return arrayRgbFullEnvanterDialog;
    }

    public int[] getArrayRgbEmptySlotPlace() {
        return arrayRgbEmptySlotPlace;
    }

    public int[] getArrayRgbChatOpen() {  return arrayRgbChatOpen;   }

    public int[] getArrayRgbLicenceImage() {
        return arrayRgbLicenceImage;
    }

    public int[] getArrayRgbIsEquipPageOpen() {
        return arrayRgbIsEquipPageOpen;
    }

    public int[] getPinkExitChatAreaIndex() {
        return pinkExitChatAreaIndex;
    }

    public int[] getArrayRgbinventIsFull() {
        return arrayRgbinventIsFull;
    }

    public int getPinkExitChatArea() {
        return pinkExitChatArea;
    }
     
    public String getPathPageOne() {
        return pathPageOne;
    }

    public String getPathPageTwo() {
        return pathPageTwo;
    }

    public String getPathPageThree() {
        return pathPageThree;
    }

    public String getPathPageFour() {
        return pathPageFour;
    }

    
    public String getPathAltinSudak()
    {
        return pathAltin;
    }
    
    public String getPathYabbie() {
        return pathYabbie;
    }

    public String getPathWorm200() {
        return pathWorm200;
    }

    public String getPathFisher() {
        return pathFisher;
    }

    public String getPathDieScreen() {
        return pathDieScreen;
    }

    public String getPathCharacterSc() {
        return pathCharYellowScr;
    }

    public String getPathBalikciAraEkran() {
        return pathBalikciAraEkran;
    }

    public String getPathyabaltkamp() {
        return pathyabaltkamp;
    }

    public String getPathExitChatArea() {
        return pathExitChatArea;
    }

    public String getPathGameforge() {
        return pathGameforge;
    }

    public String getPathGameforge2() {
        return pathGameforge2;
    }

    public String getPathGameforge4() {
        return pathGameforge4;
    }

    public String getPathGameforge3() {
        return pathGameforge3;
    }

    public int[] getArrayRgbExitChatArea() {
        return arrayRgbExitChatArea;
    }

   /* public BufferedImage getBufferBalikci() {
        return bufferBalikci;
    }*/
}
