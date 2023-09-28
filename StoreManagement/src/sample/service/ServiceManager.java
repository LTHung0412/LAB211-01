/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.service;

import sample.controller.FileList;
import sample.controller.ProductList;
import sample.controller.ReportList;
import sample.controller.WarehouseList;

/**
 *
 * @author LENOVO
 */
public class ServiceManager implements I_Service {

    ProductList productList = new ProductList();
    WarehouseList warehouseList = new WarehouseList();
    ReportList reportList = new ReportList();
    FileList fileList = new FileList();

    @Override
    public void addProduct() {
        productList.add();
    }

    @Override
    public void updateProduct() {
        productList.update(warehouseList);
    }

    @Override
    public void deleteProduct() {
        productList.remove(warehouseList);
    }

    @Override
    public void showAllProduct() {
        productList.show(warehouseList, fileList);
    }

    @Override
    public void createImportReceipt() {
        warehouseList.createAnImportReceipt(productList);
    }

    @Override
    public void createExportReceipt() {
        warehouseList.createAnExportReceipt(productList);
    }

    @Override
    public void showProductExpired() {
        reportList.showProductsExpired(productList);
    }

    @Override
    public void showProductSelling() {
        reportList.showProductsSelling(productList);
    }

    @Override
    public void showProductRunningOut() {
        reportList.showProductsRunningOut(productList);
    }

    @Override
    public void showReceiptProduct() {
        reportList.showReceiptProduct(productList, warehouseList, fileList);
    }

    @Override
    public void importFile() {
        fileList.importDataFromFile("product.dat", true, productList, warehouseList);
        fileList.importDataFromFile("warehouse.dat", false, productList, warehouseList);
    }

    @Override
    public void saveFile() {
        fileList.saveDataToFile("product.dat", true, productList, warehouseList);
        fileList.saveDataToFile("warehouse.dat", false, productList, warehouseList);
    }

}
