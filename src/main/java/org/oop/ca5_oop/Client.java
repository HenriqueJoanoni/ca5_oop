package org.oop.ca5_oop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    final static int SERVER_PORT = 1024;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }


    public void start(){
        try (Socket socket = new Socket("localhost", SERVER_PORT)){
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

        } catch (Exception e){
            System.out.println("An error occurred:");
            System.out.println(e.getMessage());
        }
    }
}
