package com.example.product_service_15_05_24.controllers;

import com.example.product_service_15_05_24.dtos.ProductRequestDto;
import com.example.product_service_15_05_24.dtos.ProductResponseDto;
import com.example.product_service_15_05_24.exceptions.ProductNotFoundException;
import com.example.product_service_15_05_24.models.Product;
import com.example.product_service_15_05_24.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService, ModelMapper modelMapper){
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private ProductResponseDto convertToProductResponseDto(Product product){
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductDetails(@PathVariable("id") Long productId)
            throws ProductNotFoundException{
        Product product = productService.getSingleProduct(productId);
        return convertToProductResponseDto(product);
    }

    /*
    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return productResponseDtos;
    }

     */

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sortBy") String sortParam){
        Page<Product> products = productService.getAllProducts(pageNumber, pageSize, sortParam);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        products.forEach(product -> productResponseDtos.add(convertToProductResponseDto(product)));
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }


    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createNewProduct(@RequestBody ProductRequestDto productRequestDto){
        Product product = productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImageUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );

        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long productId)
        throws ProductNotFoundException{
        Product product = productService.deleteProduct(productId);
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long productId,
                                                            @RequestBody ProductRequestDto productRequestDto)
        throws ProductNotFoundException{
        Product product = productService.updateProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImageUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice() );
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") Long productId,
                                                             @RequestBody ProductRequestDto productRequestDto)
        throws ProductNotFoundException{
        Product product = productService.replaceProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImageUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice() );
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }
}
