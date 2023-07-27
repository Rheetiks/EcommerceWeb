package com.ecom.ecom.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.RowMapper.ProductRowMapper;
import com.RowMapper.SellerRowMapper;
import com.RowMapper.UserRowMapper;
import com.ecom.ecom.Config.JwtUtil;
import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.Seller;
import com.ecom.ecom.Model.User;

@Repository
public class SellerDao {
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private JwtUtil helper;



    // public static Seller currSeller=new Seller();

    public String addSeller(Seller seller){
        Seller currSeller=null;
        try{
            currSeller=viewSeller(seller.getSellerEmail(), seller.getSellerPassword());
            String sql="select * from user where userEmail=? && userPassword=?";
            User user=this.jdbc.queryForObject(sql, new UserRowMapper(), seller.getSellerEmail(), seller.getSellerPassword());
            if(currSeller!=null || user!=null){
                return "Email Already Exists";
            }
        }catch(Exception e){e.printStackTrace();}
        String sql="insert into seller (sellerName,sellerPhoneNo,sellerEmail,sellerPassword) values (?,?,?,?)";
        this.jdbc.update(sql, seller.getSellerName(),seller.getSellerPhoneNo(),seller.getSellerEmail(),seller.getSellerPassword());
        return "Successfully created Seller Account";
    }

    public Seller getSellerBySellerEmail(String email){
        Seller seller= null;
        try {
            String sql="select * from seller where sellerEmail=?";
            seller=this.jdbc.queryForObject(sql, new SellerRowMapper(),email);
        } catch (Exception e) {e.printStackTrace();}
        return seller;
    }

    public Seller getSellerByEmail(String email){
        Seller currSeller=null;
        try{
            String sql="select * from seller where sellerEmail=?";
            currSeller =this.jdbc.queryForObject(sql, new SellerRowMapper() , email);
        }catch(Exception e){e.printStackTrace();}
        return currSeller;

    }



    public Seller viewSeller(String Email,String Password){
        Seller currSeller=null;
        try{
            String sql="select * from seller where sellerEmail=? && sellerPassword=?";
            currSeller =this.jdbc.queryForObject(sql, new SellerRowMapper() ,Email, Password);
        }catch(Exception e){e.printStackTrace();}
        return currSeller;

    }

    public String addProducts(MultipartFile file ,String jwtToken,Products products){
        String sql="insert into products (sellerId,productNmae,productUrl,productDescription,productQuantity,productAmount,categoryId) values ((select sellerId from seller where sellerEmail=?),?,?,?,?,?,(select categoryId from category where categoryName=?))";
        this.jdbc.update(sql,helper.getUsernameFromToken(jwtToken),products.getProductName(),file.getOriginalFilename(),products.getProductDescription(),products.getProductQuantity(),products.getProductAmount(),products.getCategory().getCategoryName());
        return "Successfully Added Product";
    }

    public List<Products> viewProducts(String jwtToken){
        String sql="select * from products where sellerId=(select sellerId from seller where sellerEmail=?)";
        List<Products> l=this.jdbc.query(sql, new ProductRowMapper(),helper.getUsernameFromToken(jwtToken));
        return l;
    }

    public String deleteProduct(Products product) {
        String sql="delete from products where productId=?";
        this.jdbc.update(sql, product.getProductId());
        return "sUCCESSFULLY deleted product";
    }

    public List<Products> Products() {
        String sql="select * from products";
        List<Products> l=this.jdbc.query(sql, new ProductRowMapper());
        return l;
    }

    public Products getProductById(Products product){
        String sql="select * from products where productId=?";
        Products prod=this.jdbc.queryForObject(sql, new ProductRowMapper(),product.getProductId());
        return prod;
    }

    
}
