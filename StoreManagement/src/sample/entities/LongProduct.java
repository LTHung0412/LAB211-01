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
public class LongProduct extends Product {

    private String manufacturingDate;
    private String expirationDate;

    public LongProduct(String manufacturingDate, String expirationDate, String code, String name, int price, int quantity, String type) {
        super(code, name, price, quantity, type);
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Manufactoring Date: %s, Expiration Date: %s", manufacturingDate, expirationDate);
    }
}
