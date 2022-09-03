package org.siTech.model;

public class Product {
    private String barcode;
    private double price;
    private String name;


    public Product(String barcode, double price, String itemName) {
        this.barcode = barcode;
        this.price = price;
        this.name = itemName;
    }

    public Product() {
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }
}
