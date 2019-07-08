/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author winbr386
 */
public class Sale {
    private Integer saleID;
    private Date date;
    private String status;
    private Customer purchasedBy;
    private ArrayList<SaleItem> saleItemList;

    public Sale(Integer saleID, Date date, String status, Customer purchasedBy, ArrayList<SaleItem> saleItemList) {
        this.saleID = saleID;
        this.date = date;
        this.status = status;
        this.purchasedBy = purchasedBy;
        this.saleItemList = saleItemList;
    }

    public Integer getSaleID() {
        return saleID;
    }

    public void setSaleID(Integer saleID) {
        this.saleID = saleID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getPurchasedBy() {
        return purchasedBy;
    }

    public void setPurchasedBy(Customer purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public ArrayList<SaleItem> getSaleItemList() {
        return saleItemList;
    }

    public void setSaleItemList(ArrayList<SaleItem> saleItemList) {
        this.saleItemList = saleItemList;
    }

    public void addItem(SaleItem item) {
        saleItemList.add(item);
    }
    
    public BigDecimal getTotal(){
        BigDecimal total = new BigDecimal(0);
        for(SaleItem item:saleItemList){
            total = total.add(item.getItemTotal());
        }
        return total;
    }

    @Override
    public String toString() {
        return "Sale{" + "saleID=" + saleID + ", date=" + date + ", status=" + status + ", purchasedBy=" + purchasedBy + ", saleItemList=" + saleItemList + '}';
    }
    
    
}
