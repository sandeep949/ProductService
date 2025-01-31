package com.project.ProductService.DTO;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;

public class ProductDTO {

        private Long id;
        private Long userId;
        private String name;
        private String description;
        private double price;
        private int quantity;
        private String category;

    private String imageName;

    private String imageType;

//    @Lob // Specifies that the database should store this as a large object
//    private byte[] imageData;
@Lob
@Column(columnDefinition = "LONGTEXT")
private String imageData;
        // Default Constructor
        public ProductDTO() {
        }

        // Constructor with fields


        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ProductDTO(Long id, Long userId, String name, String description, double price, int quantity, String category, String imageType, String imageData) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
//        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

//    public String getImageName() {
//        return imageName;
//    }
//
//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}

