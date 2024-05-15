package com.example.product_service_15_05_24.services;

import com.example.product_service_15_05_24.exceptions.ProductNotFoundException;
import com.example.product_service_15_05_24.models.Product;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public List<Product> getAllProducts();

    public Product addProduct(
            String title,
            String description,
            String imageUrl,
            String category,
            double price);

    public Product deleteProduct(Long productId) throws ProductNotFoundException;

    public Product updateProduct(
            Long productId,
            String title,
            String description,
            String imageUrl,
            String category,
            double price) throws ProductNotFoundException;

    public Product replaceProduct(
            Long productId,
            String title,
            String description,
            String imageUrl,
            String category,
            double price) throws ProductNotFoundException;

}
