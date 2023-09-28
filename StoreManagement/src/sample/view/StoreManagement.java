/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.view;

import sample.dto.I_Menu;
import sample.controller.Menu;
import sample.service.ServiceManager;

/**
 *
 * @author Lam Tien Hung
 */
public class StoreManagement {

    /**
     * @param args the command line arguments
     */
    static ServiceManager serviceManager = new ServiceManager();

    public static void main(String[] args) {
        // TODO code application logic here

        I_Menu menu = new Menu();
        menu.addItem("1. Product Manager");
        menu.addItem("2. Warehouse Manager");
        menu.addItem("3. Report");
        menu.addItem("4. Store data to files");
        menu.addItem("5. Quit");
        int choice;
        boolean cont = false;
        do {
            System.out.println("========== STORE MANAGEMENT ==========");
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    ProductMenu();
                    break;
                case 2:
                    WarehouseMenu();
                    break;
                case 3:
                    ReportMenu();
                    break;
                case 4:
                    serviceManager.saveFile();
                    
                    break;
                case 5:
                    cont = menu.confirmYesNo("Do you want to quit? (Y/N): ");
                    break;
            }
        } while (!cont);
    }

    public static void ProductMenu() {
        I_Menu menu = new Menu();
        menu.addItem("1. Add a product");
        menu.addItem("2. Update product information");
        menu.addItem("3. Delete product");
        menu.addItem("4. Show all product");
        menu.addItem("5. Quit");
        int choice;
        boolean cont = false;
        do {
            System.out.println("========== PRODUCT MANAGEMENT ==========");
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    serviceManager.addProduct();
                    break;
                case 2:
                    serviceManager.updateProduct();
                    break;
                case 3:
                    serviceManager.deleteProduct();
                    break;
                case 4:
                    serviceManager.showAllProduct();
                    break;
                case 5:
                    cont = menu.confirmYesNo("Do you want to quit? (Y/N): ");
                    break;
            }
        } while (!cont);
    }

    public static void WarehouseMenu() {
        I_Menu menu = new Menu();
        menu.addItem("1. Create an import receipt");
        menu.addItem("2. Create an export receipt");
        menu.addItem("3. Quit");
        int choice;
        boolean cont = false;

        do {
            System.out.println("========== WAREHOUSE MANAGEMENT ==========");
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    serviceManager.createImportReceipt();
                    break;
                case 2:
                    serviceManager.createExportReceipt();
                    break;
                case 3:
                    cont = menu.confirmYesNo("Do you want to quit? (Y/N): ");
                    break;
            }
        } while (!cont);
    }

    public static void ReportMenu() {
        I_Menu menu = new Menu();
        menu.addItem("1. Show products that have expired");
        menu.addItem("2. Show products that the store is selling");
        menu.addItem("3. Show products that are running out of stock");
        menu.addItem("4. Show import/export receipt of a product");
        menu.addItem("5. Quit");
        int choice;
        boolean cont = false;
        do {
            System.out.println("========== REPORT MANAGEMENT ==========");
            menu.showMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    serviceManager.showProductExpired();
                    break;
                case 2:
                    serviceManager.showProductSelling();
                    break;
                case 3:
                    serviceManager.showProductRunningOut();
                    break;
                case 4:
                    serviceManager.showReceiptProduct();
                    break;
                case 5:
                    cont = menu.confirmYesNo("Do you want to quit? (Y or N): ");
                    break;
            }
        } while (!cont);
    }

}
