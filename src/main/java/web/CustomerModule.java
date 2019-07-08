/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.CustomerDAO;
import domain.Customer;
import org.jooby.Err;
import org.jooby.Jooby;
import org.jooby.Status;

/**
 *
 * @author bradley
 */
public class CustomerModule extends Jooby{
    public CustomerModule(CustomerDAO dao) {
        post("/api/register", (req, rsp) -> {
          Customer customer = req.body().to(Customer.class);
          dao.save(customer);
          rsp.status(Status.CREATED); 
        });
        
        get("/api/customers/:username", (req) -> {
          String username = req.param("username").value();
          Customer customerGet = dao.getCustomer(username);
          
          if(customerGet == null){
              throw new Err(Status.NOT_FOUND);
          }else{
              return customerGet;
          }
        });
    }
}
