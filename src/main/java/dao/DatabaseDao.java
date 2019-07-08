/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bradley
 */
public class DatabaseDao implements ProductDataInterface{
    private String URLString = "jdbc:h2:tcp://localhost:9092/project;IFEXISTS=TRUE";
    private static Map<Integer, Product> productMap = new HashMap<>();
    
    public DatabaseDao() {
    }
    
    public DatabaseDao(String newURLString) {
        this.URLString = newURLString;
    }

    @Override
    public void addToMap(Product target) {
        productMap.put(target.getProductID(), target);
    }

    @Override
    public void addToProducts(Product newProduct) {
        String sql = "merge into product (product_id, product_name, description, category, price, quantity) values (?,?,?,?,?,?)";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            
            stmt.setInt(1, newProduct.getProductID());
            stmt.setString(2, newProduct.getName());
            stmt.setString(3, newProduct.getDescription());
            stmt.setString(4, newProduct.getCategory());
            stmt.setBigDecimal(5, newProduct.getListPrice());
            stmt.setInt(6, newProduct.getQuantity());
            
            stmt.executeUpdate();  // execute the statement
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deleteProduct(Product target) {
        String sql = "delete from product where product_id = ?";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            
            stmt.setInt(1, target.getProductID());
            
            stmt.executeUpdate();  // execute the statement
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }    
    }

    @Override
    public Collection<String> getCategoryList() {
        String sql = "select distinct category from product";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<String> categories = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {
                String category = rs.getString("category");

                categories.add(category);
            }

            return categories;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProductsList() {
        String sql = "select * from product order by product_id";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // execute the query
            ResultSet rs = stmt.executeQuery();

            // Using a List to preserve the order in which the data was returned from the query.
            List<Product> products = new ArrayList<>();

            // iterate through the query results
            while (rs.next()) {

                // get the data out of the query                
                Integer productID = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("price");
                Integer quantity = rs.getInt("quantity");

                // use the data to create a student object
                Product p = new Product(productID, name, description, category, listPrice, quantity);

                // and put it in the collection
                products.add(p);
            }

            return products;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Product productSearch(Integer id) {
        String sql = "select * from product where product_id = ?";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // get the data out of the query                
                Integer productID = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("price");
                Integer quantity = rs.getInt("quantity");

                // use the data to create a student object
                return new Product(productID, name, description, category, listPrice, quantity);
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public Collection<Product> filterProducts(String catString){
        String sql = "select * from product where category = ?";
        
        Collection<Product> products = new HashSet<>();

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setString(1, catString);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                // get the data out of the query                
                Integer productID = rs.getInt("product_id");
                String name = rs.getString("product_name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                BigDecimal listPrice = rs.getBigDecimal("price");
                Integer quantity = rs.getInt("quantity");

                // use the data to create a student object
                products.add(new Product(productID, name, description, category, listPrice, quantity));
            }
            return products;
            
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
}
