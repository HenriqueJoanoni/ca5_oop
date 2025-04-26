package org.oop.ca5_oop;

import org.json.JSONObject;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.Exception.DaoException;
import org.oop.ca5_oop.utils.ProductJsonConverter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.io.File;
import org.json.JSONArray;
import java.io.OutputStream;
import java.io.FileInputStream;


public class Server {
    final static int SERVER_PORT = 1024;
    private DataInputStream in = null;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();
                PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Server connection received");// start server to listen on port number 8888


                //DEAL WITH REQUESTS
                String request = "";
                ProductDao productDao = new ProductDao();
                while ((request = socketReader.readLine()) != null && !request.equals("quit")){
                    //get all products
                    System.out.println(request);
                    if (request.equals("get allProducts")){
                        System.out.println("print");

                        List<Product> allProducts = productDao.listAllProducts();
                        String allProductsJSON = ProductJsonConverter.productsListToJsonString(allProducts);
                        allProductsJSON += "\nEND";
                        socketWriter.println(allProductsJSON);
                    }


                    //find product by id
                    else if (request.startsWith("find")){
                        int id = Integer.parseInt(request.split(" ")[1]);
                        Product product = productDao.getProductById(id);
                        if (product == null){
                            socketWriter.println("NOT FOUND\nEND");
                        } else {
                            String productJSON = ProductJsonConverter.productToJsonString(product);
                            productJSON += "\nEND";
                            socketWriter.println(productJSON);
                        }
                    }


                    //delete by id
                    else if (request.startsWith("delete")){
                        int id = Integer.parseInt(request.split(" ")[1]);
                        productDao.deleteProductById(id);
                    }

                    //create new
                    else if (request.startsWith("create")){
                        String jsonString = request.substring(7).trim();
                        JSONObject object = new JSONObject(jsonString);
                        productDao.insertProduct(new Product(
                                object.getString("productName"),
                                object.getString("description"),
                                object.getFloat("price"),
                                object.getInt("qtyInStock"),
                                object.getString("product_sku")
                        ));
                    }

                    //edit product
                    else if (request.startsWith("update")){
                        String jsonString = request.substring(7).trim();
                        JSONObject object = new JSONObject(jsonString);
                        productDao.updateProduct(object.getInt("productID"), new Product(
                                object.getInt("productID"),
                                object.getString("productName"),
                                object.getString("description"),
                                object.getFloat("price"),
                                object.getInt("qtyInStock"),
                                object.getString("product_sku")
                        ));
                    }

                    //get images list
                    else if (request.equals("get images")) {
                        File folder = new File("server_images");
                        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") ||
                                name.toLowerCase().endsWith(".png") ||
                                name.toLowerCase().endsWith(".jpeg"));

                        JSONArray imageList = new JSONArray();
                        for (File file : files) {
                            imageList.put(file.getName());
                        }
                        socketWriter.println(imageList.toString());
                        socketWriter.println("END");
                    }
                    else if (request.startsWith("get image ")) {
                        String fileName = request.substring(10).trim();
                        File file = new File("server_images/" + fileName);
                        if (!file.exists()) {
                            socketWriter.println("ERROR: File not found");
                        } else {
                            OutputStream out = clientSocket.getOutputStream();
                            FileInputStream fileIn = new FileInputStream(file);

                            byte[] buffer = new byte[4096];
                            int count;
                            while ((count = fileIn.read(buffer)) > 0) {
                                out.write(buffer, 0, count);
                            }
                            fileIn.close();
                        }
                    }

                }


            }




        } catch (IOException e) {
            System.out.println("Server error");
        } catch (DaoException e){

        }


    }


}
