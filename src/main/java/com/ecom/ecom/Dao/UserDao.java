package com.ecom.ecom.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.RowMapper.CartMapperRowMapper;
import com.RowMapper.ProductRowMapper;
import com.RowMapper.SellerRowMapper;
import com.RowMapper.UserRowMapper;
import com.ecom.ecom.Config.JwtUtil;
import com.ecom.ecom.Model.CartMapper;
import com.ecom.ecom.Model.Category;
import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.Seller;
import com.ecom.ecom.Model.User;



@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private JwtUtil helper;



    public String addUser(User user) {
        String sql = "";
        User currUser= null;
        currUser = viewUser(user.getUserEmail(),user.getUserPassword());
        sql= "select * from seller where sellerEmail=? && sellerPassword=?";
        Seller seller=this.jdbc.queryForObject(sql,new SellerRowMapper(),user.getUserEmail(),user.getUserPassword());
        if (currUser != null || seller!=null) {
            return "Email Already Exists";
        }
        sql = "insert into user(userName,userPhoneNo,userEmail,userPassword,userAddress) values(?,?,?,?,?)";
        this.jdbc.update(sql, user.getUserName(), user.getUserPhoneNo(), user.getUserEmail(), user.getUserPassword(),
                user.getUserAddress());
        return "Successfully Registered User";
    }

    public User getUserByUserEmail(String email) {
        User user = null;
        try {
            String sql = "select * from user where userEmail=?";
            user = this.jdbc.queryForObject(sql, new UserRowMapper(), email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User viewUser(String Email,String Password) {
        User currUser = null;
        try {
            String sql = "select * from user where userEmail=? && userPassword=?";
            currUser = this.jdbc.queryForObject(sql, new UserRowMapper(), Email, Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currUser;
    }

    public CartMapper checkCartProduct(String jwtToken,Products product){
        CartMapper cart=null;
        try{
            String sql="select * from CartMapper where userId=(select userId from user where userEmail=?) && productId=?";
            cart=this.jdbc.queryForObject(sql, new CartMapperRowMapper(), this.helper.getUsernameFromToken(jwtToken),product.getProductId());
        }catch(Exception e){e.printStackTrace();}
        return cart;

    }

    public String addToCart(String jwtToken,Products products) {
        CartMapper cart=checkCartProduct(jwtToken, products);
        if(cart!=null){
            return "This Product Already Exists in Your Cart";
        }
        String sql = "insert into CartMapper(userId,productId) values((select userId from user where userEmail=?),?)";
        this.jdbc.update(sql, helper.getUsernameFromToken(jwtToken), products.getProductId());
        return "Product Added to Cart Successfully";
    }

    public String removeFromCart(String jwtToken,Products products ){
        String sql="delete from CartMapper where userId=(select userId from user where userEmail=?) && productId=?";
        this.jdbc.update(sql, helper.getUsernameFromToken(jwtToken),products.getProductId());
        return "Successfully Removed From Cart";
    }

    public List<Products> viewCart(String jwtToken) {
        String sql = "select * from products where productId in(select productId from CartMapper where userId=(select userId from user where userEmail=?))";
        List<Products> listOfProducts = this.jdbc.query(sql, new ProductRowMapper(), helper.getUsernameFromToken(jwtToken));
        return listOfProducts;
    }

    public String setCartProductQuantity(String authorization,CartMapper cartmapper) {
        Products prod=null;
        String sql="";
        try{
            sql="select * from products where productId=? && productQuantity > ?";
            prod=this.jdbc.queryForObject(sql, new ProductRowMapper(), cartmapper.getProductId(), cartmapper.getCartProductQuantity());
        }catch(Exception e){e.printStackTrace();}
        if(prod==null){
            return "Quantity Exceeded";
        }
        sql = "update CartMapper set cartProductQuantity=? where productId=? && userId=(select userId from user where userEmail=?)";
        this.jdbc.update(sql, cartmapper.getCartProductQuantity(), cartmapper.getProductId(),helper.getUsernameFromToken(authorization));
        return "Quantity Updated Successfully";
    }

    public List<Products> getProductByCategory( Category category) {
        String sql="select * from products where categoryId=(select categoryId from category where categoryName=?)";
        List<Products> l=this.jdbc.query(sql,new ProductRowMapper(),category.getCategoryName());
        return l;
    }

    public CartMapper getCartDetailsBYId(String jwtToken,Products products) {
        String sql="select * from CartMapper where productId=? && userId=(select userId from user where userEmail=?)";
        CartMapper cartMapper=this.jdbc.queryForObject(sql,new CartMapperRowMapper(),products.getProductId(),this.helper.getUsernameFromToken(jwtToken));
        return cartMapper;
    }

    public void updateProductQuantity(Products product){
        String sql="update products set productQuantity=? where productId=?";
        this.jdbc.update(sql,product.getProductQuantity() ,product.getProductId());
    }

    public String placeOrder(String jwtToken) {
        String sql="";
        List<Products> list =viewCart(jwtToken);
        for(Products product : list){
            CartMapper cart=getCartDetailsBYId(jwtToken, product);
            product.setProductQuantity(product.getProductQuantity()-cart.getCartProductQuantity());

            updateProductQuantity(product);
        }
        sql="delete from CartMapper where userId=(select userId from user where userEmail=?)";
        this.jdbc.update(sql, this.helper.getUsernameFromToken(jwtToken));
        return "Successfully Placed Order Now Please Wait for Confirmation Mail";
    }

    

}
