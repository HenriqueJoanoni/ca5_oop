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
        JSONObject jsonObject = new JSONObject(product);
        return jsonObject.toString(4);
    }

    public static String productsListToJsonString(List<Product> productList) {
        if (productList == null || productList.isEmpty()) {
            return "[]";
        }
        JSONArray jsonArray = new JSONArray(productList);
        return jsonArray.toString(4);
    }
}

