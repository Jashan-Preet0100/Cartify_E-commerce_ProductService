package com.example.product_service_15_05_24.repositories;

import com.example.product_service_15_05_24.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
    List<Product> findAll();
    Product findByIdIs(Long id);
}
