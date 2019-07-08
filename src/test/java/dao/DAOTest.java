/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bradley
 */
public class DAOTest {
    
    //ProductDataInterface dao = new ProductDao();
    ProductDataInterface dao = new DatabaseDao("jdbc:h2:tcp://localhost:9092/project-testing");
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;
    
    public DAOTest() {
    }

    @Before
    public void setUp() {
       this.prodOne = new Product(1, "name1", "cat1", "desc1", new BigDecimal("11.00"), new Integer("22"));
       this.prodTwo = new Product(2, "name2", "cat2", "desc2", new BigDecimal("33.00"), new Integer("44"));
       this.prodThree = new Product(3, "name3", "cat3", "desc3", new BigDecimal("55.00"), new Integer("66"));
       // save the products
       dao.addToProducts(prodOne);
       dao.addToProducts(prodTwo);
       // Note: Intentionally not saving prodThree
    }
    
    @Test
    public void testDaoSave() {
        // save the product using DAO
        dao.addToProducts(prodThree);
        // retrieve the same product via DAO
        Product retrieved = dao.productSearch(3);
        // ensure that the product we saved is the one we got back
        assertEquals("Retrieved product should be the same",
          prodThree, retrieved);
    }
    
    @Test
    public void testDaoEdit() {
        // find the product using DAO
        Product p = dao.productSearch(prodOne.getProductID());
        // edit product
        p.setName("editedname");
        // save the product using DAO
        dao.addToProducts(p);
        // retrieve the product using DAO
        Product retrieved = dao.productSearch(1);
        // ensure that the product we edited is the one we got back
        assertEquals("Retrieved product should be the same", p, retrieved);
    }
    
    @Test
    public void testDaoDelete() {
        // delete the product via the DAO
        dao.deleteProduct(prodOne);
        // try to retrieve the deleted product
        Product retrieved = dao.productSearch(1);
        // ensure that the student was not retrieved (should be null)
        assertNull("Product should no longer exist", retrieved);
    }
    
    @Test
    public void testDaoGetAll() {
        Collection<Product> products = dao.getProductsList();
        // ensure the result includes the two saved products
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));
        // ensure the result ONLY includes the two saved products
        assertEquals("Only 2 products in result", 2, products.size());
        // find prodOne - result is not a map, so we have to scan for it
        for (Product p : products) {
            if (p.equals(prodOne)) {
                // ensure that all of the details were correctly retrieved
                assertEquals(prodOne.getProductID(), p.getProductID());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getListPrice(), p.getListPrice());
                assertEquals(prodOne.getQuantity(), p.getQuantity());
            }
        }
    }
    
    @Test
    public void testDaoGetCategories() {
        Collection<String> categories = dao.getCategoryList();
        // ensure the result includes the two saved categories
        assertTrue("prodOne's category should exist", categories.contains(prodOne.getCategory()));
        assertTrue("prodTwo's category should exist", categories.contains(prodTwo.getCategory()));
        // ensure the result ONLY includes the two categories
        assertEquals("Only 2 products in result", 2, categories.size());
        // Make sure that categories does not contain cat3 somehow
        assertFalse("category should not exist", categories.contains("cat3"));
    }
    
    @Test
    public void testDaoGetProductsByCategory() {
        // filter by category to get a single product
        Collection<Product> products = dao.filterProducts(prodOne.getCategory());

        // make sure that prodOne is in the result and that there is only one item in the result
        assertTrue("products should contain prodOne", products.contains(prodOne));
        assertEquals("Only 1 products in result", 1, products.size());
        
        // get the product out of the collection
        Product p = products.iterator().next();

        if (p.equals(prodOne)) {
            // ensure that all of the details were correctly retrieved
            assertEquals(prodOne.getProductID(), p.getProductID());
            assertEquals(prodOne.getName(), p.getName());
            assertEquals(prodOne.getDescription(), p.getDescription());
            assertEquals(prodOne.getCategory(), p.getCategory());
            assertEquals(prodOne.getListPrice(), p.getListPrice());
            assertEquals(prodOne.getQuantity(), p.getQuantity());
        }
        
        // make sure that we dont get any result when filtering by a category that doesn't exist
        assertTrue(dao.filterProducts("unexistentcategory").isEmpty());
    }
    
    @Test
      public void testDaoFindById() {
        // get prodOne using findById method
        Product p = dao.productSearch(prodOne.getProductID());
        // assert that you got back prodOne, and not another product
        // assert that prodOne's details were properly retrieved
        if (p.equals(prodOne)) {
            // ensure that all of the details were correctly retrieved
            assertEquals(prodOne.getProductID(), p.getProductID());
            assertEquals(prodOne.getName(), p.getName());
            assertEquals(prodOne.getDescription(), p.getDescription());
            assertEquals(prodOne.getCategory(), p.getCategory());
            assertEquals(prodOne.getListPrice(), p.getListPrice());
            assertEquals(prodOne.getQuantity(), p.getQuantity());
        }
        // call getById using a non-existent ID
        Product p2 = dao.productSearch(123);
        // assert that the result is null
        assertNull("product with id 123 does not exist", p2);
    }
    
    @After
    public void tearDown() {
        dao.deleteProduct(prodOne);
        dao.deleteProduct(prodTwo);
        dao.deleteProduct(prodThree);
    }
    
}
