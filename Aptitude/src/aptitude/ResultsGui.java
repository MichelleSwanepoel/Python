/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aptitude;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author Robert
 */
public class ResultsGui extends javax.swing.JFrame {

    DBQueries database;
    /**
     * Creates new form ResultsGui
     */
    public ResultsGui(DBQueries _db) {
        initComponents();
        database = _db;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAEx = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Results");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        txtAEx.setEditable(false);
        txtAEx.setColumns(20);
        txtAEx.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtAEx.setLineWrap(true);
        txtAEx.setRows(5);
        txtAEx.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAEx);

        btnSave.setText("Save Results");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jButton1.setText("View Graph");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:      
        this.dispose();
        MazeSwingGUI.callFocus();
    }//GEN-LAST:event_formWindowClosed
    private void PlotGraph(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        dataset.setValue(database.getNumberOfCorrectQuestions("EASY"),"Number","Easy");
        dataset.setValue(database.getNumberOfCorrectQuestions("MEDIUM"),"Number","Medium");
        dataset.setValue(database.getNumberOfCorrectQuestions("HARD"),"Number","Hard");
        JFreeChart chart= ChartFactory.createBarChart("Results","Difficulty","Number answered correctly", dataset,PlotOrientation.VERTICAL,false,true,false);
        CategoryPlot p= chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        ChartFrame frame = new ChartFrame("Results Graph",chart);
        frame.setVisible(true);
        frame.setSize(450,350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void showCorrectandIncorrect()
    {
        txtAEx.append("\n");
        ArrayList<Integer> correctQs = database.getListOfCorrectQuestions();
        for (int i = 0; i < 8; i++) 
        {
            if (correctQs.contains((i+1)))
            {
                txtAEx.append("Question "+(i+1)+"\t"+"✔"+"\n");
            }
            else
            {
                txtAEx.append("Question "+(i+1)+"\t"+"✘"+"\n");
            }
        }
       
    }
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        setLocationRelativeTo(null);
        this.setTitle("Results: "+database.getName());
        txtAEx.setText("----------------------Results---------------------- "+"\n\nCorrectly answered questions per category:\n\n");
        txtAEx.append("Easy Questions:\t\t\t"+ database.getNumberOfCorrectQuestions("EASY")+"/2\n");
        txtAEx.append("Medium Questions:\t\t"+ database.getNumberOfCorrectQuestions("MEDIUM")+"/3\n");
        txtAEx.append("Difficult Questions:\t\t"+ database.getNumberOfCorrectQuestions("HARD")+"/3\n");
        txtAEx.append("\nMaths Questions:\t\t"+ database.getNumberOfCorrectCategoryQuestions("Maths")+"/2\n");
        txtAEx.append("Puzzle Questions:\t\t"+ database.getNumberOfCorrectCategoryQuestions("Puzzle")+"/3\n");
        txtAEx.append("Language Questions:\t\t"+ database.getNumberOfCorrectCategoryQuestions("Language")+"/3\n");
        showCorrectandIncorrect();
        
    }//GEN-LAST:event_formWindowActivated

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here
        
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        f.showSaveDialog(null);
        String filename =  f.getCurrentDirectory()+ "\\Results For: "+database.getName()+".txt";
        try 
        {
            PrintWriter out = new PrintWriter(filename);
            out.println(txtAEx.getText());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ResultsGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PlotGraph();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ResultsGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResultsGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResultsGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResultsGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new ResultsGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtAEx;
    // End of variables declaration//GEN-END:variables
}
