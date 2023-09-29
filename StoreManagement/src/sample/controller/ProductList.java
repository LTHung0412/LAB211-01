/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import sample.dto.I_ProductList;
import sample.entities.Product;
import sample.entities.DailyProduct;
import sample.entities.LongProduct;
import sample.entities.Warehouse;
import sample.utils.Utils;

/**
 *
 * @author LENOVO
 */
public class ProductList extends ArrayList<Product> implements I_ProductList {

    @Override
    public int find(String code) {
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCode().equals(code)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void add() {
        boolean quit;
        do {
            boolean check = true;
            String code = "";
            String codePattern = "P\\d{7}";
            do {
                code = Utils.getString("Input code (Pxxxxxxx): ");
                int index = this.find(code);
                if (index == -1 && code.matches(codePattern)) {
                    check = false;
                }
            } while (check);
            String name = Utils.getString("Input name: ");
            int price = Utils.getInt("Input price: ", 1, 1000);
            int quantity = Utils.getInt("Input quantity: ", 1, 1000);
            String type = Utils.getString("Input type (daily or long): ");
            if (type.equals("daily")) {
                int size = Utils.getInt("Input size: ", 1, 1000);
                this.add(new DailyProduct(size, code, name, price, quantity, type));
            } else if (type.equals("long")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDateTime now = LocalDateTime.now();
                String manufactoringDate = dtf.format(now);
                System.out.println("Manufacturing Date: " + manufactoringDate);
                String expirationDate = Utils.getDate("Input expiration date (MM/dd/yyyy): ", "MM/dd/yyyy");
                this.add(new LongProduct(manufactoringDate, expirationDate, code, name, price, quantity, type));
            }
            quit = Utils.confirmYesNo("Do you want to continue add (Y or N): ");
        } while (quit);
    }

    @Override
    public void update(WarehouseList warehouseList) {
        boolean check = false;
        String code = "";
        try {
            code = Utils.getString("Input code that you want to update: ");
            int index = this.find(code);
            if (index < 0 || index > this.size()) {
                System.out.println("Product does not exist.");
                check = false;
            } else {
                String newName = Utils.getString("Input new name: ", this.get(index).getName());
                int newPrice = Utils.getInt("Input new price: ", 1, 1000, this.get(index).getPrice());
                int newQuantity = Utils.getInt("Input new quantity: ", 1, 1000, this.get(index).getQuantity());
                String newType = Utils.getString("Input new type (daily or long): ", this.get(index).getType());

                int newSize = 0;
                String newManufactoringDate = null;
                String newExpirationDate = null;

                Product product = this.get(index);
                if (product.getType().equals("daily")) {
                    DailyProduct dp = (DailyProduct) product;
                    dp.setSize(0);
                    this.set(index, dp);
                } else if (product.getType().equals("long")) {
                    LongProduct lp = (LongProduct) product;
                    lp.setManufacturingDate(null);
                    lp.setExpirationDate(null);
                    this.set(index, lp);
                }
                if (newType.equals("long")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDateTime now = LocalDateTime.now();
                    newManufactoringDate = dtf.format(now);
                    System.out.println("Manufacturing Date: " + newManufactoringDate);
                    newExpirationDate = Utils.getDate("Input expiration date (MM/dd/yyyy): ", "MM/dd/yyyy");
                    LongProduct lp = new LongProduct(newManufactoringDate, newExpirationDate, code, newName, newPrice, newQuantity, newType);
                    this.set(index, lp);
                } else if (newType.equals("daily")) {
                    newSize = Utils.getInt("Input new size: ", 1, 1000);
                    DailyProduct dp = new DailyProduct(newSize, code, newName, newPrice, newQuantity, newType);
                    this.set(index, dp);
                }
                System.out.println(this.get(index));

                //Appy changes for import/export receipts
                Iterator<Warehouse> warehouseImportListIterator = warehouseList.listImport.iterator();
                while (warehouseImportListIterator.hasNext()) {
                    Warehouse w = warehouseImportListIterator.next();

                    Iterator<Product> productIterator = w.getListProduct().iterator();
                    List<Product> productsToAdd = new ArrayList<>(); // Store products to be added

                    while (productIterator.hasNext()) {
                        Product p = productIterator.next();

                        if (p.getCode().equals(code)) {
                            if (p.getType().equals("daily") && p.getType().equals(newType)) {
                                DailyProduct dp = (DailyProduct) p;
                                dp.setName(newName);
                                dp.setPrice(newPrice);
                                dp.setType(newType);
                                dp.setSize(newSize);
                            } else if (p.getType().equals("daily") && !p.getType().equals(newType)) {
                                Product lp = new LongProduct(newManufactoringDate, newExpirationDate, p.getCode(), newName, newPrice, p.getQuantity(), newType);
                                productsToAdd.add(lp); // Add to the list of products to be added
                                productIterator.remove(); // Remove the current product
                            } else if (p.getType().equals("long") && p.getType().equals(newType)) {
                                LongProduct lp = (LongProduct) p;
                                lp.setName(newName);
                                lp.setPrice(newPrice);
                                lp.setManufacturingDate(newManufactoringDate);
                                lp.setExpirationDate(newExpirationDate);
                                lp.setType(newType);
                            } else if (p.getType().equals("long") && !p.getType().equals(newType)) {
                                Product dp = new DailyProduct(newSize, p.getCode(), newName, newPrice, p.getQuantity(), newType);
                                productsToAdd.add(dp); // Add to the list of products to be added
                                productIterator.remove(); // Remove the current product
                            }
                        }
                    }

                    // Add the products to be added after the iteration is complete
                    w.getListProduct().addAll(productsToAdd);
                }
                Iterator<Warehouse> warehouseExportListIterator = warehouseList.listExport.iterator();
                while (warehouseExportListIterator.hasNext()) {
                    Warehouse w = warehouseExportListIterator.next();

                    Iterator<Product> productIterator = w.getListProduct().iterator();
                    List<Product> productsToAdd = new ArrayList<>(); // Store products to be added

                    while (productIterator.hasNext()) {
                        Product p = productIterator.next();

                        if (p.getCode().equals(code)) {
                            if (p.getType().equals("daily") && p.getType().equals(newType)) {
                                DailyProduct dp = (DailyProduct) p;
                                dp.setName(newName);
                                dp.setPrice(newPrice);
                                dp.setType(newType);
                                dp.setSize(newSize);
                            } else if (p.getType().equals("daily") && !p.getType().equals(newType)) {
                                Product lp = new LongProduct(newManufactoringDate, newExpirationDate, p.getCode(), newName, newPrice, p.getQuantity(), newType);
                                productsToAdd.add(lp); // Add to the list of products to be added
                                productIterator.remove(); // Remove the current product
                            } else if (p.getType().equals("long") && p.getType().equals(newType)) {
                                LongProduct lp = (LongProduct) p;
                                lp.setName(newName);
                                lp.setPrice(newPrice);
                                lp.setManufacturingDate(newManufactoringDate);
                                lp.setExpirationDate(newExpirationDate);
                                lp.setType(newType);
                            } else if (p.getType().equals("long") && !p.getType().equals(newType)) {
                                Product dp = new DailyProduct(newSize, p.getCode(), newName, newPrice, p.getQuantity(), newType);
                                productsToAdd.add(dp); // Add to the list of products to be added
                                productIterator.remove(); // Remove the current product
                            }
                        }
                    }

                    // Add the products to be added after the iteration is complete
                    w.getListProduct().addAll(productsToAdd);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean remove(WarehouseList warehouseList) {
        String code = Utils.getString("Input product code to remove: ");
        boolean checkRemove = false, checkGenerate = true;
        boolean confirmRemove = Utils.confirmYesNo("Do you sure to remove product (Y or N): ");

        if (confirmRemove) {
            for (Warehouse w : warehouseList.listImport) {
                for (Product p : w.getListProduct()) {
                    if (p.getCode().equals(code)) {
                        checkGenerate = false;
                    }
                }
            }
            for (Warehouse w : warehouseList.listExport) {
                for (Product p : w.getListProduct()) {
                    if (p.getCode().equals(code)) {
                        checkGenerate = false;
                    }
                }
            }
            if (!checkGenerate) {
                System.out.println("Product has been generated into import or export receipt.");
            }
        }
        if (confirmRemove && checkGenerate) {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getCode().equals(code)) {
                    this.remove(this.get(i));
                    System.out.println("Remove successfully !!!");
                    checkRemove = true;
                }
            }
        }
        if (!checkRemove) {
            System.out.println("Remove fails !!!");
        }
        return checkRemove;
    }

    @Override
    public void show(WarehouseList warehouseList, FileList fileList) {
        boolean showOption = false;
        String show = Utils.getString("Do you want to show products in product.dat file or in product collection (A or B): ");
        if (show.toUpperCase().equals("A")) {
            showOption = true;
        } else {
            showOption = false;
        }

        if (showOption) {
            ProductList pl = new ProductList();
            fileList.importDataFromFile("product.dat", true, pl, warehouseList);
            System.out.println(pl.size());
            for (Product p : pl) {
                System.out.println(p);
            }
        } else if (!showOption) {
            for (Product p : this) {
                System.out.println(p);
            }
        }
    }

    @Override
    public Product getProductByCode(String code) {
        for (Product p : this) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }
}
