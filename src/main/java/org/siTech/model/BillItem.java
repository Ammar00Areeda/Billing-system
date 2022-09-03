package org.siTech.model;

public class BillItem extends Product {
    private double quantity = 1;

    public BillItem(String barcode, double price, String itemName) {
        super(barcode, price, itemName);

    }

    public BillItem(double quantity) {
        this.quantity = quantity;
    }

    public void calQua() {
        quantity += 1;
    }

    public double getQuantity() {
        return quantity;
    }

    public String toString() {
        return "barcode: " + getBarcode();
    }
}

