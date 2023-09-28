/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.service;

/**
 *
 * @author LENOVO
 */
public interface I_Service {

    void addProduct();

    void updateProduct();

    void deleteProduct();

    void showAllProduct();

    void createImportReceipt();

    void createExportReceipt();

    void showProductExpired();

    void showProductSelling();

    void showProductRunningOut();

    void showReceiptProduct();

    void importFile();

    void saveFile();
}
