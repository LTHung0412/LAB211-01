/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.entities;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class Warehouse implements Serializable {

    private String code;
    private String time;
    private List<Product> listProduct;

    public Warehouse(String code, String time, List<Product> listProduct) {
        this.code = code;
        this.time = time;
        this.listProduct = listProduct;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }    
}
