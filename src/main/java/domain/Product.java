/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.Objects;
import net.sf.oval.constraint.*;

/**
 *
 * @author winbr386
 */
public class Product {
    @NotNull(message = "ID must be provided.")
    @NotNegative(message = "ID must not be negative.")
    private Integer productID;
    
    @NotNull(message = "Name must be provided.")
    @NotBlank(message = "Name must be provided.")
    @Length(min=2, message="Name must contain at least two characters.")
    @MaxLength(value=50, message = "Name must not exceed 50 characters.")
    private String name;
    
    @NotNull(message = "Description must be provided.")
    @NotBlank(message = "Description must be provided.")
    @Length(min=4, message="Description must contain at least four characters.")
    @MaxLength(value=200, message = "Description must not exceed 50 characters.")
    private String description;
    
    @NotNull(message = "Category must be provided.")
    @NotBlank(message = "Category must be provided.")
    @MaxLength(value=50, message = "Category name must not exceed 50 characters.")
    private String category;
    
    @NotNull(message = "Price must be provided.")
    @NotNegative(message = "Price must be zero or greater.")
    @MaxLength(value=8, message = "Price must not have greater than 8 characters before decimal point.")
    private BigDecimal listPrice;
    
    @NotNull(message = "Quantity must be provided.")
    @NotNegative(message = "Quantity must be zero or greater.")
    private Integer quantity;

    public Product(Integer productID, String name, String description, String category, BigDecimal listPrice, Integer quantity) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.listPrice = listPrice;
        this.quantity = quantity;
    }

    public Product() {
        
    }
    
    public Integer getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID: " + productID + ", Name: " + name + ", Desc: " + description + ", Category: " + category + ", Price: " + listPrice + ", Quantity: " + quantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }
    
    
}
