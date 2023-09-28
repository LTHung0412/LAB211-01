/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.dto.I_ReportList;
import sample.dto.LongProduct;
import sample.dto.Product;
import sample.dto.Warehouse;
import sample.utils.Utils;

/**
 *
 * @author LENOVO
 */
public class ReportList implements I_ReportList {

    @Override
    public int compareDate(String dateFormat, String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d1 = null, d2 = null;
        try {
            d1 = sdf.parse(date1);
            d2 = sdf.parse(date2);

        } catch (ParseException ex) {
            Logger.getLogger(WarehouseList.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (d1.before(d2)) {
            return -1;
        } else if (d1.after(d2)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void showProductsExpired(ProductList productList) {
        List<Product> listProductsExpired = new ArrayList<>();

        try {
            for (Product p : productList) {
                if (p.getClass() == Class.forName("sample.dto.LongProduct")) {
                    LongProduct lp = (LongProduct) p;
                    int comparison = compareDate("MM/dd/yyyy", lp.getManufacturingDate(), lp.getExpirationDate());
                    if (comparison > 0) {
                        listProductsExpired.add(lp);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        for (Product p : listProductsExpired) {
            LongProduct lp = (LongProduct) p;
            System.out.println(lp);
        }
    }

    @Override
    public void showProductsSelling(ProductList productList) {
        List<Product> listProductsSelling = new ArrayList<>();

        try {
            for (Product p : productList) {
                if (p.getClass() == Class.forName("sample.dto.LongProduct")) {
                    LongProduct lp = (LongProduct) p;
                    int comparison = compareDate("MM/dd/yyyy", lp.getManufacturingDate(), lp.getExpirationDate());
                    if (comparison <= 0) {
                        listProductsSelling.add(p);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        for (Product p : listProductsSelling) {
            LongProduct lp = (LongProduct) p;
            System.out.println(lp);
        }
    }

    @Override
    public void showProductsRunningOut(ProductList productList) {
        List<Product> listProductsRunningOut = new ArrayList<>();
        try {
            for (Product p : productList) {
                if (p.getClass() == Class.forName("sample.dto.LongProduct")) {
                    LongProduct lp = (LongProduct) p;
                    int comparison = compareDate("MM/dd/yyyy", lp.getManufacturingDate(), lp.getExpirationDate());
                    if (comparison <= 0) {
                        listProductsRunningOut.add(p);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        Collections.sort(listProductsRunningOut, Comparator.comparing((Product p) -> p.getQuantity()));

        for (Product p : listProductsRunningOut) {
            LongProduct lp = (LongProduct) p;
            System.out.println(lp);
        }
    }

    @Override
    public void showReceiptProduct(ProductList productList, WarehouseList warehouseList, FileList fileList) {
        boolean showOption = false;
        String show = Utils.getString("Do you want to show product's data in warehouse.dat or warehouse's collection file (A or B): ");
        if (show.toUpperCase().equals("A")) {
            showOption = true;
        } else {
            showOption = false;
        }
        if (showOption) {
            WarehouseList wl = new WarehouseList();
            fileList.importDataFromFile("product.dat", true, productList, warehouseList);
            fileList.importDataFromFile("warehouse.dat", false, productList, warehouseList);
            getProductsInWarehouse(productList, warehouseList);
        } else if (!showOption) {
            getProductsInWarehouse(productList, warehouseList);
        }
    }

    public void getProductsInWarehouse(ProductList productList, WarehouseList warehouseList) {

        String code;

        code = Utils.getString("Input product code: ");
        int index = productList.find(code);
        if (index < 0 || index > productList.size()) {
            System.out.println("Product does not exist.");
            return;
        }
        for (Warehouse w : warehouseList.listImport) {
            for (Product p : w.getListProduct()) {
                if (p.getCode().equals(code)) {
                    String output = String.format("Warehouse [Code: %s, Time: %s, %s]", w.getCode(), w.getTime(), p.toString());
                    System.out.println(output);
                }
            }
        }
        for (Warehouse w : warehouseList.listExport) {
            for (Product p : w.getListProduct()) {
                if (p.getCode().equals(code)) {
                    String output = String.format("Warehouse [Code: %s, Time: %s, %s]", w.getCode(), w.getTime(), p.toString());
                    System.out.println(output);
                }

            }
        }
    }
}