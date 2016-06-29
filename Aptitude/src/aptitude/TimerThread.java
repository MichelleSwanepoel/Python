/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptitude;

/**
 *
 * @author Robert
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TimerThread extends Thread{
    int count;
    Timer  t;
 @Override
    public void run() {
            TimerRun();           
          }
    @Override
    public void start(){
      TimerRun();    
    }
    
     private void TimerRun(){
      count = 0;
      t = new Timer(1000,new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              count++;
          }
      });
      t.start();
      while(count <3){
         if (count==3){
            t.stop();
          }
      }
     }

}
