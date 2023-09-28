/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import sample.controller.FileList;
import sample.controller.WarehouseList;

/**
 *
 * @author LENOVO
 */
public interface I_ProductList {

    int find(String code);

    void add();

    boolean update();

    boolean remove(WarehouseList warehouseList);

    void show(WarehouseList warehouseList,FileList fileList);

    Product getProductByCode(String code);
}
