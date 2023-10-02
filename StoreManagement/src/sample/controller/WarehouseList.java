/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import sample.entities.DailyProduct;
import sample.dto.I_WarehouseList;
import sample.entities.LongProduct;
import sample.entities.Product;
import sample.entities.Warehouse;
import sample.utils.Utils;

/**
 *
 * @author LENOVO
 */
public class WarehouseList implements I_WarehouseList {

    public List<Warehouse> listImport;
    public List<Warehouse> listExport;

    public WarehouseList() {
        listImport = new ArrayList<>();
        listExport = new ArrayList<>();
    }

    @Override
    public int find(String code, boolean option) {
        int index = -1;
        if (option) {
            for (int i = 0; i < listImport.size(); i++) {
                if (listImport.get(i).getCode().equals(code)) {
                    index = i;
                    break;
                }
            }
        } else if (!option) {
            for (int i = 0; i < listExport.size(); i++) {
                if (listExport.get(i).getCode().equals(code)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    @Override
    public Warehouse inputReceipt(boolean option, ProductList productList) {
        String code = "";
        int end_code;
        if (option) {
            code += "I";    //Ixxxxxxx
            end_code = listImport.size() + 1;

        } else {
            code += "E";    //Exxxxxxx
            end_code = listExport.size() + 1;
        }
        if (end_code > 999999) {
            System.out.println("Warehouse information is full !!!");
        }
        int number_zero = 7 - (end_code + "").length();
        for (int i = 1; i <= number_zero; i++) {
            code += "0";
        }
        code += end_code;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);

        List<Product> listWarehouseProduct = new ArrayList<>();

        boolean check = false;
        do {
            String productCode = Utils.getString("Input product code: ");
            Product p = productList.getProductByCode(productCode);
            if (p == null) {
                System.out.println("Product does not exist.");
            } else if (listWarehouseProduct.contains(p)) {
                System.out.println("Product is exists in receipt.");
            } else if (p.getQuantity() < 1 && option) {
                System.out.println("Product is out of stock.");
            } else {
                if (option) {
                    int productQuantity = 0;
                    productQuantity = Utils.getInt("Input product quantity: ", 1, p.getQuantity());
                    if (p.getType().equals("daily")) {
                        DailyProduct dp = (DailyProduct) p;
                        Product nProduct = new DailyProduct(dp.getSize(), dp.getCode(), dp.getName(), dp.getPrice(), dp.getQuantity(), dp.getType());
                        nProduct.setQuantity(productQuantity);
                        listWarehouseProduct.add(nProduct);
                    } else if (p.getType().equals("long")) {
                        LongProduct lp = (LongProduct) p;
                        Product nProduct = new LongProduct(lp.getManufacturingDate(), lp.getExpirationDate(), lp.getCode(), lp.getName(), lp.getPrice(), productQuantity, lp.getType());
                        nProduct.setQuantity(productQuantity);
                        listWarehouseProduct.add(nProduct);
                    }
                    p.setQuantity(p.getQuantity() - productQuantity);
                } else if (!option) {

                    List<Warehouse> menuWarehouse = new ArrayList<>();
                    for (Warehouse w : listImport) {
                        for (Product pr : w.getListProduct()) {
                            if (pr.getCode().equals(productCode)) {
                                menuWarehouse.add(w);
                            }
                        }
                    }
                    for (Warehouse outWH : menuWarehouse) {
                        for (Product pr : outWH.getListProduct()) {
                            if (pr.getCode().equals(productCode)) {
                                int i = 1;
                                System.out.println(i + " " + "Warehouse [Code: " + outWH.getCode() + ", " + pr.toString());
                                i++;
                            }
                        }
                    }
                    int choice = Utils.getInt("Input to choose warehouse to export: ", 1, menuWarehouse.size());
                    int productQuantity = 0;
                    for (Product pr : menuWarehouse.get(choice - 1).getListProduct()) {
                        if (pr.getCode().equals(productCode)) {
                            productQuantity = Utils.getInt("Input product quantity: ", 1, pr.getQuantity());
                            pr.setQuantity(pr.getQuantity() - productQuantity);
                        }
                    }

                    if (p.getType().equals("daily")) {
                        DailyProduct dp = (DailyProduct) p;
                        Product nProduct = new DailyProduct(dp.getSize(), dp.getCode(), dp.getName(), dp.getPrice(), dp.getQuantity(), dp.getType());
                        nProduct.setQuantity(productQuantity);
                        listWarehouseProduct.add(nProduct);
                    } else if (p.getType().equals("long")) {
                        LongProduct lp = (LongProduct) p;
                        Product nProduct = new LongProduct(lp.getManufacturingDate(), lp.getExpirationDate(), lp.getCode(), lp.getName(), lp.getPrice(), productQuantity, lp.getType());
                        nProduct.setQuantity(productQuantity);
                        listWarehouseProduct.add(nProduct);
                    }
                }
                check = Utils.confirmYesNo("Do you want to continue adding product (Y or N): ");
            }
        } while (check);

        Warehouse receipt = new Warehouse(code, time, listWarehouseProduct);
        return receipt;
    }

    @Override
    public void createAnImportReceipt(ProductList productList) {
        Warehouse importReceipt = inputReceipt(true, productList);
        listImport.add(importReceipt);
    }

    @Override
    public void createAnExportReceipt(ProductList productList) {
        Warehouse exportReceipt = inputReceipt(false, productList);
        listExport.add(exportReceipt);
    }

}
