package com.project.ProductService.Controller;

import com.project.ProductService.DTO.ProductDTO;
import com.project.ProductService.Exception.ResourceNotFoundException;
import com.project.ProductService.Exception.UnauthorizedUserException;
import com.project.ProductService.Interceptors.RequestInterceptor;
import com.project.ProductService.Model.ProductModel;
import com.project.ProductService.DTO.UserDTO;
import com.project.ProductService.Service.ProductService;
import com.project.ProductService.Service.JwtService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/products")
    public class Controller {

        @Autowired
        private ProductService productService;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private JwtService jwtService;

        //@CrossOrigin(origins = "http://localhost:8083")
        @PostMapping
        public ResponseEntity<ProductModel> createProduct(
                @RequestBody ProductModel product) throws IOException {
            System.out.println(product);
            System.out.println(product.getUserId());
            // Fetch user details from User Service
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + RequestInterceptor.getToken());

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            UserDTO userDTO = restTemplate.getForObject("http://USER-SERVICE/users/" + product.getUserId(), UserDTO.class,
                    requestEntity);
            System.out.println(userDTO+"ikujyghkughv");
            // Validate user role
            if (userDTO == null || userDTO.getRole() == null || !userDTO.getRole().toString().equalsIgnoreCase("seller")) {
                throw new UnauthorizedUserException("Only sellers can add products. UserId: " + product.getUserId());
            }

            // Set userId and proceed with product creation
            product.setUserId(product.getUserId());
            ProductModel createdProduct = productService.createProduct(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        }

//        @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//        public ResponseEntity<ProductModel> createProduct(
//                @RequestPart("product") @Valid @ModelAttribute ProductModel product,
//                @RequestPart("image") MultipartFile image) throws IOException {
//
//            System.out.println("Product received: " + product);
//            System.out.println("UserId: " + product.getUserId());
//
//            // Fetch user details from User Service with Authorization Header
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + RequestInterceptor.getToken());
//
//            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//            ResponseEntity<UserDTO> response = restTemplate.exchange(
//                    "http://USER-SERVICE/users/" + product.getUserId(),
//                    HttpMethod.GET,
//                    requestEntity,
//                    UserDTO.class
//            );
//
//            UserDTO userDTO = response.getBody();
//            System.out.println("User fetched: " + userDTO);
//
//            // Validate user role
//            if (userDTO == null || userDTO.getRole() == null || !userDTO.getRole().toString().equalsIgnoreCase("seller")) {
//                throw new UnauthorizedUserException("Only sellers can add products. UserId: " + product.getUserId());
//            }
//
//            // Set user ID correctly
//            product.setUserId(userDTO.getId());
//
//            // Create and save the product
//            ProductModel createdProduct = productService.createProduct(product, image);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//        }
//          @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//          public ResponseEntity<?> createProduct(@RequestPart ProductModel productModel,
//                                                 @RequestPart MultipartFile imageFile) {
//              try {
//                  HttpHeaders headers = new HttpHeaders();
//                  headers.set("Authorization", "Bearer " + RequestInterceptor.getToken());
//                   HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//                  ProductModel product1 = productService.createProduct(productModel, imageFile);
//                  return new ResponseEntity<>(product1, HttpStatus.CREATED);
//              }
//              catch(Exception e) {
//                  return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//              }
//          }





        @PutMapping("/{id}")
        public ResponseEntity<ProductModel> updateProduct(@PathVariable Long id, @RequestBody ProductModel productDetails) {
            ProductModel updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        }

        @GetMapping("/{productId}")
        public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
            ProductDTO productDTO = productService.getProductDTOById(productId);
            return ResponseEntity.ok(productDTO);
        }

        @GetMapping("/username/{username}")
        public ResponseEntity<List<ProductModel>> getProductByUsername(@RequestParam String userName){
            List<ProductModel> Products = productService.getProductByUsername(userName);
            return ResponseEntity.ok(Products);
        }

        @GetMapping
        public ResponseEntity<List<ProductModel>> getAllProducts() {
            List<ProductModel> products = productService.getAllProducts();
            return ResponseEntity.ok(products);  // Return the list of products with 200 OK
        }



        @GetMapping("/category")
        public ResponseEntity<List<ProductModel>> getProductsByCategory(@RequestParam String category) {
            List<ProductModel> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(products);
        }

        @GetMapping("/search")
        public ResponseEntity<List<ProductModel>> searchProducts(
                @RequestParam String name,
                @RequestParam(required = false) String category) {

            List<ProductModel> products;
            if (category == null || category.isEmpty()) {
                products = productService.searchByName(name);
            } else {
                products = productService.searchByNameAndCategory(name, category);
            }

            return ResponseEntity.ok(products);
        }




        @GetMapping("/price-range")
        public ResponseEntity<List<ProductModel>> getProductsByPriceRange(
                @RequestParam Double minPrice,
                @RequestParam Double maxPrice)
        {
            List<ProductModel> products = productService.getProductsByPriceRange(minPrice, maxPrice);
            return ResponseEntity.ok(products);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }

    }
