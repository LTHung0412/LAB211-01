/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import sample.controller.FileList;
import sample.controller.ProductList;
import sample.controller.WarehouseList;

/**
 *
 * @author LENOVO
 */
public interface I_ReportList {

    int compareDate(String dateFormat, String date1, String date2);

    void showProductsExpired(ProductList productList);

    void showProductsSelling(ProductList productList);

    void showProductsRunningOut(ProductList productList);

    void showReceiptProduct(ProductList productList, WarehouseList warehouseList, FileList fileList);
}
