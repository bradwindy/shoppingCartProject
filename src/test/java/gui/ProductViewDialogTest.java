/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDataInterface;
import domain.Product;
import gui.helpers.SimpleListModel;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;
import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.awt.event.KeyEvent.*;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author winbr386
 */
public class ProductViewDialogTest {
      
    private ProductDataInterface dao;
	private DialogFixture fixture;
	private Robot robot;
        private Product prodOne;

	@Before
	public void setUp() {
            robot = BasicRobot.robotWithNewAwtHierarchy();

            // Slow down the robot a little bit - default is 30 (milliseconds).
            // Do NOT make it less than 5 or you will have thread-race problems.
            robot.settings().delayBetweenEvents(70);

            prodOne = new Product(1234, "Stereo", "A quality hifi stereo", "Audio", new BigDecimal(200.00), 40);

            Collection<Product> products = new HashSet<>();
            products.add(prodOne);

            // create a mock for the DAO
            dao = mock(ProductDataInterface.class);

            when(dao.getProductsList()).thenReturn(products);
	}

	@After
	public void tearDown() {
		// clean up fixture so that it is ready for the next test
		fixture.cleanUp();
	}
    
        @Test
	public void testView() {
		// create dialog passing in mocked DAO
		ProductReportDialog dialog = new ProductReportDialog(null, true, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);

		// show the dialog on the screen, and ensure it is visible
		fixture.show().requireVisible();
                
                // verify that the DAO.save method was called, and capture the passed product
                verify(dao).getProductsList();
                
                //get list from dialog
                SimpleListModel model = (SimpleListModel) fixture.list("listItems").target().getModel();
                
                // check list details are correct
                assertTrue("List contains the expected product", model.contains(prodOne));
		assertEquals("List contains the correct number of products", 1, model.getSize());

		// select prod one from the list
		fixture.list("listItems").selectItem(prodOne.toString());

		// click the edit button
		fixture.button("buttonEdit").click();

                DialogFixture editDialog = fixture.dialog("productEdit");
                // check that passed details are correct
		editDialog.textBox("txtID").requireText(prodOne.getProductID().toString());
	}
}
