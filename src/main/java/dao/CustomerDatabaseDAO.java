/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Customer;
import domain.Product;
import domain.Sale;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bradley
 */
public class CustomerDatabaseDAO implements CustomerDAO{
    private String URLString = "jdbc:h2:tcp://localhost:9092/project;IFEXISTS=TRUE";
    
    public CustomerDatabaseDAO() {
    }
    
    public CustomerDatabaseDAO(String newURLString) {
        this.URLString = newURLString;
    }

    @Override
    public void save(Customer customer) {
        String sql = "merge into customer (USERNAME, FIRSTNAME, SURNAME, PASSWORD, EMAIL, ADDRESS, CREDITCARDDETAILS) key (username) values (?,?,?,?,?,?,?)";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getSurname());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getEmail());
            stmt.setString(6, customer.getAddress());
            stmt.setString(7, customer.getCreditCardDetails());
            
            stmt.executeUpdate();  // execute the statement
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Customer getCustomer(String username) {
        String sql = "select * from customer where username = ?";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setString(1, username);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // get the data out of the query                
                Integer personID = rs.getInt("personid");
                String user = rs.getString("username");
                String firstname = rs.getString("firstname");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String creditCardDetails = rs.getString("creditCardDetails");
                

                // use the data to create a student object
                return new Customer(personID, user,firstname,surname,password,email,address,creditCardDetails);
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        /*
        if (customers.containsKey(username)) {
            return customers.get(username).getPassword().equals(password);
        } else {
            return false;
        }
        */
        
        String sql = "select password from customer where username = ?";

        try (
            // get a connection to the database
            Connection dbCon = JdbcConnection.getConnection(URLString);

            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setString(1, username);
            // execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordFromDB = rs.getString("password");
                return(passwordFromDB.equals(password));
            }else{
                return false;
            }
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }
    
    
}
