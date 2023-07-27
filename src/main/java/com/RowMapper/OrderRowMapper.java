package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecom.ecom.Model.Orders;

public class OrderRowMapper implements RowMapper<Orders> {

    @Override
    public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {
        Orders orders=new Orders();
        orders.setOrderId(rs.getInt(1));
        orders.setUserId(rs.getInt(2));
        orders.setProductId(rs.getInt(3));
        return orders;
    }
    
}
