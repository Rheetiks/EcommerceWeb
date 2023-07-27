package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecom.ecom.Model.CartMapper;

public class CartMapperRowMapper implements RowMapper<CartMapper>{

    @Override
    public CartMapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartMapper cartmapper=new CartMapper();
        cartmapper.setUserId(rs.getInt(1));
        cartmapper.setProductId(rs.getInt(2));
        cartmapper.setCartProductQuantity(rs.getInt(3));

        return cartmapper;
        
    }
    
}
