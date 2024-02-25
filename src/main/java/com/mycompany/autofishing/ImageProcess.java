
package com.mycompany.autofishing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;


public class ImageProcess extends ImageResources
{
    public final int SENSIBILTY_LOW = 3;
    public final int SENSIBILTY_MED = 2;
    public final int SENSIBILTY_HIGH = 1;

    //I created these variables there in order to make algorithm more fast
    int [] arrayFieldColorRed = new int[2];
    int [] arrayFieldColorGreen =  new int[2];
    int [] arrayFieldColorBlue =  new int[2];

    protected Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    
//Determined area RGB values = index = 69 Red Value = 199 Green Value = 199 Blue Value = 199  hex value = 0x00c7c7c7
    public ImageProcess()
    {
        super();

       // printArray("Random array", takeScreenShotReturnRGBarray(new Rectangle(23,42,50,30)));
    }

   
     public boolean  DetectMiniMapColors(int [] comparableArray)
     {
         int [] arrayMiniMapColors = new int [4];
         int [] arrayColorRed = new int[arrayMiniMapColors.length +1];
         int [] arrayColorGreen =  new int[arrayMiniMapColors.length +1];
         int [] arrayColorBlue =  new int[arrayMiniMapColors.length +1];
        
         int comparableArrayIndex =arrayMiniMapColors.length;
         
         int equalityVar =0;
         
        
         
         arrayMiniMapColors[0] = MINIMAP_PLAYER_DARKPINK;
         arrayMiniMapColors[1] = MINIMAP_PLAYER_DARKYELLOW;
         arrayMiniMapColors[2] = MINIMAP_PLAYER_OPENPINK;
         arrayMiniMapColors[3] = MINIMAP_PLAYER_OPENYELLOW;
         
         for (int k = 0; k < arrayMiniMapColors.length; k++) 
         {
          arrayColorRed[k] = (arrayMiniMapColors[k] & 0x00ff0000) >> 16;
          arrayColorGreen[k] = (arrayMiniMapColors[k] & 0x0000ff00) >> 8;
          arrayColorBlue[k] = arrayMiniMapColors[k] & 0x000000ff;
         }
         for (int colorIndexes = 0; colorIndexes < arrayMiniMapColors.length; colorIndexes++) 
           {
              for (int i = 0; i < comparableArray.length; i++) 
         {

              arrayColorRed[comparableArrayIndex] = (comparableArray[i] & 0x00ff0000) >> 16;
              arrayColorGreen[comparableArrayIndex] = (comparableArray[i] & 0x0000ff00) >> 8;
              arrayColorBlue[comparableArrayIndex] = comparableArray[i] & 0x000000ff;
              
              if(Math.abs(arrayColorRed[colorIndexes] - arrayColorRed[comparableArrayIndex]) < 2 &&
                     Math.abs(arrayColorGreen[colorIndexes] - arrayColorGreen[comparableArrayIndex]) <2 
                     && Math.abs(arrayColorBlue[colorIndexes] - arrayColorBlue[comparableArrayIndex]) < 2)
             {
               equalityVar++;
                 //System.out.println("equalityVar = " + equalityVar);
               if(equalityVar >0)
               {
                   if(colorIndexes == 0 || colorIndexes == 2)
                   {
                       GameStatus.isEnemyDetected = true;
                   }
                   else
                   {
                       GameStatus.isEnemyDetected = false;
                   }
                   //System.out.println("detected indexes = " + colorIndexes);
                   return true;
               }
               
            }
              
         }
              equalityVar =0;
           }
         
        /* for (int i = 0; i < comparableArray.length; i++) 
         {

              arrayColorRed[comparableArrayIndex] = (comparableArray[i] & 0x00ff0000) >> 16;
              arrayColorGreen[comparableArrayIndex] = (comparableArray[i] & 0x0000ff00) >> 8;
              arrayColorBlue[comparableArrayIndex] = comparableArray[i] & 0x000000ff;
                     
              for (int colorIndexes = 0; colorIndexes < arrayMiniMapColors.length; colorIndexes++) 
              {
                 if(Math.abs(arrayColorRed[colorIndexes] - arrayColorRed[comparableArrayIndex]) < 1 &&
                     arrayColorGreen[colorIndexes] - arrayColorGreen[comparableArrayIndex] <1 
                     && arrayColorBlue[colorIndexes] - arrayColorBlue[comparableArrayIndex] < 1)
             {
               equalityVar++;
               if(equalityVar >15)
               {
                   return true;
               }
               break;
            }
                 
              }
           
           
             }*/
          
         //System.out.println("equality variable " + equalityVar);
         return false;
         
     }
    
      public int [] RecordWantedColorIndex(int wantedColor , int [] comparableArray)
    {
        if(comparableArray == null)
        {
            System.out.println("RecordWantedColor.. method comparable array is NULL");
            return null;
        }
            int colorRed =0;
            int colorGreen =0;
            int colorBlue =0;
            
            int equalityIndex =0;
            int [] equalityArray = null;
           
              colorRed = (wantedColor & 0x00ff0000) >> 16;
              colorGreen = (wantedColor & 0x0000ff00) >> 8;
              colorBlue = wantedColor & 0x000000ff;
              
              for (int i = 0; i < comparableArray.length; i++) 
              {
             if(Math.abs(colorRed - ((comparableArray[i] & 0x00ff0000 ) >> 16 )) < 3 &&
                   Math.abs(colorGreen - ((comparableArray[i] & 0x0000ff00 ) >> 8 )) < 3 &&
                     Math.abs(colorBlue - ((comparableArray[i] & 0x000000ff ))) < 3)
                     {
                         equalityIndex++;
                     }
              }
              
              equalityArray = new int[equalityIndex];
              equalityIndex = 0;
               for (int i = 0; i < comparableArray.length; i++) 
              {
             if(Math.abs(colorRed - ((comparableArray[i] & 0x00ff0000 ) >> 16 )) < 3 &&
                   Math.abs(colorGreen - ((comparableArray[i] & 0x0000ff00 ) >> 8 )) < 3 &&
                     Math.abs(colorBlue - ((comparableArray[i] & 0x000000ff ))) < 3)
                     {
                         equalityArray[equalityIndex++] = i;
                     }
              }
              
             return equalityArray;
    }
    public boolean [] RecordWantedColorAsBool(int wantedColor, int [] comparableArray)
    {
        if(comparableArray == null)
        {
            System.out.println("RecordWantedColor.. method comparable array is NULL");
            return null;
        }
        int colorRed =0;
        int colorGreen =0;
        int colorBlue =0;

        boolean [] equalityArray = new boolean[comparableArray.length];

        colorRed = (wantedColor & 0x00ff0000) >> 16;
        colorGreen = (wantedColor & 0x0000ff00) >> 8;
        colorBlue = wantedColor & 0x000000ff;

        for (int i = 0; i < comparableArray.length; i++)
        {
            if(Math.abs(colorRed - ((comparableArray[i] & 0x00ff0000 ) >> 16 )) < 3 &&
                    Math.abs(colorGreen - ((comparableArray[i] & 0x0000ff00 ) >> 8 )) < 3 &&
                    Math.abs(colorBlue - ((comparableArray[i] & 0x000000ff ))) < 3)
            {
                equalityArray[i] = true;
            }else
            {
                equalityArray[i] = false;
            }
        }
        return  equalityArray;
    }
      
      public int CountWantedColorPixel(int wantedColor , int [] comparableArray)
      {
          int colorRed =0;
            int colorGreen =0;
            int colorBlue =0;
            
            int equalityIndex =0;
           
           
              colorRed = (wantedColor & 0x00ff0000) >> 16;
              colorGreen = (wantedColor & 0x0000ff00) >> 8;
              colorBlue = wantedColor & 0x000000ff;
              
              for (int i = 0; i < comparableArray.length; i++) 
              {
             if(Math.abs(colorRed - ((comparableArray[i] & 0x00ff0000 ) >> 16 )) < 3 && 
                   Math.abs(colorGreen - ((comparableArray[i] & 0x0000ff00 ) >> 8 )) < 3 && 
                     Math.abs(colorBlue - ((comparableArray[i] & 0x000000ff ))) < 3)
                     {
                         equalityIndex++;
                     }
              }

             return equalityIndex;
      }
    public boolean comparePinkEquality(int [] sourceArray,int[] compareArray,int pinkFishValue)
    {
        
        int boolEquality =0;
        int value =0;
        
        for (int i = 0; i < sourceArray.length; i++) 
        {
            value= sourceArray[i];           
            if(value == compareArray[i] && value !=0)
            {
               boolEquality++;
            }
        }
        //System.out.println("bool Equality value = " + boolEquality);
        return boolEquality > pinkFishValue -1;
       
    }
   
    public void printArray(String name,int[] array)
    {
        if( array != null &&array.length > 0)
        {
           /*  if(array[0] == 1)
        {
            System.out.println("Fish is detected in TOP SIDE");
        }else if(array[0] == 2)
        {
              System.out.println("Fish is detected in MIDDLE SIDE");
        }
        else if(array[0] == 3)
        {
            System.out.println("Fish is detected in BOT SIDE");
        }*/
             for (int i = 0; i < array.length; i++) 
        {
            System.out.println(name +" index = "+ i + "  hex value = "+ String.format("0x%06x", array[i])
            + " dec value = " + array[i]);    
        }
        }
        else
        {
            System.out.println("printArray param array is null");
        }
       
        
    }

     /*
    Brief : This function take a screen shot of the determined
    rectangle area and return a new BufferedImage 
    return : BufferedImages
     */
    public BufferedImage getBufImageScannedArea(Rectangle rect) {

        try {
            Robot robot = new Robot();
            BufferedImage bufImage = robot.createScreenCapture(rect);
            //  System.out.println("bufImage width and height = " + bufImage.getWidth() + bufImage.getHeight());
            return bufImage;
            //return  bufImage.getSubimage((int)rect.getX(),(int)rect.getY(),
            //         (int)rect.getWidth(),(int)rect.getHeight());

        } catch (AWTException ex) {
            System.out.println(ex);
            return null;
        }
        catch (NullPointerException e)
        {
            System.out.println("Rect param is null");
            return  null;
        }

    }
   
    public boolean CompareTwoArrayAdvanced(int [] sourceArray, int [] comparableArray, int sensiblityLevel)
    {
        int equalityNum =0;
      //System.out.println("source length = " + sourceArray.length);
        //     System.out.println("compparable length = " + comparableArray.length);
        if(comparableArray != null && comparableArray.length > 0)
        {
         int [] arrayColorRed = new int[2];
         int [] arrayColorGreen =  new int[2];
         int [] arrayColorBlue =  new int[2];
        
         
         if(sourceArray.length > comparableArray.length)
         {
            
             
             return false;
         }
         else
         {
              for (int i = 0; i < sourceArray.length; i++)
             {
                 for (int index = 0; index < 2; index++) 
                 {
                     if(index == 0)
                     {
                     arrayColorRed[index] = (sourceArray[i] & 0x00ff0000) >> 16;
                     arrayColorGreen[index] = (sourceArray[i] & 0x0000ff00) >> 8;
                     arrayColorBlue[index] = sourceArray[i] & 0x000000ff;
                     }
                     else
                     {
                     arrayColorRed[index] = (comparableArray[i] & 0x00ff0000) >> 16;
                     arrayColorGreen[index] = (comparableArray[i] & 0x0000ff00) >> 8;
                     arrayColorBlue[index] = comparableArray[i] & 0x000000ff;
                     }
                    
                 }
                 
                 if(Math.abs(arrayColorRed[0] - arrayColorRed[1])< 4 && Math.abs(arrayColorGreen[0] -arrayColorGreen[1] ) < 4
                         && Math.abs(arrayColorBlue[0] - arrayColorBlue[1]) <  4)
                 {
                    // System.out.println("22array color 0 = " + arrayColorRed[0] + " 22 array color 1 = " + arrayColorRed[1]);
                     // System.out.println("index num =  " + i + " return false ");
                     equalityNum++;
                 }
             }
              
         }
        }
        //System.out.println("comparable array length = " + comparableArray.length + "equality var = " + equalityNum);
        return equalityNum > ((sourceArray.length/sensiblityLevel) -((5* sourceArray.length)/100)) ;
    }

    public boolean CompareTwoRgbIntAdvanced(int source,int comparable)
    {


                    for (int index = 0; index < 2; index++)
                    {
                        if(index == 0)
                        {
                            arrayFieldColorRed[index] = (source & 0x00ff0000) >> 16;
                            arrayFieldColorGreen[index] = (source & 0x0000ff00) >> 8;
                            arrayFieldColorBlue[index] = source & 0x000000ff;
                        }
                        else
                        {
                            arrayFieldColorRed[index] = (comparable & 0x00ff0000) >> 16;
                            arrayFieldColorGreen[index] = (comparable & 0x0000ff00) >> 8;
                            arrayFieldColorBlue[index] = comparable & 0x000000ff;
                        }

                    }

                    if(Math.abs(arrayFieldColorRed[0] - arrayFieldColorRed[1])< 3
                            && Math.abs(arrayFieldColorGreen[0] -arrayFieldColorGreen[1] ) < 3
                            && Math.abs(arrayFieldColorBlue[0] - arrayFieldColorBlue[1]) <  3)
                    {
                        return true;
                    }
                    return false;

    }
    public boolean compareTwoArraySimpleAlgorithm(int [] comparableArray, int [] sourceArray , int sensibilityLevel)
    {
        int equalityVar =0;
        if(comparableArray == null || sourceArray == null)
        {
            System.out.println("compareTwoArratSimple.. method arrays or array is null");
            return false;
        }
        //System.out.println("SourceArray length = " + sourceArray.length);
        // System.out.println("Comparable length = " + comparableArray.length);
        if(sourceArray.length > comparableArray.length)
        {
            for (int i = 0; i < comparableArray.length; i++)
            {
                if(comparableArray[i] == sourceArray[i])
                {
                    equalityVar++;
                }
                //System.out.println("equality variable = " + equalityVar);
                if(equalityVar > (sourceArray.length/sensibilityLevel) -2)
                {
                    // System.out.println("equality variable = " + equalityVar);
                    return true;
                }

            }
        }else
        {
            for (int i = 0; i < sourceArray.length; i++)
            {
                if(comparableArray[i] == sourceArray[i])
                {
                    equalityVar++;
                }
                //System.out.println("equality variable = " + equalityVar);
                if(equalityVar > (sourceArray.length/sensibilityLevel) -2)
                {
                    // System.out.println("equality variable = " + equalityVar);
                    return true;
                }

            }
        }

        //System.out.println("equality variable = " + equalityVar);
        return false;
    }
    public boolean compareTwoArrayQuickly(int [] source, int [] comparable)
    {
        int equalityVar =0;
        if(comparable == null || source == null)
        {
            System.out.println("compareTwoArrayQuickly.. method arrays or array is null");
            return false;
        }
        if(source.length > comparable.length)
        {
            for(int i =0; i < comparable.length; i++)
            {
                if(source[i] != comparable[i])
                {
                    return false;
                }
            }
        }
        else
        {
            for(int i =0; i < source.length; i++)
            {
                if(source[i] != comparable[i])
                {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkRgbArrayFromSpecifiedArea(int wantedColor, BufferedImage sourceBuffer, Rectangle rectScannableArea)
    {
        int []comparableRgbArray;
        //comparableRgbArray = takeScreenShotReturnRGBarray(rectScannableArea);
        comparableRgbArray = takeSSRtrnRgbForImgProcessClss(rectScannableArea);

        int []arraySourceRgb;
        arraySourceRgb = transferBufferImgtoARGB(sourceBuffer);
        convertARGBarrayToRGB(arraySourceRgb);

        boolean [] arraySourceColor = RecordWantedColorAsBool(CHAT_WHITE_COLOR,arraySourceRgb);
        boolean [] arrayComparableColor = RecordWantedColorAsBool(CHAT_WHITE_COLOR,comparableRgbArray);

     /*  System.out.println("arraySourceRgb length = " + arraySourceRgb.length +
              " arraySourceColor length = " + arraySourceColor.length +
               " sourceBuffer width = " + sourceBuffer.getWidth() +
               " sourceBuffer height = " + sourceBuffer.getHeight());*/
        int firstCoordCounter =0;
        int secondCoordCounter =0;



        if(wantedColor != 0)
        {
            for (int firstYpos =0; firstYpos <rectScannableArea.height/2; firstYpos++ )
            {
                for (int firstXPos = 0; firstXPos < rectScannableArea.width; firstXPos++)
                {
                    if(CompareTwoRgbIntAdvanced(CHAT_WHITE_COLOR,comparableRgbArray[firstXPos + firstCoordCounter]))
                    {
                        int counterSourceColor =0;
                        int firstTrueIndex =0;
                        boolean sourceChatIsMatched = true;

                        //Find first White true index for synchronizing comparable and source pixel value reading
                        for(int findtrue =0 ; findtrue < arraySourceColor.length; findtrue ++)
                        {
                            if(arraySourceColor[findtrue] == true)
                            {
                                firstTrueIndex = findtrue;
                                break;
                            }
                        }
                        int secondPixelStarter = firstXPos - firstTrueIndex;
                        //System.out.println("secondPixelStarter value = " + secondPixelStarter);
                        //start from sourceWantedColor  firstXPos - first index
                       for(int secondYpos = firstYpos ; secondYpos < firstYpos + sourceBuffer.getHeight();secondYpos++)
                       {
                           if(secondPixelStarter < 0)
                           {
                               sourceChatIsMatched = false;
                               break;
                           }
                           for(int secondXpos = secondPixelStarter; secondXpos < secondPixelStarter + sourceBuffer.getWidth() ;secondXpos++)
                           {

                              /* try {
                                   Robot robot = new Robot();
                                   AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(robot.createScreenCapture(
                                           new Rectangle(secondXpos,secondYpos,sourceBuffer.getWidth(),sourceBuffer.getHeight())
                                   )));
                               } catch (AWTException e) {
                                   throw new RuntimeException(e);
                               }*/
                               //System.out.println("secondYPos val = " + secondYpos + " secondXPos Val = " + secondXpos);
                              // System.out.println("arrayComparableColor index value = " + (secondXpos + firstCoordCounter + secondCoordCounter ));
                              // System.out.println(" arraySourceColor index value = "  + ((secondXpos - secondPixelStarter) + counterSourceColor));
                                if(arrayComparableColor[secondXpos + firstCoordCounter + secondCoordCounter] !=
                                arraySourceColor[(secondXpos - secondPixelStarter) + counterSourceColor])
                                {
                                    sourceChatIsMatched = false;
                                    firstXPos += sourceBuffer.getWidth();
                                    secondYpos += sourceBuffer.getHeight();
                                    break;
                                }
                               //AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(transferRGBtoBufferedImage(comparableRgbArray,
                                    //   new Rectangle((secondXpos - secondPixelStarter),secondYpos,sourceBuffer.getWidth(),sourceBuffer.getHeight()))));
                           }
                           secondCoordCounter += rectScannableArea.width;
                           counterSourceColor += sourceBuffer.getWidth();
                       }
                       if(sourceChatIsMatched)return true;
                       secondCoordCounter =0;
                    }
                }
                firstCoordCounter += rectScannableArea.width;
            }

                }
        return false;

    }
    public Rectangle rectFromBufferImage(BufferedImage bufferedImage)
    {
        return  new Rectangle(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
    }
    public Rectangle findWantedImageFromScreen(int [] sourceRGBarray,Rectangle rectSourceRgbArray,Rectangle scannableArea)
    {
        int []targetRgbArray;
        int firstCoordCounter =0;
        Rectangle rectScannableArea;
        //printArray("sourceArray = " ,sourceRGBarray);
        if(scannableArea != null)
        {
            rectScannableArea = scannableArea;
        }
        else
        {
             rectScannableArea = new Rectangle(dimension);
        }

       // comparableRgbArray = takeScreenShotReturnRGBarray(rectScannableArea);
        targetRgbArray = takeSSRtrnRgbForImgProcessClss(rectScannableArea);

       // System.out.println("find Wanted Image ... method started");
        for (int firstYpos =0; firstYpos <rectScannableArea.height; firstYpos++ )
        {
            for (int firstXPos =0; firstXPos < rectScannableArea.width; firstXPos++)
            {
                //If we detect first source pixel the same with comparable array,
                //It will check up to the first line it means width of the source array
                if(CompareTwoRgbIntAdvanced(sourceRGBarray[0],
                        targetRgbArray[firstCoordCounter + firstXPos]))
                {

                    //System.out.println("we detect the same coordinates = " + firstXPos + ","+ firstYpos);
                    int equalityCounter =0;
                    for(int secondXpos =0;secondXpos < rectSourceRgbArray.width;secondXpos++)
                    {
                        if (CompareTwoRgbIntAdvanced(sourceRGBarray[secondXpos],
                                targetRgbArray[firstCoordCounter + firstXPos + secondXpos]))
                        {
                            equalityCounter++;
                        }
                        else
                        {
                            break;
                        }
                    }

                    if(equalityCounter == rectSourceRgbArray.width)
                    {
                       // System.out.println("It found the same to first width");
                   //     int [] arrayScanned = takeScreenShotReturnRGBarray(
                           //     new Rectangle(firstXPos,firstYpos,rectRgbArray.width,rectRgbArray.height));
                        int [] arrayScanned = takeSSRtrnRgbForImgProcessClss(
                                new Rectangle(firstXPos,firstYpos,rectSourceRgbArray.width,rectSourceRgbArray.height));

                      /*  AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(
                                transferARGBtoBufferedImage(convertRGBarrayToARGB(arrayScanned),
                                        new Rectangle(firstXPos,firstYpos,rectRgbArray.width,rectRgbArray.height))));*/

                        if(CompareTwoArrayAdvanced(sourceRGBarray,arrayScanned,SENSIBILTY_MED))
                        {
                            return new Rectangle(firstXPos,firstYpos,rectSourceRgbArray.width,rectSourceRgbArray.height);
                        }
                    }
                    /*else
                    {
                        firstXPos+= rectSourceRgbArray.width;
                    }*/
                }
            }
            firstCoordCounter += rectScannableArea.width;
        }
        System.out.println("returned null in the  findWantedImageFromScreen method");
        return null;
    }

    public Rectangle findBorderAreaForWantedColor(int wantedColor,Rectangle scanWantedArea)
    {

        int arrayIncrementCounter =0;
        int maxYValue = 0;
        int maxXValue = 0;
        int minYValue = 0xffffff;
        int minXValue = 0xffffff;




       // int [] comparableArray = takeScreenShotReturnRGBarray(scanWantedArea);
        int [] comparableArray = takeSSRtrnRgbForImgProcessClss(scanWantedArea);


        for(int yPos =0;yPos < scanWantedArea.height; yPos++)
        {
            for (int xPos =0; xPos < scanWantedArea.width; xPos++)
            {
                if(CompareTwoRgbIntAdvanced(wantedColor,comparableArray[xPos + arrayIncrementCounter]))
                {
                    if(maxYValue < yPos )
                    {
                        maxYValue = yPos;
                    }
                    if(minYValue > yPos)
                    {
                        minYValue = yPos;
                    }
                    if(maxXValue < xPos)
                    {
                        maxXValue = xPos;
                    }
                    if(minXValue > xPos)
                    {
                        minXValue = xPos;
                    }

                }
            }

            arrayIncrementCounter += scanWantedArea.width;
        }
            if(maxXValue <= 0 || maxYValue <= 0)
            {
                return null;
            }
      return  new Rectangle(scanWantedArea.x + minXValue,
              scanWantedArea.y + minYValue,
              ((scanWantedArea.x + maxXValue) - (scanWantedArea.x + minXValue) +1),
               (scanWantedArea.y + maxYValue) - (scanWantedArea.y + minYValue)+1);
    }
    public Rectangle [] parseWordsFromOneLineArea(int wantedColor, Rectangle rectBorderedBefore) {

      //  System.out.println("findBorderAreasForWantedColor method is entered ");
        if(rectBorderedBefore == null || wantedColor == 0)
        {
            //System.out.println("Param rectBorderedBefore is null or wantedColor value = 0 ");
            return null;
        }
        //AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(getBufImageScannedArea(rectBorderedBefore)));
        //TimerGame.Sleep(1000);
        Rectangle [] rectArrays = null;

        int arrayIncrementCounter = 0;


        int countColumnSpace =0;

        int rectArraySize =0;
       // int [] comparableArray = takeScreenShotReturnRGBarray(rectBorderedBefore);
        int [] comparableArray = takeSSRtrnRgbForImgProcessClss(rectBorderedBefore);

            for (int firstXPos = 0; firstXPos < rectBorderedBefore.width; firstXPos++)
            {
                arrayIncrementCounter =0;
                for(int firstYpos =0;firstYpos < rectBorderedBefore.height; firstYpos++)
                {
                if (!CompareTwoRgbIntAdvanced(wantedColor, comparableArray[firstXPos + arrayIncrementCounter]))
                {
                        countColumnSpace++;
                   arrayIncrementCounter += rectBorderedBefore.width;
                }
                else
                {
                    countColumnSpace =0;
                    break;
                }
                }
                if(countColumnSpace >= 2*rectBorderedBefore.height)
                {
                    rectArraySize++;
                    countColumnSpace=0;

                    int secondXCounter =0;

                    for(int secondXPos = firstXPos; secondXPos < rectBorderedBefore.width; secondXPos++)
                   // for(int secondYPos = 0; secondYPos < rectBorderedBefore.height; secondYPos++)
                    {
                       // for(int secondXPos = firstXPos; secondXPos < rectBorderedBefore.width; secondXPos++)
                            for(int secondYPos = 0; secondYPos < rectBorderedBefore.height; secondYPos++)
                        {
                            if(CompareTwoRgbIntAdvanced(wantedColor,comparableArray[secondXPos + secondXCounter]))
                            {
                                //System.out.println("detected wanted color first loops = " + secondXPos + " " + secondYPos);
                             firstXPos = secondXPos;
                             secondXPos = rectBorderedBefore.width;
                             break;
                            }
                            secondXCounter += rectBorderedBefore.width;
                        }
                        secondXCounter =0;
                    }
                }

             }
            //Increase for last one we cannot detect because last height index ends with wanted color so
            //we can't detect space
            rectArraySize++;

            //System.out.println(" rectArraySize = " + rectArraySize);
            countColumnSpace =0;
            arrayIncrementCounter =0;

            rectArrays = new Rectangle[rectArraySize];
            rectArraySize = 0;
            int anotherRectXCoor =0;

        for (int firstXPos = 0; firstXPos < rectBorderedBefore.width; firstXPos++)
        {
            for(int firstYpos =0;firstYpos < rectBorderedBefore.height; firstYpos++)
            {
                if (!CompareTwoRgbIntAdvanced(wantedColor, comparableArray[firstXPos + arrayIncrementCounter]))
                {
                    countColumnSpace++;
                    arrayIncrementCounter += rectBorderedBefore.width;
                }else
                {
                    countColumnSpace =0;
                    break;
                }
            }
            if(countColumnSpace >= 2*rectBorderedBefore.height)
            {
                rectArrays[rectArraySize++] = new Rectangle(rectBorderedBefore.x + anotherRectXCoor,rectBorderedBefore.y,
                       firstXPos - anotherRectXCoor - 1,rectBorderedBefore.height );
                anotherRectXCoor = firstXPos;


                countColumnSpace=0;

                int secondXCounter =0;
                for(int secondXPos = firstXPos; secondXPos < rectBorderedBefore.width; secondXPos++)
                // for(int secondYPos = 0; secondYPos < rectBorderedBefore.height; secondYPos++)
                {
                    // for(int secondXPos = firstXPos; secondXPos < rectBorderedBefore.width; secondXPos++)
                    for(int secondYPos = 0; secondYPos < rectBorderedBefore.height; secondYPos++)
                    {
                        if(CompareTwoRgbIntAdvanced(wantedColor,comparableArray[secondXPos + secondXCounter]))
                        {
                         //   System.out.println("detected wanted color second loops = " + secondXPos + " " + secondYPos);
                            firstXPos = secondXPos;
                            anotherRectXCoor = firstXPos;
                            secondXPos = rectBorderedBefore.width;
                            break;
                        }
                        secondXCounter += rectBorderedBefore.width;
                    }
                    secondXCounter =0;
                }
            }
            arrayIncrementCounter =0;
        }
        rectArrays[rectArrays.length -1] = new Rectangle(rectBorderedBefore.x + anotherRectXCoor,rectBorderedBefore.y,
                (rectBorderedBefore.x + rectBorderedBefore.width) - (rectBorderedBefore.x + anotherRectXCoor),rectBorderedBefore.height);
       // System.out.println(" Not Specified Rectangle info = "+Arrays.deepToString(rectArrays));

        //Edit rectangle areas according to wanted Color again.
        for(int i =0; i < rectArrays.length; i++)
        {
            if(rectArrays[i] != null && rectArrays[i].width > 0 && rectArrays[i].height > 0)
            {
                rectArrays[i] = findBorderAreaForWantedColor(CHAT_WHITE_COLOR, rectArrays[i]);
            }

        }
        //System.out.println(" Specified Rectangle info = "+ Arrays.deepToString(rectArrays));
        /*for(int i =0; i < rectArrays.length; i++)
        {
            if(rectArrays[i] != null)
            {
                AutoFishingJFrame.StaticLabelFishingArea.setIcon(
                        new ImageIcon(getBufImageScannedArea(rectArrays[i])));
                TimerGame.Sleep(500);
            }
        }*/

        return rectArrays;
    }
     public void printArrayAsRgb(String name,int[] array)
    {
        if(array != null && array.length > 0)
        {
            int colorRed =0;
       int colorGreen =0;
       int colorBlue =0;
       
       int minValue =0xFFFFFF;
       int minIndexNum =0;
       int maxValue =0;
       int maxIndexNum =0;
       
             for (int i = 0; i < array.length; i++) 
        {
            colorRed = (array[i] & 0x00ff0000) >> 16;
            colorGreen = (array[i] & 0x0000ff00) >> 8;
            colorBlue = array[i] & 0x000000ff;
            System.out.println(name +"index = "+ i + " Red Value = " + colorRed 
            + " Green Value = " + colorGreen + " Blue Value = " + colorBlue + "  hex value = "
                    + String.format("0x%06x", array[i]));    
            
            if(minValue > array[i])
            {
                minValue = array[i];
                minIndexNum = i;
            }
            if(maxValue < array[i])
            {
                maxValue =array[i];
                maxIndexNum = i;
            }
        }
             
             System.out.println("MAX value  = "+ maxValue + " Max index = " + maxIndexNum 
                     + " MAX hex value = " + String.format("0x%06x",maxValue ) + "\n"
            + " MIN value = " + minValue + " Min Index = " + minIndexNum + " MIN hex value = "
                    + String.format("0x%06x",minValue ));
        }
        else
        {
            System.out.println("Rgb Array Boş");
        }
      
    }
  
     public int [] FindFishColors(int [] rgbArray)
    {
        long starttime = System.currentTimeMillis();
        int tempArray [] = new int[500];
        int arrayRgbResult [] ;
        int tempCounter =0;
        int arrayCounter =0;
        int ColorRed =0;
        int ColorGreen =0;
        int ColorBlue =0;
        
        int recordIndex =0;
       
        //int maxValue =0;
        //int maxIndex =0;
   
        
    
        for (int i = 0; i < rgbArray.length; i++) 
        {
          
            ColorRed = (rgbArray[i] & 0x00ff0000) >> 16;
            ColorGreen = (rgbArray[i] & 0x0000ff00) >> 8;
            ColorBlue = rgbArray[i] & 0x000000ff;
            
           // System.out.println("count = "+ count +" red = "+ ColorRed + " green = " + ColorGreen + " blue = " + ColorBlue);
            if(Math.abs(53 - ColorRed) < 11 && Math.abs(ColorGreen - 88) < 5
                    && Math.abs(ColorBlue - 122) <3)
             {
                 recordIndex = i;
                 tempArray[tempCounter++] = rgbArray[i];      
             }
         
        }
        arrayRgbResult = new int[tempCounter +1];
        
        if(tempCounter > 0)
        {
            //Top Side
        if(recordIndex < rgbArray.length/3)
        {
            arrayRgbResult[0] = 1;
        }
        //Middle Side
        else if(recordIndex > rgbArray.length/3 && recordIndex < 2*(rgbArray.length/3))
        {
            arrayRgbResult[0] = 2;
        }
        //Bottom Side
        else if(recordIndex > 2*(rgbArray.length/3) && recordIndex < rgbArray.length)
        {
            arrayRgbResult[0] = 3;
        }
        }
        
        for(int k=0; k < tempArray.length; k++)
        {
            if(tempArray[k] != 0)
            {
                arrayRgbResult[1+ arrayCounter++] = tempArray[k];
            }
        }
        
        for (int l = 0; l <arrayRgbResult.length; l++) 
        {
            if(arrayRgbResult[l] == fishThirdPixel || arrayRgbResult[l] == fishFourthPixel ||
                    arrayRgbResult[l] == fishFifthPixel)
            {
                return null;
            }
        }
      
        //System.out.println("max value = " + String.format("0x%08x", maxValue)+ " index = " + maxIndex);
        //System.out.println("millis value = " +(  System.currentTimeMillis()- starttime));
        return arrayRgbResult;
    }
     
     public int[] takeScreenShotReturnRGBarray(Rectangle screenShotArea,boolean isSyncMetin2Icon)
     {
         if (screenShotArea == null)
         {
             System.out.println("takeScreenShot... method rect value is NULL");
             return null;
         }

         RobotMovingForMetin2 robot ;
         BufferedImage buf;
         int[] comparableArray;

         try
         {
             robot = new RobotMovingForMetin2(this);
         if(isSyncMetin2Icon)
         {

             Rectangle rect = thisGameObjCoord.scannableRectMt2Icon(this,screenShotArea);
             if(rect != null)
             {
                 buf = robot.createScreenCapture(rect);
             }
             else
             {
                 buf = robot.createScreenCapture(screenShotArea);
             }

             comparableArray = transferBufferImgtoARGB(buf);
             convertARGBarrayToRGB(comparableArray);
            return comparableArray;
         }else
         {

             buf = robot.createScreenCapture(screenShotArea);
             //AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(buf));
             comparableArray = transferBufferImgtoARGB(buf);
             convertARGBarrayToRGB(comparableArray);
             return comparableArray;
         }
         }
         catch(AWTException e)
         {
             System.out.println(e.getMessage());
             return null;
         }


     }

     private int[] takeSSRtrnRgbForImgProcessClss(Rectangle screenShotArea)
     {
         if (screenShotArea == null)
         {
             System.out.println("takeScreenShot... method rect value is NULL");
             return null;
         }
         Robot robot ;
         BufferedImage buf;
         int[] comparableArray;
         try
         {
             robot = new Robot();
             buf = robot.createScreenCapture(screenShotArea);
             //AutoFishingJFrame.StaticLabelFishingArea.setIcon(new ImageIcon(buf));
             comparableArray = transferBufferImgtoARGB(buf);
             convertARGBarrayToRGB(comparableArray);
             return comparableArray;
         }
         catch(AWTException e)
         {
             System.out.println(e.getMessage());
             return null;
         }
     }
     public void imageFileWriteToDesktop(String name)
     {

        try {
            Robot robot = new Robot();
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

            BufferedImage buf = robot.createScreenCapture(new Rectangle(size));
            String homePath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().toString();
            String path = homePath + File.separator + name + ".png";
            ImageIO.write(buf, "png", new File(path));
            System.out.println(name + "file is written to " + path + new Date());
         }
        catch (AWTException | IOException e )
        {
            System.out.println(e);
        }

     }
    public void fillWithZeroArray(int [] array)
    {
        if(array != null && array.length  > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                array[i] =0 ;
            }
        }
    }

}
