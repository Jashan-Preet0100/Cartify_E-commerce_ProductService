package com.example.product_service_15_05_24;

import com.example.product_service_15_05_24.models.Category;
import com.example.product_service_15_05_24.models.Product;
import com.example.product_service_15_05_24.repositories.CategoryRepository;
import com.example.product_service_15_05_24.repositories.ProductRepository;
import com.example.product_service_15_05_24.repositories.projections.ProductProjection;
import com.example.product_service_15_05_24.repositories.projections.ProductWithIdAndTitle;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductService150524ApplicationTests {

	// 3 Ways to Dependency Injection , 1. Constructor (use this first) 2. Setter Method
	// 3. Autowired
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void contextLoads() {
	}


	@Test
	void testJpaDeclaredJoin() {
		List<Product> products = productRepository.findAllByCategory_Title("new Electronics");
		for (Product product : products){
			System.out.print(product.getTitle());
		}
	}

	@Test
	void testHQL(){
		List<Product> products = productRepository.getProductWithCategoryName("new Electronics");
		for (Product product : products){
			System.out.println(product.getTitle());
		}
	}

	@Test
	void testSpecificField(){
		List<String> productTitles = productRepository.someTitleMethod("new Electronics");
		for (String productTitle : productTitles){
			System.out.println(productTitle);
		}
	}

	@Test
	void testProjections(){
		List<ProductWithIdAndTitle> productWithIdAndTitles = productRepository.someMethod1("old Electronics");
		for (ProductWithIdAndTitle productWithIdAndTitle : productWithIdAndTitles){
			System.out.print(productWithIdAndTitle.getTitle()+" id is ");
			System.out.print(productWithIdAndTitle.getId());
		}
	}

	@Test
	void testProductProjections(){
		List<ProductProjection> productProjections = productRepository.someMethod2("old Electronics");
		for (ProductProjection pp : productProjections){
			System.out.print(pp.getTitle()+" id is ");
			System.out.print(pp.getId());
		}
	}

	/*@Test
	void testNativeSql(){
		Product product = productRepository.someNativeQuery(1L);
		System.out.println(product.getTitle());
		System.out.println(product.getDescription());
		System.out.println(product.getPrice());
		System.out.println(product.getImageUrl());
	}*/

	@Test
	@Transactional // Use only when fetch type is lazy
		// (In LAZY fetch type after one DB call session gets terminated Therefore, using transactional will not allow session to get terminated)
	void testFetchType(){
		Optional<Category> category = categoryRepository.findById(5L); // 1st DB Call
		if(category.isPresent()){
			System.out.println(category.get().getTitle());
			List<Product> products = category.get().getProducts(); // 2nd DB call
			for (Product product : products){
				System.out.println(product.getTitle());
			}
		}
	}

	@Test
	@Transactional
	void testFetchMode(){
		List<Category> categories = categoryRepository.findByTitleEndingWith("Electronics");
		for (Category category : categories){
			System.out.println(category.getTitle());
			List<Product> products = category.getProducts();
			for (Product product : products){
				System.out.println(product.getTitle());
			}
		}
	}
}
