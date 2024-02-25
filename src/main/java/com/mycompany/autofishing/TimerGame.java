
package com.mycompany.autofishing;

/**
 *
 * @author wadwa a wad aw
 */
public class TimerGame 
{

public long StartedSecondTime = 0l;

public boolean CheckDelayTimeInSecond(long delayTime)
{
    if(StartedSecondTime <= 0l)SetStartedSecondTime();
    if(System.currentTimeMillis()/1000 - StartedSecondTime < delayTime)
    {
        return true;
    }
 return false;
   
}
    public static void Sleep(int milliseconds) {
        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException ex) {
            System.out.println(ex);

        }
    }

    public static int MakeRandomIntValueBetweenParams(int minValue, int maxValue)
    {
        if(minValue >= maxValue)minValue =0;
        int total = ((int)(Math.random() * maxValue)) + minValue;
        if(total > maxValue)return maxValue;
        return total;
    }
public void SetStartedSecondTime()
{
    StartedSecondTime = System.currentTimeMillis()/1000;
}
}
