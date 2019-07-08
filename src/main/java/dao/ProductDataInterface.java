/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

/**
 *
 * @author bradley
 */
public interface ProductDataInterface {

    void addToMap(Product target);

    void addToProducts(Product newProduct);

    void deleteProduct(Product target);

    Collection<String> getCategoryList();

    Collection<Product> getProductsList();

    Product productSearch(Integer id);
    
    Collection<Product> filterProducts(String category);
}
