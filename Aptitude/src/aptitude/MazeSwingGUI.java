/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptitude;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author DELL
 */
public class MazeSwingGUI extends javax.swing.JFrame {

    private static QuestionManager  manager ;
    private static URL url;
    private static CanvasGameContainer canvas;
    private static int questionCounter;
    private static int positionInDirectionsArray;
    private static String userAnswer,correctAnswer;
    private static DBQueries  database;
    private static String[] directions = {"Down", "Down or Right","Down or Right","Up or Down","Right","Down","Down or Right","Right","Down","Down","Right","Down","Down","None"};
    /**
     * Creates new form MazeSwingGUI
     */
    public MazeSwingGUI(DBQueries _database) 
    {
        database = _database;
        manager = new QuestionManager(database);
        questionCounter = 0;
        try 
        {
            initComponents();
            canvas = new CanvasGameContainer(new SlickMaze(("1")));
            canvas.setBounds(0,0,630,630);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setExtendedState(MAXIMIZED_BOTH);
            
            jPanel1.add(canvas);
            this.setVisible(true);
            canvas.start();
            canvas.setFocusable(true);
            canvas.requestFocus();
            
        } 
        catch (SlickException ex) 
        {
            Logger.getLogger(MazeSwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
                   
    }

    private static Image BufferedImtoIm(BufferedImage Bimage)
    {
        return Toolkit.getDefaultToolkit().createImage(Bimage.getSource());
    }
    private static Image resizepic(Image pic,int w,int h)
    {
        BufferedImage rs = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rs.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(pic,0,0,w,h,null);
        g.dispose();
        return rs;
    }
    private static void setImage()
    {
           Image image;
           if (manager.myquestion.getBlob()==null)
           {
                try 
                { 
                    url = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/300px-No_image_available.svg.png");
                    image = ImageIO.read(url);
                    ImageIcon icon = new ImageIcon(resizepic(image, jLabel1.getWidth(),jLabel1.getHeight()));
                    jLabel1.setIcon(icon);
                } 
                catch (IOException ex) 
                {
                    
                }
            } 
           else
           {
               InputStream in = new ByteArrayInputStream(manager.myquestion.getBlob());
               try 
               {
                   BufferedImage bi = ImageIO.read(in);
                   image = BufferedImtoIm(bi);
                   ImageIcon icon = new ImageIcon(resizepic(image, jLabel1.getWidth(),jLabel1.getHeight()));
                   jLabel1.setIcon(icon);
               } 
               catch (IOException ex) 
               {
               }
           }

    }
    public static boolean dealWithAnswer()
    {
        System.out.println(questionCounter);
       correctAnswer = manager.myquestion.answer;
       if(questionCounter < 8)
       {
            //userAnswer = txtAns.getText();
            if(   (IsNumber(userAnswer)&&IsNumber(correctAnswer)) || (!IsNumber(userAnswer)&&!IsNumber(correctAnswer))    )
            {
                proceed();
                return true;
            }
            else 
            {
                if(IsNumber(userAnswer)&& !IsNumber(correctAnswer))
                {
                    JOptionPane.showMessageDialog(null,"Please enter a word or sentence","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else if(!IsNumber(userAnswer)&& IsNumber(correctAnswer))
                {
                    JOptionPane.showMessageDialog(null,"Please enter a number","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
      
       return true;

    }
    private void formWindowClosed(java.awt.event.WindowEvent evt) 
    {                   
        canvas.dispose();
        this.dispose();
        System.exit(0);
    }
    public static void showQuestion(String checkPoint, int position)
    {
        
        
        int qID = Integer.parseInt(checkPoint.substring(0,1));
        switch (qID)
        {
            case 1:
            case 2:
                manager.loadQuestion(QuestionManager.difficulty.EASY);
                break;
            case 3:
            case 4:
            case 5:
                manager.loadQuestion(QuestionManager.difficulty.MEDIUM);
                break;
            case 6:
            case 7:
            case 8:
                manager.loadQuestion(QuestionManager.difficulty.HARD);
                break;
        }
        positionInDirectionsArray = position;
        txtAQ.setText("Question "+(questionCounter+1)+"\n\n"+manager.myquestion.getQuestion());
        setImage();
        userAnswer = JOptionPane.showInputDialog("Your answer:");
        boolean flag = dealWithAnswer();
        while (! flag)
        {
            userAnswer = JOptionPane.showInputDialog("Your answer:");
            flag = dealWithAnswer();
        }
        
        
        
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAQ = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setForeground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtAQ.setEditable(false);
        txtAQ.setColumns(20);
        txtAQ.setLineWrap(true);
        txtAQ.setRows(5);
        txtAQ.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAQ);

        jButton1.setText("View Results");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new ResultsGui(database).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private static void proceed()
    {
       //boolean random = StartPage.userdb.addQuestionToListOfCorrectQuestions(q, Ans);
        manager.saveAnswer(userAnswer);
        txtAQ.setText("Time to move your sprite!\n\nUse the arrow keys!\n\nAvailable directions: "+ directions[positionInDirectionsArray]);

        questionCounter++;
        
        if (questionCounter == 8)
        {
            //txtAQ.setText("No more moves!\n\nSave your results!");
            
            new ResultsGui(database).setVisible(true);
        }

       
    }
    private static boolean IsNumber(String s){

        for (int i = 0; i < s.length(); i++) {
            if(!Character.isDigit(s.charAt(i))) {  
                return false;
            }        
        }
        return true;
    }
    public static void callFocus()
    {
        canvas.setFocusable(true);
        canvas.requestFocus();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea txtAQ;
    // End of variables declaration//GEN-END:variables
}
