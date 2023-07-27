package com.ecom.ecom.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->auth.antMatchers("/seller/addSeller").permitAll()
                .antMatchers("/auth/user/login").permitAll()
                .antMatchers("/user/addUser").permitAll()
                .antMatchers("/Html/Main.html").permitAll()
                .antMatchers("/Javascript/**").permitAll()
                .antMatchers("/Images/**").permitAll()
                .antMatchers("/Css/**").permitAll()
                .antMatchers("/auth/seller/login").permitAll()
                .antMatchers("/user/**").hasRole("User")
                .antMatchers("/seller/**").hasRole("Seller")
                .antMatchers("/products").permitAll()
                .antMatchers("/ProductById").permitAll()
                .antMatchers("/getProductByCategory").permitAll()
                .anyRequest().permitAll())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
    
}
