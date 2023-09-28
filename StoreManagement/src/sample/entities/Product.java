/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.entities;

import java.io.Serializable;

/**
 *
 * @author LENOVO
 */
public abstract class Product implements Serializable {

    private String code;
    private String name;
    private int price;
    private int quantity;
    private String type;
    

    public Product(String code, String name, int price, int quantity, String type) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Product [Code: %s, Name: %s, Price: %s, Quantity: %s, Type: %s, ", code, name, price, quantity, type);
    }

}
