package dao;

import dao.DAOException;
import dao.SaleDAO;
import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleJdbcDAO implements SaleDAO {

    private static final String url = "jdbc:h2:tcp://localhost:9092/project;IFEXISTS=TRUE";

    @Override
    public void save(Sale sale) {

        Connection con = JdbcConnection.getConnection(url);
        try {
            try (
                    PreparedStatement insertSaleStmt = con.prepareStatement(
                            "insert into sale (date, status, personid) values (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement insertSaleItemStmt = con.prepareStatement(
                            "merge into saleItem (saleid, product_id, quantity, saleprice) values (?,?,?,?)");
                    PreparedStatement updateProductStmt = con.prepareStatement(
                            "update product set quantity =  quantity - (?) where product_id = (?)");) {

                // Since saving and sale involves multiple statements across
                // multiple tables we need to control the transaction ourselves
                // to ensure our DB remains consistent.
                //
                // Turn off auto-commit which effectively starts a new transaction.
                con.setAutoCommit(false);

                Customer customer = sale.getPurchasedBy();

                // #### save the sale ### //
                // add a date to the sale if one doesn't already exist
                if (sale.getDate() == null) {
                    sale.setDate(new Date());
                }
                
                sale.setStatus("Sold");

                // convert sale date into to java.sql.Timestamp
                Date date = sale.getDate();
                Timestamp timestamp = new Timestamp(date.getTime());

                // ****
                // write code here that saves the timestamp and username in the
                // sale table using the insertSaleStmt statement.
                // ****
                
                
                insertSaleStmt.setTimestamp(1, timestamp);
                insertSaleStmt.setString(2, sale.getStatus());
                insertSaleStmt.setInt(3, customer.getPersonID());

                insertSaleStmt.executeUpdate();
                
                // get the auto-generated sale ID from the database
                ResultSet rs = insertSaleStmt.getGeneratedKeys();
                
                Integer saleId = null;

                if (rs.next()) {
                    saleId = rs.getInt(1);
                } else {
                    throw new DAOException("Problem getting generated Sale ID");
                }

                Collection<SaleItem> items = sale.getSaleItemList();

                for (SaleItem item : items) {

                    Product product = item.getProduct();
                    
                    System.out.println(product);

                    // ****
                    // write code here that saves the sale item
                    // using the insertSaleItemStmt statement.
                    // ****
                    
                    // merge into saleItem (saleid, product_id, quantity, saleprice) values (?,?,?,?)
                    
                    // saleID retrieved from sale that owns this sale item 
                    insertSaleItemStmt.setInt(1, saleId);
                    insertSaleItemStmt.setInt(2, item.getProduct().getProductID());
                    insertSaleItemStmt.setInt(3, item.getQuantityPurchased());
                    insertSaleItemStmt.setBigDecimal(4, item.getSalePrice());

                    insertSaleItemStmt.executeUpdate();
                    
                    // ****
                    // write code here that updates the product quantity using
                    // the updateProductStmt statement.
                    // ****
                    
                    // MERGE INTO product (quantity) KEY(product_id) VALUES(?)
                    
                    
                    updateProductStmt.setInt(1, item.getQuantityPurchased());
                    updateProductStmt.setInt(2, item.getProduct().getProductID());
                    updateProductStmt.executeUpdate();
                }

                // commit the transaction
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {

            Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);

            try {
                // something went wrong so rollback
                con.rollback();

                // turn auto-commit back on
                con.setAutoCommit(true);

                // and throw an exception to tell the user something bad happened
                throw new DAOException(ex.getMessage(), ex);
            } catch (SQLException ex1) {
                throw new DAOException(ex1.getMessage(), ex1);
            }

        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
