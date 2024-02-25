
package com.mycompany.autofishing;

import javax.swing.JFrame;
import java.awt.*;

/**
 *
 * @author wadwa a wad aw
 */
public class TestDrawingFrame extends JFrame
{
    ImageProcess imageProcess;
   public DrawingScreen drawingScreen;
    public TestDrawingFrame(ImageProcess imageProcess)
    {
        this.imageProcess = imageProcess;
        drawingScreen =  new DrawingScreen(this,imageProcess);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        add(drawingScreen);
        pack();
    }
}
