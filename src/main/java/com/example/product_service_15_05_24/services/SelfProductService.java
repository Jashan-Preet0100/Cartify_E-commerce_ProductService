package com.example.product_service_15_05_24.services;

import com.example.product_service_15_05_24.exceptions.ProductNotFoundException;
import com.example.product_service_15_05_24.models.Category;
import com.example.product_service_15_05_24.models.Product;
import com.example.product_service_15_05_24.repositories.CategoryRepository;
import com.example.product_service_15_05_24.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if(product == null){
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize,
                Sort.by(sortParam).descending()));
    }

    @Override
    public Product addProduct(String title, String description,
                              String imageUrl, String category, double price) {
        Product newProduct = new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        newProduct.setImageUrl(imageUrl);
        newProduct.setPrice(price);

        Category categoryfromDb = categoryRepository.findByTitle(category);

        if(categoryfromDb == null){
            Category newCategory = new Category();
            newCategory.setTitle(category);
            //categoryRepository.save(newCategory);
            categoryfromDb = newCategory;
        }

        newProduct.setCategory(categoryfromDb);
        Product savedProduct = productRepository.save(newProduct);
        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if(product == null){
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
        productRepository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(Long productId, String title, String description,
                                 String imageUrl, String category, double price) throws ProductNotFoundException {
        Product productInDb = productRepository.findByIdIs(productId);
        if(productInDb == null){
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }

        if(title != null){
            productInDb.setTitle(title);
        }
        if(description != null){
            productInDb.setDescription(description);
        }
        if(imageUrl != null){
            productInDb.setImageUrl(imageUrl);
        }
        if(price != 0){
            productInDb.setPrice(price);
        }
        if(category != null){
            Category categoryFromDb = categoryRepository.findByTitle(category);
            if(categoryFromDb == null){
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryFromDb = newCategory;
            }
            productInDb.setCategory(categoryFromDb);
        }
        return productRepository.save(productInDb);
    }

    @Override
    public Product replaceProduct(Long productId, String title, String description,
                                  String imageUrl, String category, double price) throws ProductNotFoundException {
        Product productInDb = productRepository.findByIdIs(productId);
        if(productInDb == null){
            throw new ProductNotFoundException(
                    "Product with id " + productId + " not found"
            );
        }
            productInDb.setTitle(title);

            productInDb.setDescription(description);

            productInDb.setImageUrl(imageUrl);

            productInDb.setPrice(price);

            Category categoryFromDb = categoryRepository.findByTitle(category);
            if(categoryFromDb == null){
                Category newCategory = new Category();
                newCategory.setTitle(category);
                categoryFromDb = newCategory;
            }
            productInDb.setCategory(categoryFromDb);

        return productRepository.save(productInDb);
    }
}
