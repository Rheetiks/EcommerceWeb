package com.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecom.ecom.Model.Category;

public class CategoryRowMapper implements RowMapper<Category>{

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category=new Category();
        category.setCategoryId(rs.getInt(1));
        category.setCategoryName(rs.getString(2));

        return category;
    }
    
}
