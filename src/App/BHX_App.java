/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import form.LoginForm;

/**
 *
 * @author Duy Khanh
 */
public class BHX_App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new AppManage().createDB();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });

    }

}
