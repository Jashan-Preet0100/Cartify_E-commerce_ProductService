package com.example.product_service_15_05_24.repositories.projections;

import com.example.product_service_15_05_24.models.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    String getImageUrl();
    Category getCategory();
}
