
import dao.DatabaseDao;
import dao.ProductDataInterface;
import gui.MainMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author winbr386
 */
public class Administration {
    public static void main(String[] args) {
        ProductDataInterface dao = new DatabaseDao();
        // create the frame instance
        MainMenu menuFrame = new MainMenu(dao);

        // centre the frame on the screen
        menuFrame.setLocationRelativeTo(null);

        // show the frame
        menuFrame.setVisible(true);
    }
            
}
