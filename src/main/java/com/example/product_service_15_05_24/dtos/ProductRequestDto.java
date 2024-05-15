package com.example.product_service_15_05_24.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
}
