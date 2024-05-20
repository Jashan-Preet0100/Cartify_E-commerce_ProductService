package com.example.product_service_15_05_24.repositories;

import com.example.product_service_15_05_24.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category save(Category category);
    Category findByTitle(String name);
}
