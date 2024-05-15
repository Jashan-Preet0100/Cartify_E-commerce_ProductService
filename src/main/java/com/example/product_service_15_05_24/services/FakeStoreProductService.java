package com.example.product_service_15_05_24.services;

import com.example.product_service_15_05_24.dtos.FakeStoreDto;
import com.example.product_service_15_05_24.exceptions.ProductNotFoundException;
import com.example.product_service_15_05_24.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException{
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );

        if(fakeStoreDto == null){
            throw new ProductNotFoundException(
                    "Product with id "+ productId + " not found"
                    +" try a product with id less than 21");
        }

        return fakeStoreDto.toProduct();
        //one dto to anther dto directly
    }

    @Override
    public List<Product> getAllProducts(){
        FakeStoreDto[] fakeStoreDtos = restTemplate.getForObject(
                "http://fakestoreapi.com/products/",
                FakeStoreDto[].class
        );

        // CONVERT ALL FAKESTORE DTOS TO PRODUCT OBJECT
        List<Product> products = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto : fakeStoreDtos){
            products.add(fakeStoreDto.toProduct());
        }

        return products;
    }

    @Override
    public Product addProduct(String title,
                              String description,
                              String imageUrl,
                              String category,
                              double price) {
        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(imageUrl);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        FakeStoreDto response = restTemplate.postForObject(
                "http://fakestoreapi.com/products",
                requestDto,
                FakeStoreDto.class
        );

        return response.toProduct();
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "http://fakestoreapi.com/products/" + productId,
                FakeStoreDto.class
        );

        if(fakeStoreDto == null){
            throw new ProductNotFoundException(
                    "Product with id "+ productId + " not found"
                            +" try a product with id less than 21");
        }

        return fakeStoreDto.toProduct();
    }

    @Override
    public Product updateProduct(Long productId,
                                 String title,
                                 String description,
                                 String imageUrl,
                                 String category,
                                 double price) throws ProductNotFoundException {
       FakeStoreDto requestDto = new FakeStoreDto();
       requestDto.setTitle(title);
       requestDto.setDescription(description);
       requestDto.setImage(imageUrl);
       requestDto.setCategory(category);
       requestDto.setPrice(price);

       FakeStoreDto response = requestDto;
       response.setId(productId);
       return response.toProduct();
    }

    @Override
    public Product replaceProduct(Long productId,
                                  String title,
                                  String description,
                                  String imageUrl,
                                  String category,
                                  double price) throws ProductNotFoundException {

        FakeStoreDto requestDto = new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(imageUrl);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto);

        FakeStoreDto response = restTemplate.getForObject(
                "http://fakestoreapi.com/products"+productId,
                FakeStoreDto.class
        );
        if(response == null){
            throw new ProductNotFoundException(
                    "Product with id "+ productId + " not found"
                            +" try a product with id less than 21");
        }

        return response.toProduct();
    }
}
