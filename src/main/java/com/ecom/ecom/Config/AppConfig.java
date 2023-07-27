package com.ecom.ecom.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
public class AppConfig {
    
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails userDetails = User.builder().
    //             username("DURGESH")
    //             .password(passwordEncoder().encode("DURGESH")).roles("user").
    //             build();
    //     return new InMemoryUserDetailsManager(userDetails);
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println(new BCryptPasswordEncoder());
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
