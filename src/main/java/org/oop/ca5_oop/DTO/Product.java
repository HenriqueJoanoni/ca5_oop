package org.oop.ca5_oop.DTO;

public class Product {
    private int productID;
    private String productName;
    private String description;
    private float price;
    private int qtyInStock;
    private String product_sku;
    private String imageName;

    public Product(
            int productID,
            String productName,
            String description,
            float price,
            int qtyInStock,
            String product_sku,
            String imageName
    ) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.qtyInStock = qtyInStock;
        this.product_sku = product_sku;
        this.imageName = imageName;
    }

    public Product(
            String productName,
            String description,
            float price,
            int qtyInStock,
            String product_sku,
            String imageName
    ) {
        this(0, productName, description, price, qtyInStock, product_sku, imageName);
    }

    public Product() {
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Product [" +
                "ID: " + productID +
                ", Name: " + productName +
                ", Description: " + description +
                ", Price: " + String.format("%.2f", price) +
                ", Quantity in Stock: " + qtyInStock +
                ", SKU: " + product_sku +
                ", Image Name: " + imageName +
                "]";
    }
}
