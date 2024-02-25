

package com.mycompany.autofishing;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author wadwa a  wad aw
 */
public class AutoFishingPublic {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new AutoFishingJFrame();
         frame.setTitle("Auto Fishing Public");


         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
         
      });
    }
}
