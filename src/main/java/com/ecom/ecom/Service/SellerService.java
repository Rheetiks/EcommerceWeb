package com.ecom.ecom.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.ecom.Dao.SellerDao;

import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.Seller;

@Service
public class SellerService {

    @Autowired
    SellerDao sellerdao;

    private final String productUrl = "D:\\projects\\ecom\\src\\main\\resources\\static\\ProductImages";

    public String addSeller(Seller seller) {
        return sellerdao.addSeller(seller);
    }

    public Seller viewSeller(Seller seller) {
        return sellerdao.viewSeller(seller.getSellerEmail(), seller.getSellerPassword());
    }

    public String addProducts(MultipartFile file, String jwtToken, Products products)
            throws IOException, UncheckedIOException {
        uploadFile(file, productUrl);
        return sellerdao.addProducts(file, jwtToken, products);
    }

    public List<Products> viewProducts(String jwtToken) {
        return sellerdao.viewProducts(jwtToken);
    }

    public void uploadFile(MultipartFile file, String uploadUrl) throws IOException {
        Files.copy(file.getInputStream(), Paths.get(uploadUrl + File.separator + file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING);
    }

    public String deleteProduct(Products product) {
        return sellerdao.deleteProduct(product);
    }

    public List<Products> Products() {
        return sellerdao.Products();
    }

    public Products getProductById(Products product) {

        return sellerdao.getProductById(product);
    }

}
