package org.oop.ca5_oop.Handlers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.oop.ca5_oop.DAO.ProductDao;
import org.oop.ca5_oop.DTO.Product;
import org.oop.ca5_oop.utils.ProductJsonConverter;
import org.oop.ca5_oop.Exception.DaoException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader socketReader;
    private PrintWriter socketWriter;
    private ProductDao productDao;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.productDao = new ProductDao();
    }

    @Override
    public void run() {
        try {
            socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            while ((request = socketReader.readLine()) != null && !request.equals("quit")) {
                System.out.println("Received request: " + request);

                if (request.equals("get allProducts")) {
                    List<Product> allProducts = productDao.listAllProducts();
                    String allProductsJSON = ProductJsonConverter.productsListToJsonString(allProducts);
                    socketWriter.println(allProductsJSON + "\nEND");
                }
                else if (request.startsWith("find")) {
                    int id = Integer.parseInt(request.split(" ")[1]);
                    Product product = productDao.getProductById(id);
                    if (product == null) {
                        socketWriter.println("NOT FOUND\nEND");
                    } else {
                        String productJSON = ProductJsonConverter.productToJsonString(product);
                        socketWriter.println(productJSON + "\nEND");
                    }
                }
                else if (request.startsWith("delete")) {
                    int id = Integer.parseInt(request.split(" ")[1]);
                    productDao.deleteProductById(id);
                }
                else if (request.startsWith("create")) {
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
                else if (request.startsWith("update")) {
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
                else if (request.equals("get images")) {
                    File folder = new File("server_images");
                    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") ||
                            name.toLowerCase().endsWith(".png") ||
                            name.toLowerCase().endsWith(".jpeg"));

                    if (files != null) {
                        JSONArray imageList = new JSONArray();
                        for (File file : files) {
                            imageList.put(file.getName());
                        }
                        socketWriter.println(imageList.toString());
                        socketWriter.println("END");
                    }
                }
                else if (request.startsWith("get image")) {
                    String fileName = request.substring(10).trim();
                    File file = new File("server_images/" + fileName);
                    if (file.exists()) {
                        OutputStream out = clientSocket.getOutputStream();
                        FileInputStream fileIn = new FileInputStream(file);

                        byte[] buffer = new byte[4096];
                        int count;
                        while ((count = fileIn.read(buffer)) > 0) {
                            out.write(buffer, 0, count);
                        }
                        fileIn.close();
                    } else {
                        socketWriter.println("ERROR: File not found");
                    }
                }
            }

            socketReader.close();
            socketWriter.close();
            clientSocket.close();
            System.out.println("Client disconnected.");

        } catch (IOException | DaoException e) {
            System.out.println("Client handler error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
