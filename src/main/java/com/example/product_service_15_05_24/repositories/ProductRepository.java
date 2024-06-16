package com.example.product_service_15_05_24.repositories;

import com.example.product_service_15_05_24.models.Category;
import com.example.product_service_15_05_24.models.Product;
import com.example.product_service_15_05_24.repositories.projections.ProductProjection;
import com.example.product_service_15_05_24.repositories.projections.ProductWithIdAndTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product save(Product product);
    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Product findByIdIs(Long id);

    // testing HQL queries
    List<Product> findAllByCategory_Title(String title);

    // In this we can get all the information from product along with title like price, description etc
    @Query("select p from Product p where p.category.title = :categoryName")
    List<Product> getProductWithCategoryName(String categoryName);

    // In this only title can be retrieved
    @Query("select p.title from Product p where p.category.title = :categoryName")
    List<String> someTitleMethod(String categoryName);

    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    List<ProductWithIdAndTitle> someMethod1(String categoryName);

    @Query("select p.id as id, p.title as title from Product p where p.category.title = :categoryName")
    List<ProductProjection> someMethod2(String categoryName);

    // Testing Native SQL Queries
    @Query(value = "select * from Product p where p.id = :id", nativeQuery = true)
    Product someNativeQuery(Long id);

}
