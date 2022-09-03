package org.siTech.common;

import org.siTech.DB.AccsessOnDB;
import org.siTech.DB.MariaDb;
import org.siTech.callback.CallbackInterface;
import org.siTech.model.BillItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.siTech.common.Util.findInText;

public class clientHandler implements Runnable {
    AccsessOnDB accsessOnDB = new AccsessOnDB();
    private int id;
    private Socket clientSocket;
    FileManager fileManager;
    ArrayList<BillItem> products = new ArrayList<>();
    LocalDateTime date = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
    String formattedDate = date.format(myFormatObj);
    CallbackInterface callbackInterface;
    Scanner scanner = new Scanner(System.in);


    public clientHandler(Socket socket, int id, CallbackInterface callbackInterface) {
        this.clientSocket = socket;
        this.id = id;
        this.callbackInterface = callbackInterface;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String msgFromClient;
            whileMethod(out, in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    in.close();
                }
                if (in != null)
                    in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void whileMethod(PrintWriter out, BufferedReader in) throws IOException {
        String msgFromClient;

        while ((msgFromClient = in.readLine()) != null) {
            System.out.printf("sent from client %s : %s\n", id, msgFromClient);
            out.println(msgFromClient);


            if (Util.isdValidBarcode( msgFromClient ) == true || msgFromClient.equalsIgnoreCase("buy")) {


                MariaDb mariaDb = MariaDb.getDbInstance();
                try {
                    if (msgFromClient == null || msgFromClient.length() == 0) {
                        continue;
                    }

//                    if (msgFromClient.equalsIgnoreCase("buy")) {
//                        break;
//                    }
                    BillItem billItem = accsessOnDB.getProduct(msgFromClient);
                    if (billItem != null) {
                        if (products.isEmpty()) {
                            products.add(billItem);
                        } else {
                            boolean shouldAddItem = true;
                            for (int i = 0; i < products.size(); i++) {
                                if (products.get(i).getBarcode().equals(billItem.getBarcode())) {
                                    products.get(i).calQua();
                                    shouldAddItem = false;
                                }
                            }
                            if (shouldAddItem) {
                                products.add(billItem);
                            }
                        }
                    } else if (msgFromClient.equalsIgnoreCase("buy")) {

                        fileManager = new FileManager(String.format("d:\\%s.txt", formattedDate));
                        PrintInTxtFile print = new PrintInTxtFile(fileManager, "tajMall", "0790989832");
                        print.setListOfItemsFromBilling(products);
                        print.printBill(products);
                        System.out.println("Please inter Phone number");
                        String costumerPhoneNum = scanner.nextLine();
                        while (Util.isdValidMobileNumber(costumerPhoneNum) != true) {
                            System.out.println("Please enter Phone number again");
                             costumerPhoneNum = scanner.nextLine();
                        }
                        print.customerPHoneNum(costumerPhoneNum);
                        System.out.println("Please enter your Email");
                        String costumerEmail = scanner.nextLine();
//                        boolean b = Utility.isValidMobileNumber("9988787");
                        while (Util.isdValidEmail(costumerEmail) != true) {
                            System.out.println("Please inter your Email again");
                            costumerEmail = scanner.nextLine();
                        }
                        print.customerEmail(costumerEmail);
                         String text = "wqreqwrwqer www.google.com?087/gh8kj sdafsdf";
                        findInText(text);
                        fileManager.close();

                        callbackInterface.done(id);
                        break;


                    }else {
                        System.out.println("Product is not exist in the system we need to add this product");
                        accsessOnDB.addNewProduct();
                    }



                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }



            } else {
                System.out.println("resend it ");
            }
        }

    }


}

