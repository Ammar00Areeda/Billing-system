package org.siTech.common;

import org.siTech.callback.CallbackInterface;
import javax.imageio.IIOException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
public class SocketConnection implements CallbackInterface {



    public void callSocket() {

        ServerSocket server = null;
        int i = 1;
        try {
            server = new ServerSocket(8081);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                System.out.println("new client connection");
                clientHandler clientSock = new clientHandler(client, i, this);
                i += 1;
                new Thread(clientSock).start();
            }
        } catch (
                IIOException e) {
            e.printStackTrace();
        } catch (
                SocketException e) {
            throw new RuntimeException(e);
        } catch (
                IOException e) {

        } finally {
            if (server != null) {
                try {

                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void done(int id) {
        CallBackFPrint<String> callBackFPrint = new CallBackFPrint<>(" has been done");

        System.out.print(id);
        callBackFPrint.printCallBack();
    }
}

