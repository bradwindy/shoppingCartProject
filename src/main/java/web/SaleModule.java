/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

//import dao.SaleDAO;
import dao.SaleDAO;
import domain.Sale;
import domain.SaleItem;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author bradley
 */
public class SaleModule extends Jooby {

    public SaleModule(SaleDAO dao) {
        post("/api/sales", (req, rsp) -> {
            Sale sale = req.body().to(Sale.class);
            System.out.println(sale);
            dao.save(sale);
            
            ArrayList<String> itemStrings = new ArrayList();
            
            for(SaleItem item : sale.getSaleItemList()){
                String productName = item.getProduct().getName();
                String quantity = item.getQuantityPurchased().toString();
                String price = item.getSalePrice().toString();
                itemStrings.add("Product Name: " + productName + ", Quantity Purchased: " + quantity + ", Total Price: $" + price);
            }
            
            CompletableFuture.runAsync(() -> {
                Email email = new SimpleEmail();
                email.setHostName("localhost");
                email.setSmtpPort(2525);
                try {
                    //email.setAuthenticator(new DefaultAuthenticator("username", "password"));
                    //email.setSSLOnConnect(true);
                    email.setFrom("sales@webstore.com");
                } catch (EmailException ex) {
                    Logger.getLogger(SaleModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                email.setSubject("Your Sale Information");
                
                try {
                    email.setMsg(
                            "Dear " + sale.getPurchasedBy().getFirstName() + " " + sale.getPurchasedBy().getSurname() + "\n" +
                            "The contents of your recent order were:\n" +
                            "Your Purchased Items: " + itemStrings.toString() + "\n" +
                            "Provided Shipping Address: " + sale.getPurchasedBy().getAddress() + "\n" +
                            "Total Cost: $" + sale.getTotal().toString()
                    );  
                } catch (EmailException ex) {
                    Logger.getLogger(SaleModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    email.addTo(sale.getPurchasedBy().getEmail());
                } catch (EmailException ex) {
                    Logger.getLogger(SaleModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(SaleModule.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            });
            
            rsp.status(Status.CREATED);
        });
    }
}
