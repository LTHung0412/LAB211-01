/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.entities;

/**
 *
 * @author LENOVO
 */
public class DailyProduct extends Product {

    private int size;

    public DailyProduct(int size, String code, String name, int price, int quantity, String type) {
        super(code, name, price, quantity, type);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Size: %s]", size);
    }

}
