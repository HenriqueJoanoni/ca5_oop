package org.oop.ca5_oop;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int SERVER_PORT = 1024;
    private DataInputStream in = null;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        while (true){
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                 Socket clientSocket = serverSocket.accept();       // start server to listen on port number 8888
                 PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            )
            {
                System.out.println("Server Running");

            } catch (Exception e){
                System.out.println("A server error occurred:");
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Server closed.");
            }
        }

    }
}
