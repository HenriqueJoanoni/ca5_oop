package org.oop.ca5_oop.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.oop.ca5_oop.DTO.Product;

import java.util.List;

public class ProductJsonConverter {

    public static String productToJsonString(Product product) {
        if (product == null) {
            return "{}";
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productID", product.getProductID());
        jsonObject.put("productName", product.getProductName());
        jsonObject.put("description", product.getDescription());
        jsonObject.put("price", product.getPrice());
        jsonObject.put("qtyInStock", product.getQtyInStock());
        jsonObject.put("product_sku", product.getProduct_sku());
        jsonObject.put("imageName", product.getImageName());

        return jsonObject.toString(4);
    }

    public static String productsListToJsonString(List<Product> productList) {
        if (productList == null || productList.isEmpty()) {
            return "[]";
        }

        JSONArray jsonArray = new JSONArray();
        for (Product product : productList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productID", product.getProductID());
            jsonObject.put("productName", product.getProductName());
            jsonObject.put("description", product.getDescription());
            jsonObject.put("price", product.getPrice());
            jsonObject.put("qtyInStock", product.getQtyInStock());
            jsonObject.put("product_sku", product.getProduct_sku());
            jsonObject.put("imageName", product.getImageName());
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString(4);
    }
}
