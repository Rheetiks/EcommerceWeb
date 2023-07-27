package com.ecom.ecom.Model;

public class CartMapper {
    private int userId;
    private int productId;
    private int cartProductQuantity;
    

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getCartProductQuantity() {
        return cartProductQuantity;
    }
    public void setCartProductQuantity(int cartProductQuantity) {
        this.cartProductQuantity = cartProductQuantity;
    }
    
    
}
