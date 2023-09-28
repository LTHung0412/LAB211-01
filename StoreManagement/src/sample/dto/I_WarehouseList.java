/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import sample.entities.Warehouse;
import sample.controller.ProductList;

/**
 *
 * @author LENOVO
 */
public interface I_WarehouseList {

    int find(String code, boolean option);
    Warehouse inputReceipt(boolean option, ProductList productList);

    void createAnImportReceipt(ProductList productList);

    void createAnExportReceipt(ProductList productList);
}
