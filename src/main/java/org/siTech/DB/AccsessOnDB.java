package org.siTech.DB;

import org.siTech.model.BillItem;
import org.siTech.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccsessOnDB {
    Scanner scan = new Scanner(System.in);
    MariaDb mariaDb = new MariaDb();

    public BillItem getProduct(String barcode) throws SQLException {
        BillItem billItem = null;
        ResultSet resultSet = mariaDb.find(String.format("SELECT * FROM product WHERE barcode = '%s'", barcode));
        if (resultSet.next()) {
            billItem = new BillItem(resultSet.getString("barcode"),
                    resultSet.getDouble("price"),
                    resultSet.getString("product_name"));
        }
        return billItem;
    }



    public void addNewProduct() throws SQLException {
        System.out.println("dou you want to add it ? yes/no");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("enter barcode");
            String barcode = scan.next();
            System.out.println("enter name of product ");
            String product_name = scan.next();
            System.out.println("enter price ");
            double price = scan.nextDouble();
            insertNewProduct(new Product(barcode, price, product_name));

        }
    }


    public void insertNewProduct(Product product) throws SQLException {



        String barcode = product.getBarcode();
        String product_name = product.getName();
        double price = product.getPrice();

        ResultSet resultSet = mariaDb.find(String.format("INSERT INTO PRODUCT (barcode,product_name, price) VALUES ('%s','%s','%s')", barcode, product_name, price));




    }
}
