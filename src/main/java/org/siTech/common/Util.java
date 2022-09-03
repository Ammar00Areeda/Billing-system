package org.siTech.common;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public final class Util {

    static List<String> mylist = new ArrayList<>();
    private static final String BARCODE_REGEX = "(^[a-z]{2})([a-zA-z0-9]{7})#$";
    private static final String MOBILE_NUMBER_REGEX = "^962 7([0-9]){2}-([0-9]){4}$";
    private static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final String LINK_REGEX = "(https://|www[.]|http://)[A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]*";



    public static boolean isValidText(String regex , String text){
         Pattern pt = Pattern.compile(regex);
         Matcher mt = pt.matcher(text);
        boolean result = mt.matches();
        return result;
    }

    //Accept the following format 000 000-00000
    public static boolean isdValidMobileNumber(String mobileNumber){
       return isValidText(MOBILE_NUMBER_REGEX,mobileNumber);
    }
    //Accept the following format ________@______.___
    public static boolean isdValidEmail(String email){
       return isValidText(EMAIL_REGEX,email);
    }
    //Accept the following format ________@______.___
    public static boolean isdValidBarcode(String barcode){
       return isValidText(BARCODE_REGEX,barcode);
    }


    public static void searchInText(String regex , String text){
        Pattern pt = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher mt = pt.matcher(text);
        while (mt.find()){
            mylist.add(text.substring(mt.start(0), mt.end(0)));
        }
        if (mylist.size() == 0){
            System.out.println("Empty list");

        }
        for (String str:mylist)
            System.out.println(str);




    }
    public static void findInText(String text){
        searchInText(LINK_REGEX, text);
    }


























}

