package com.project.ProductService.Repository;

import com.project.ProductService.Model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByCategory(String category);
    List<ProductModel> findByPriceBetween(Double minPrice, Double maxPrice);
    //List<ProductModel> findBySellerId(String sellerId);


    List<ProductModel> findByNameContainingIgnoreCase(String name);

    List<ProductModel> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);

//    List<ProductModel> findByNameContaining(String name);
//
//    List<ProductModel> findByNameContainingAndCategoryId(String name, Long categoryId);

}

