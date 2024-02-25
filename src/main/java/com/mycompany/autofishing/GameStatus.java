package com.mycompany.autofishing;

public class GameStatus
{

    //public  static volatile boolean  isDialogWormClose;
    //public static volatile boolean isWormCountIsReady = false;
    public static volatile  boolean isStopped = true;
    public static volatile boolean isSettingButtonSeemed = false;
    public static volatile boolean isCharKilled = false;
    public static volatile boolean isCharScreenActive = false;
    public static volatile boolean isSaleTitleActive = false;
   // public static volatile boolean isHittedFish = false;
   // public static volatile boolean isActiveFishingClass = false;
    public static volatile boolean isActiveFishBoard = false;
    public static volatile boolean isAnotherPlayer = false;
    public static volatile boolean isMetin2IconSeemed = true;
    public static volatile boolean isChatting = false;
    public static volatile boolean isPausedTheGame = false;
    public static volatile boolean isEnemyDetected = false;


    public static volatile boolean isThreadOneActive = false;
    public static volatile boolean isThreadTwoActive = false;
    public static volatile boolean isThreadThreeActive = false;

    public static boolean GetGameStatusOrUserPrefer()
    {

        return isStopped == false && isChatting == false && isSettingButtonSeemed == true &&
                isCharKilled == false && isSaleTitleActive == false && isCharScreenActive == false
        && isMetin2IconSeemed == true && isPausedTheGame == false;

       /* return isStopped == false && isAnotherPlayer == false && isSettingButtonSeemed == true &&
                isCharKilled == false && isSaleTitleActive == false && isCharScreenActive == false
                && isMetin2IconSeemed == true;*/
    }
}
