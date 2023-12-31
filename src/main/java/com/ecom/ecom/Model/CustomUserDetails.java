package com.ecom.ecom.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;



public class CustomUserDetails extends User{
    private int userId;

    public CustomUserDetails(int userId, String username, String password,String authorities) {
        super(username, password, getAuthorities(authorities));
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
    private static Collection<?extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
