package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecom.ecom.Model.Category;
import com.ecom.ecom.Model.Products;

public class ProductRowMapper implements RowMapper<Products> {

    @Override
    public Products mapRow(ResultSet rs, int rowNum) throws SQLException {
        Products products=new Products();
        Category category=new Category();
      
            products.setProductId(rs.getInt(1));
            products.setSellerId(rs.getInt(2));
            products.setProductName(rs.getString(3));
            products.setProductUrl(rs.getString(4));
            products.setProductDescription(rs.getString(5));
            products.setProductQuantity(rs.getInt(6));
            products.setProductAmount(rs.getLong(7));
            category.setCategoryId(rs.getInt(8));
            products.setCategory(category);

            return products;
 
    }
    
}
