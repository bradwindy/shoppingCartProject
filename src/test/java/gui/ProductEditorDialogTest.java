/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.ProductDataInterface;
import domain.Product;
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

/**
 *
 * @author winbr386
 */
public class ProductEditorDialogTest {
        private ProductDataInterface dao;
	private DialogFixture fixture;
	private Robot robot;

	@Before
	public void setUp() {
            robot = BasicRobot.robotWithNewAwtHierarchy();

            // Slow down the robot a little bit - default is 30 (milliseconds).
            // Do NOT make it less than 5 or you will have thread-race problems.
            robot.settings().delayBetweenEvents(70);

            // add some categories for testing with
            Collection<String> categories = new HashSet<>();
            categories.add("Audio");
            categories.add("Video");
            categories.add("Broken");

            // create a mock for the DAO
            dao = mock(ProductDataInterface.class);

            // stub the getCategoryList method to return the categories
            when(dao.getCategoryList()).thenReturn(categories);
	}

	@After
	public void tearDown() {
		// clean up fixture so that it is ready for the next test
		fixture.cleanUp();
	}

	@Test
	public void testEdit() {
		// a student to edit
		Product stereo = new Product(1234, "Stereo", "A quality hifi stereo", "Audio", new BigDecimal(200.00), 40);

		// create dialog passing in product and mocked DAO
		ProductEditorDialog dialog = new ProductEditorDialog(null, true, stereo, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);

		// show the dialog on the screen, and ensure it is visible
		fixture.show().requireVisible();

		// verify that the UI componenents contains the Product details
		fixture.textBox("txtID").requireText("1234");
		fixture.textBox("txtName").requireText("Stereo");
                fixture.textBox("txtDescription").requireText("A quality hifi stereo");
		fixture.comboBox("comboCategory").requireSelection("Audio");
                fixture.textBox("txtPrice").requireText("200.00");
                fixture.textBox("txtQuantity").requireText("40");

		// edit details
		fixture.textBox("txtName").selectAll().deleteText().enterText("Cheap Stereo");
                fixture.textBox("txtDescription").selectAll().deleteText().enterText("Yeah nah this sucks");
		fixture.comboBox("comboCategory").selectItem("Broken");
                
                // These actions are different because selectall does not work on these particular formatted text fields
                fixture.textBox("txtPrice").click().doubleClick().pressAndReleaseKeys(VK_BACK_SPACE).enterText("10");
                fixture.textBox("txtQuantity").click().doubleClick().pressAndReleaseKeys(VK_BACK_SPACE).enterText("1");

		// click the save button
		fixture.button("buttonSave").click();

		// create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		// verify that the DAO.save method was called, and capture the passed student
		verify(dao).addToProducts(argument.capture());

		// retrieve the passed student from the captor
		Product editedProduct = argument.getValue();

		// check that the changes were saved
		assertEquals("Ensure the name was changed", "Cheap Stereo", editedProduct.getName());
                assertEquals("Ensure the desc was changed", "Yeah nah this sucks", editedProduct.getDescription());
		assertEquals("Ensure the category was changed", "Broken", editedProduct.getCategory());
                assertEquals("Ensure the price was changed", new BigDecimal(10.00), editedProduct.getListPrice());
                assertEquals("Ensure the quantity was changed", new Integer(1), editedProduct.getQuantity());
	}

	@Test
	public void testSave() {
		// create the dialog passing in the mocked DAO
		ProductEditorDialog dialog = new ProductEditorDialog(null, true, dao);

		// use AssertJ to control the dialog
		fixture = new DialogFixture(robot, dialog);
		fixture.show().requireVisible();

		// enter some details into the UI components
                fixture.textBox("txtID").enterText("222");
		fixture.textBox("txtName").enterText("TV");
                fixture.textBox("txtDescription").enterText("It's HD");
		fixture.comboBox("comboCategory").enterText("Video");
                fixture.textBox("txtPrice").enterText("900");
                fixture.textBox("txtQuantity").enterText("5");

		// click the save button
		fixture.button("buttonSave").click();

		// create a Mockito argument captor to use to retrieve the passed student from the mocked DAO
		ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

		// verify that the DAO.save method was called, and capture the passed student
		verify(dao).addToProducts(argument.capture());

		// retrieve the passed student from the captor
		Product savedProduct = argument.getValue();

		// test that the student's details were properly saved
		assertEquals("Ensure the ID was saved", new Integer(222), savedProduct.getProductID());
		assertEquals("Ensure the name was saved", "TV", savedProduct.getName());
                assertEquals("Ensure the desc was saved", "It's HD", savedProduct.getDescription());
		assertEquals("Ensure the category was saved", "Video", savedProduct.getCategory());
                assertEquals("Ensure the price was saved", new BigDecimal(900), savedProduct.getListPrice());
                assertEquals("Ensure the quantity was saved", new Integer(5), savedProduct.getQuantity());
	}
}
