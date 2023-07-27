package com.ecom.ecom.Model;

public class Products {
    private int productId;
    private int sellerId;
    private String productName;
    private String productUrl;
    private String productDescription;
    private int productQuantity;
    private long productAmount;
    private Category category;
    
    
    
    
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getSellerId() {
        return sellerId;
    }
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductUrl() {
        return productUrl;
    }
    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public int getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    public long getProductAmount() {
        return productAmount;
    }
    public void setProductAmount(long productAmount) {
        this.productAmount = productAmount;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getCategoryId(Category category) {
        return category.getCategoryId();
    }
    public void setCategoryId(Category category) {
        this.category.setCategoryId(category.getCategoryId());
    }


}
