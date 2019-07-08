/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author winbr386
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    dao.DAOTest.class,
    gui.ProductEditorDialogTest.class,
    gui.ProductViewDialogTest.class})

public class AllTests {}
