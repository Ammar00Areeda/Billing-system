package org.siTech.common;

import org.siTech.model.BillItem;
import org.siTech.model.Product;

import java.util.ArrayList;
import java.util.Date;

public class PrintInTxtFile {
    private String marketName;
    private String phoneNumber;
    protected FileManager fileManager;
    ArrayList<Product> colliction = new ArrayList<>();


    public PrintInTxtFile(FileManager fileManager, String marketName, String phoneNumber) {
        this.fileManager = fileManager;
        this.marketName = marketName;
        this.phoneNumber = phoneNumber;
    }

    ArrayList<BillItem> listOfItemsFromBilling = new ArrayList<BillItem>();

    public void setListOfItemsFromBilling(ArrayList<BillItem> listOfItemsFromBilling) {
        this.listOfItemsFromBilling = listOfItemsFromBilling;
    }

    public void header() {

        for (int i = 0; i < 31; i++) {
            fileManager.exportToFile("#");
        }
        fileManager.exportToFile("\n");
        fileManager.exportToFile("      Billing system           ");
        fileManager.exportToFile("\n");
        for (int i = 0; i < 31; i++) {
            fileManager.exportToFile("#");
        }
        fileManager.exportToFile("\n");
        fileManager.exportToFile("\n BIll Date : " + new Date());
        fileManager.exportToFile("\n Branch : " + marketName);
        fileManager.exportToFile("\n Branch mobile : " + phoneNumber);
        fileManager.exportToFile("\n ");
        for (int i = 0; i < 31; i++) {
            fileManager.exportToFile("#");
        }
        fileManager.exportToFile("\n");
        fileManager.exportToFile("\n item\t|\tDescription\t\t\t|\tAmount\t|\tPrice (JOD)");
    }

    public double total(ArrayList<BillItem> listOfItemsFromBilling) {
        double total = 0;
        for (BillItem item : listOfItemsFromBilling) {
            total += item.getPrice() * item.getQuantity();
        }

        return total;
    }

    public double tax(ArrayList<BillItem> listOfItemsFromBilling, double tax) {
        double totalWithTax = 0;
        totalWithTax = total(listOfItemsFromBilling) * tax;
        return totalWithTax;
    }

    public double service(ArrayList<BillItem> listOfItemsFromBilling) {
        double totalWithService = 0;
        totalWithService = tax(listOfItemsFromBilling, 1.6) + 2;
        return totalWithService;
    }

    public void printTable() {
        for (int i = 0; i < listOfItemsFromBilling.size(); i++) {
            fileManager.exportToFile("\n" + (i + 1) + "\t\t|\t" + listOfItemsFromBilling.get(i).getName() + "\t\t\t|" + listOfItemsFromBilling.get(i).getQuantity() + "\t\t\t\t" + listOfItemsFromBilling.get(i).getPrice() * listOfItemsFromBilling.get(i).getQuantity());
        }
    }

    public void footer(ArrayList<BillItem> listOfItemsFromBilling) {
        fileManager.exportToFile("\n total = " + total(listOfItemsFromBilling));
        fileManager.exportToFile("\ntotal tax = " + tax(listOfItemsFromBilling, 0.16));
    }

    public void printBill(ArrayList<BillItem> itemsFromMain) {
        header();
        printTable();
        footer(listOfItemsFromBilling);
    }
    public void customerPHoneNum(String phoneNumber){

        fileManager.exportToFile("\n costumer phone number is :" +phoneNumber);

    }public void customerEmail(String email){

        fileManager.exportToFile("\n costumer Email is :" +email);
    }
}
