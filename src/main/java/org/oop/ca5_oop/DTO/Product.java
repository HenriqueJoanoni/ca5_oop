package org.oop.ca5_oop.DTO;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productID;
    private String productName;
    private String description;
    private float price;
    private int qtyInStock;
    private String product_sku;

    public Product(int productID, String productName, String description, float price, int qtyInStock, String product_sku) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.product_sku = product_sku;
    }

    //use this for instering
    public Product(String productName, String description, float price, int qtyInStock, String product_sku) {
        this(0, productName, description, price, qtyInStock, product_sku);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public void setProduct_sku(String product_sku) {
        this.product_sku = product_sku;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qtyInStock=" + qtyInStock +
                ", product_sku='" + product_sku + '\'' +
                '}';
    }
}

