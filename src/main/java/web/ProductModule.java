/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dao.ProductDataInterface;
import org.jooby.Jooby;

/**
 *
 * @author bradley
 */
public class ProductModule extends Jooby{
    public ProductModule(ProductDataInterface dao) {
        get("/api/products", () -> dao.getProductsList());
        get("/api/products/:id", (req) -> {
          Integer id = req.param("id").intValue();
          return dao.productSearch(id);
        });
        get("/api/categories", () -> dao.getCategoryList());
        get("/api/categories/:category", (req) -> {
          String category = req.param("category").value();
          return dao.filterProducts(category);
        });
        
    }
}
