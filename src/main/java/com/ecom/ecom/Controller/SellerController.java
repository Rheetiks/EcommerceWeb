package com.ecom.ecom.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.Seller;
import com.ecom.ecom.Service.SellerService;

@RestController
public class SellerController {

    @Autowired
    SellerService sellerservice;

    @PostMapping("/seller/addSeller")
    public String addSeller(@RequestBody Seller seller){
        return sellerservice.addSeller(seller);
    }

    @PostMapping("/seller/viewSeller")
    public Seller viewSeller(@RequestBody Seller seller){
        return sellerservice.viewSeller(seller);
    }

    @PostMapping("/seller/addProducts")
    public String addProducts(@RequestParam("product") MultipartFile file ,@RequestHeader("Authorization") String authorization,@ModelAttribute Products products) throws IOException{
        String jwtToken = authorization.replace("Bearer ", "");
        return sellerservice.addProducts(file,jwtToken,products);
    }

    @GetMapping("/seller/viewProducts")
    public List<Products> viewProducts(@RequestHeader("Authorization") String authorization){
        String jwtToken = authorization.replace("Bearer ", "");
        return sellerservice.viewProducts(jwtToken);
    }    

    @PostMapping("/seller/deleteProduct")
    public String deleteProduct(@RequestBody Products product){
        return sellerservice.deleteProduct(product);
    }

    @GetMapping("/products")
    public List<Products> Products(){
        return sellerservice.Products();
    } 

    @PostMapping("/ProductById")
    public Products getProductById(@RequestBody Products product){
        return sellerservice.getProductById(product);
    }
}
