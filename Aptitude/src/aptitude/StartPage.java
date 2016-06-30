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
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
public class StartPage extends javax.swing.JFrame {
static AppGameContainer app;
//static QuestionGui myQuestion ;
    /**
     * Creates new form StartPage
     */
    public StartPage() {
        initComponents();
    }
//  public void close(){
//    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
//    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
//}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStart = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtStart = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MazeProg");
        setPreferredSize(new java.awt.Dimension(469, 352));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(null);

        btnStart.setText("Start");
        btnStart.setFocusTraversalPolicyProvider(true);
        btnStart.setName("btnStart"); // NOI18N
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });
        getContentPane().add(btnStart);
        btnStart.setBounds(180, 220, 57, 30);

        btnEnter.setVisible(false);
        btnEnter.setText("Enter");
        btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });
        getContentPane().add(btnEnter);
        btnEnter.setBounds(180, 170, 59, 23);

        jLabel1.setVisible(false);
        jLabel1.setText("Firstly: unscramble \"tarts\" into a new beginning");
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 70, 358, 14);

        txtStart.setVisible(false);
        getContentPane().add(txtStart);
        txtStart.setBounds(170, 110, 88, 30);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Navigate your way through the maze. Answer any questions that may pop up. Your results will be shown upon completion");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(37, 11, 350, 140);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 0, 290);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   //public static DB Udb = new DB();
   public static DBQueries database = new DBQueries();
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Please enter your name");
        database.addUser(name);
        jTextArea1.setVisible(false);
        jTextArea1.setVisible(false);
        jLabel1.setVisible(true);
        txtStart.setVisible(true);
        btnEnter.setVisible(true);
        btnStart.setVisible(false);
        
    }//GEN-LAST:event_btnStartActionPerformed

    public static void showPopup(String checkPoint)
    {
        QuestionManager manager = new QuestionManager(database);
        //myQuestion.setVisible(true);
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
        String answer = JOptionPane.showInputDialog(null,manager.myquestion.getQuestion());
        manager.saveAnswer(answer);

        
        
        
    }
    private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnterActionPerformed
        // TODO add your handling code here:
        String Ans=txtStart.getText();
        //myQuestion =  new QuestionGui(database);
      
        if(Ans.toLowerCase().equals("start"))
        {
          //JOptionPane.showMessageDialog(null,"Get Ready");
          //close();
          //this.dispose();
           this.setVisible(false);
          //new QuestionGui().setVisible(true);
          try
            {
                
                app = new AppGameContainer(new SlickMaze("1"));
                app.setDisplayMode(630, 630, false);
                app.start();
                
            }
            catch (SlickException e)
            {
             e.printStackTrace();
            }
          
         } 
     
    }//GEN-LAST:event_btnEnterActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        jLabel2.setSize(this.getSize());
        setLocationRelativeTo(null);
        
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartPage().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnter;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtStart;
    // End of variables declaration//GEN-END:variables
}
