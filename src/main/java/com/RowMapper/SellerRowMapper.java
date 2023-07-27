package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecom.ecom.Model.Seller;

public class SellerRowMapper implements RowMapper<Seller>{

    @Override
    public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {
        Seller seller=new Seller();
        seller.setSellerId(rs.getInt(1));
        seller.setSellerName(rs.getString(2));
        seller.setSellerPhoneNo(rs.getLong(3));
        seller.setSellerEmail(rs.getString(4));
        seller.setSellerPassword(rs.getString(5));
        

        return seller;
       
    }
    
}
