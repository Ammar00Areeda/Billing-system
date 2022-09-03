package org.siTech.common;

public class CallBackFPrint <T> {
    T thingToPrint;
    public CallBackFPrint (T thingToPrint){
        this.thingToPrint = thingToPrint;
    }

    public void printCallBack(){

        System.out.println(thingToPrint);
    }

}
