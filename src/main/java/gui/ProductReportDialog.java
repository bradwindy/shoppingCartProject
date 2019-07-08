/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dao.DatabaseDao;
import dao.ProductDataInterface;
import domain.Product;
import gui.helpers.SimpleListModel;
import gui.helpers.ValidationHelper;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author bradley
 */
public class ProductReportDialog extends javax.swing.JDialog {

    private final ProductDataInterface productDao;    
    SimpleListModel listModel = new SimpleListModel();
    SimpleListModel catModel = new SimpleListModel();
    ValidationHelper vhelp = new ValidationHelper();
    /**
     * Creates new form ProductReportDialog
     */
    public ProductReportDialog(java.awt.Frame parent, boolean modal, ProductDataInterface dao) {
        super(parent, modal);
        initComponents();
        
        this.productDao = dao;
        
        // update the list model wiht a list gathered form the DAO
        listModel.updateItems(productDao.getProductsList());
        
        // set the list based on items form the list model
        listItems.setModel(listModel);
        
        catModel.updateItems(productDao.getCategoryList());
        comboCatFilter.setModel(catModel);

        vhelp.addTypeFormatter(txtIDSearch, "#0", Integer.class);
        
        buttonEdit.setName("buttonEdit");
        buttonCancel.setName("buttonCancel");
        buttonDelete.setName("buttonDelete");
        buttonSearch.setName("buttonSearch");
        txtIDSearch.setName("txtIDSearch");
        listItems.setName("listItems");
        comboCatFilter.setName("comboCatFilter");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listItems = new javax.swing.JList<>();
        buttonCancel = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonEdit = new javax.swing.JButton();
        comboCatFilter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonSearch = new javax.swing.JButton();
        txtIDSearch = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("productView"); // NOI18N

        jScrollPane1.setViewportView(listItems);

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonEdit.setText("Edit");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        comboCatFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCatFilterActionPerformed(evt);
            }
        });

        jLabel1.setText("View Products");

        jLabel2.setText("Search by ID:");

        jLabel3.setText("Category Filter:");

        buttonSearch.setText("Search");
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        txtIDSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(buttonEdit)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonDelete)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(buttonCancel))
                                .addComponent(comboCatFilter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtIDSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSearch)))))
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(buttonSearch)
                    .addComponent(txtIDSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCatFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttonDelete)
                    .addComponent(buttonEdit))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        if(!listItems.isSelectionEmpty()){
            Product target = (Product)listItems.getSelectedValue();

            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected product?");
            // did the user click the yes button?
            if (result == JOptionPane.YES_OPTION) {
                productDao.deleteProduct(target);

                // update the list model wiht a list gathered form the DAO
                listModel.updateItems(productDao.getProductsList());

                // set the list based on items form the list model
                listItems.setModel(listModel);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No product selected to delete", "Selection Error", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        if(!listItems.isSelectionEmpty()){
            Product target = (Product)listItems.getSelectedValue();
            
            // create the dialog instance
            // the first parameter the parent window, and the second is the modal status
            ProductEditorDialog prodEdDialog = new ProductEditorDialog(this, true, target, productDao);

            // centre the dialog on the parent window
            prodEdDialog.setLocationRelativeTo(this);

            // make the dialog visible
            prodEdDialog.setVisible(true);

            // update the list model wiht a list gathered form the DAO
            listModel.updateItems(productDao.getProductsList());
            listItems.setModel(listModel);
        }else{
            JOptionPane.showMessageDialog(this, "No product selected to edit", "Selection Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        if(txtIDSearch.getValue() != null){
            Integer targetID = (Integer)txtIDSearch.getValue();
            Product productResult = productDao.productSearch(targetID);
            listModel.updateItems(productResult);
            listItems.setModel(listModel);
        }else{
            JOptionPane.showMessageDialog(this, "No value was entered in search", "Search Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void comboCatFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCatFilterActionPerformed
        String catString = (String) comboCatFilter.getSelectedItem();
        listModel.updateItems(productDao.filterProducts(catString));
        listItems.setModel(listModel);
    }//GEN-LAST:event_comboCatFilterActionPerformed

    private void txtIDSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JComboBox<String> comboCatFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Product> listItems;
    private javax.swing.JFormattedTextField txtIDSearch;
    // End of variables declaration//GEN-END:variables
}
