/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;

/**
 *
 * @author winbr386
 */
public class SaleItem {
    private Integer quantityPurchased;
    private BigDecimal salePrice;
    private Product product;
    private Sale ownedBy;
    
    public SaleItem(Integer quantityPurchased, BigDecimal salePrice, Product product, Sale ownedBy) {
        this.quantityPurchased = quantityPurchased;
        this.salePrice = salePrice;
        this.product = product;
        this.ownedBy = ownedBy;
    }

    public Integer getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(Integer quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Sale getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Sale ownedBy) {
        this.ownedBy = ownedBy;
    }

    public BigDecimal getItemTotal(){
        return salePrice.multiply(new BigDecimal(quantityPurchased));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    
}
