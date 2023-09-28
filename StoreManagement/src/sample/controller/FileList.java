/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.dto.I_FileList;
import sample.entities.Product;
import sample.entities.Warehouse;

/**
 *
 * @author LENOVO
 */
public class FileList implements I_FileList {

    @Override
    public void importDataFromFile(String fName, boolean check, ProductList productList, WarehouseList warehouseList) {
        try {
            File file = new File(fName);
            if (file.exists()) {
                // Khong lam gi
            } else {
                try {
                    // Tao mot file moi neu chua ton tai
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + e.getMessage());
                }
            }
            FileInputStream fis = new FileInputStream(file);

            if (file.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                List<Warehouse> whList = new ArrayList<>();

                while (true) {
                    if (check) {
                        try {
                            Product newProduct = (Product) ois.readObject();
                            productList.add(newProduct);
                        } catch (ClassNotFoundException e) {
                            System.out.println(e);
                        } catch (EOFException e) {
                            break;
                        }
                    } else if (!check) {
                        try {
                            Warehouse newWarehouse = (Warehouse) ois.readObject();
                            whList.add(newWarehouse);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(FileList.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (EOFException e) {
                            break;
                        }
                    }
                }
                if (!check) {
                    for (Warehouse w : whList) {
                        if (w.getCode().contains("I") && warehouseList.find(w.getCode(), true) < 0) {
                            warehouseList.listImport.add(w);
                        } else if (w.getCode().contains("E") && warehouseList.find(w.getCode(), false) < 0) {
                            warehouseList.listExport.add(w);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void saveDataToFile(String fName, boolean check, ProductList productList, WarehouseList warehouseList) {
        try {
            File file = new File(fName);
            if (file.exists()) {
                // Khong lam gi
            } else {
                try {
                    // Tao mot file moi neu chua ton tai
                    file.createNewFile();
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + e.getMessage());
                }
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            if (check) {
                for (Product p : productList) {
                    oos.writeObject(p);
                }
            } else if (!check) {
                for (Warehouse wl : warehouseList.listImport) {
                    oos.writeObject(wl);
                }
                for (Warehouse we : warehouseList.listExport) {
                    oos.writeObject(we);
                }
            }
            System.out.println("Save to file successfully !!!");
        } catch (IOException ex) {
            Logger.getLogger(FileList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
