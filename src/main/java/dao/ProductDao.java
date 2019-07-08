/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import domain.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author winbr386
 */
public class ProductDao implements ProductDataInterface{
    private static Collection<Product> productsList = new HashSet<>();
    private static Collection<String> categoryList = new HashSet<>();
    private static Map<Integer, Product> productMap = new HashMap<>();
    private static Multimap<String,Product> mm = HashMultimap.create();



    @Override
    public Collection<Product> getProductsList() {
        return productsList;
    }

    @Override
    public void addToProducts(Product newProduct) {
        productsList.add(newProduct);
        mm.put(newProduct.getCategory(), newProduct);
        productMap.put(newProduct.getProductID(), newProduct);
        
        // if the product is not in the list of categories already, add it
        if(categoryList.contains(newProduct.getCategory()) == false){
           categoryList.add(newProduct.getCategory()); 
        }
        
    }
    
    @Override
    public Collection<String> getCategoryList() {
        return categoryList;
    }
    
    @Override
    public void deleteProduct(Product target){
        productsList.remove(target);
        productMap.remove(target.getProductID(), target);

    }
    
    @Override
    public void addToMap(Product target){
        productMap.put(target.getProductID(), target);
    }
    
    @Override
    public Product productSearch(Integer id){
        return productMap.get(id);
    }
    
    @Override
    public Collection<Product> filterProducts(String catString){
        return mm.get(catString);
    }
    
}
