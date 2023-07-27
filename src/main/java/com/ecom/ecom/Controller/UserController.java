package com.ecom.ecom.Controller;


import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecom.Model.CartMapper;
import com.ecom.ecom.Model.Category;
import com.ecom.ecom.Model.CustomUserDetails;
import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.User;
import com.ecom.ecom.Service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userservice;



    @PostMapping("/user/addUser")
    public String addUser(@RequestBody User user){
       return userservice.addUser(user);
    }

    @PostMapping("/user/viewUser")
    public User viewUser(@RequestBody User user){
        return userservice.viewUser(user);
    }

    @PostMapping("/user/addToCart")
    public String addToCart(@RequestHeader("Authorization") String authorization,@RequestBody Products products){
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.addToCart(jwtToken,products);
    }

    @PostMapping("/user/removeFromCart")
    public String removeFromCart(@RequestHeader("Authorization") String authorization,@RequestBody Products products ){
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.removeFromCart(jwtToken, products);

    }

    @GetMapping("/user/viewCart")
    public List<Products> viewCart(@RequestHeader("Authorization") String authorization){
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.viewCart(jwtToken);
    }

    @PostMapping("/user/setCartProductQuantity")
    public String setCartProductQuantity(@RequestHeader("Authorization") String authorization,@RequestBody CartMapper cartmapper){
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.setCartProductQuantity(jwtToken,cartmapper);
    }
    @GetMapping("/user/test")
    public String test(){
        return "Working...";
    }

    @GetMapping("/user/profile")
    public UserDetails getUserProfile() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Object principal = (authentication).getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails;
    }

    @PostMapping("/getProductByCategory")
    public List<Products> getProductByCategory(@RequestBody Category category){
        return userservice.getProductByCategory(category);
    }

    @PostMapping("/user/getCartDetailsBYId")
    public CartMapper getCartDetailsBYId(@RequestHeader("Authorization") String authorization,@RequestBody Products products){
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.getCartDetailsBYId(jwtToken,products);
    }

    @GetMapping("/user/placeOrder")
    public String placeOrder(@RequestHeader("Authorization") String authorization) throws MessagingException{
        String jwtToken = authorization.replace("Bearer ", "");
        return userservice.placeOrder(jwtToken);
    }
        
}
