/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import sample.controller.ProductList;
import sample.controller.WarehouseList;

/**
 *
 * @author LENOVO
 */
public interface I_FileList {

    void importDataFromFile(String fName, boolean check, ProductList productList, WarehouseList warehouseList);

    void saveDataToFile(String fName, boolean check, ProductList productList, WarehouseList warehouseList);
}
