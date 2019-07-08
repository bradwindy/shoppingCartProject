/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerCollectionsDAO;
import dao.CustomerDAO;
import dao.CustomerDatabaseDAO;
import dao.DatabaseDao;
import dao.ProductDataInterface;
import dao.SaleDAO;
import dao.SaleJdbcDAO;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.jooby.Jooby;
import org.jooby.json.Gzon;
import web.auth.BasicHttpAuthenticator;

/**
 *
 * @author bradley
 */
public class Server extends Jooby {
    private final ProductDataInterface productDao = new DatabaseDao();
    private final CustomerDAO customerDao = new CustomerDatabaseDAO();
    private final SaleDAO saleDao = new SaleJdbcDAO();
    
    public Server() {
        port(8080);
        use(new Gzon());
        use(new AssetModule());
        
        List<String> noAuth = Arrays.asList("/api/register");
        use(new BasicHttpAuthenticator(customerDao, noAuth));
        
        use(new ProductModule(productDao));
        use(new CustomerModule(customerDao));
        use(new SaleModule(saleDao));
        
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("\nStarting Server.");
        Server server = new Server();
        
        CompletableFuture.runAsync(() -> {
          server.start();
        });
        
        server.onStarted(() -> {
          System.out.println("\nPress Enter to stop the server.");
        });
        
        
        // wait for user to hit the Enter key
        System.in.read();
        System.exit(0);
    } 
}
