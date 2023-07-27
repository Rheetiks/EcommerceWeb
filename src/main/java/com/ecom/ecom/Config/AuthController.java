package com.ecom.ecom.Config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.ecom.Model.JwtRequest;
import com.ecom.ecom.Model.JwtResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil helper;

    @Autowired
    private UserDetailsService userDetailsService;
   

    

    

    @PostMapping("/user/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request, HttpServletResponse servelet) {

        this.doAuthenticate(request.getUserEmail(), request.getUserPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();

        Cookie seller = new Cookie("sellerCookie", null);
        seller.setPath("/"); 
        seller.setMaxAge(0);
        servelet.addCookie(seller);

        Cookie cookie = new Cookie("userCookie", token);
        // cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60 * 60);
        servelet.addCookie(cookie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    
    @PostMapping("/seller/login")
    public ResponseEntity<JwtResponse> sellerLogin(@RequestBody JwtRequest request, HttpServletResponse servelet) {

        this.doAuthenticate(request.getUserEmail(), request.getUserPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername())
                .build();

        Cookie user = new Cookie("userCookie", null);
        user.setPath("/");
        user.setMaxAge(0);
        servelet.addCookie(user);

        Cookie cookie = new Cookie("sellerCookie", token);
        // cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60 * 60);
        servelet.addCookie(cookie);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
