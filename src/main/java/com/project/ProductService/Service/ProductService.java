package com.project.ProductService.Service;

import com.project.ProductService.DTO.ProductDTO;
import com.project.ProductService.Exception.ResourceNotFoundException;
import com.project.ProductService.Model.ProductModel;
import com.project.ProductService.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public ProductModel createProduct(ProductModel product) throws IOException {
//        product.setImageName(image.getOriginalFilename());
//        product.setImageType(image.getContentType());
//        product.setImageData(image.getBytes());
        return productRepository.save(product);
    }

    public ProductModel updateProduct(Long id, ProductModel productDetails)
    {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }

public ProductDTO getProductDTOById(Long productId) {
    ProductModel product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));
    // Convert ProductModel to ProductDTO
    return new ProductDTO(
            product.getId(),
            product.getUserId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getCategory(),
            product.getImageType(),
            product.getImageData()

    );
}

    public List<ProductModel> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductModel> getProductByUsername(String  userName) {
        return productRepository.findByNameContainingIgnoreCase(userName);
    }



    public List<ProductModel> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<ProductModel> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ProductModel> searchByNameAndCategory(String name, String category) {
        return productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
    }


    public void deleteProduct(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

        public List<ProductModel> getAllProducts() {
            return productRepository.findAll();
        }

//    public ProductDTO getProductByUserId(Long userId) {
//
//        ProductModel product = productRepository.findByUserId(userId);
////                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + userId + " not found"));
//        // Convert ProductModel to ProductDTO
//        return new ProductDTO(
//                product.getId(),
//                product.getUserId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getQuantity(),
//                product.getCategory(),
//                product.getImageType(),
//                product.getImageData(),
//                product.getImageData().getBytes()
//        );
//    }

    public List<ProductModel> getProductByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }
}




