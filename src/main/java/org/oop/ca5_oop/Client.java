package org.oop.ca5_oop;

import javafx.application.Application;

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
            GUIAppMain appWindow = new GUIAppMain();
            
            // referenced from: https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html
            //                  https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html#launch-java.lang.Class-java.lang.String...-
            Application.launch(GUIAppMain.class);
            System.out.println("Here");

        } catch (Exception e){
            System.out.println("An error occurred:");
            System.out.println(e.getMessage());
        }
    }
}
