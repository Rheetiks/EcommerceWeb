package com.ecom.ecom.Service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.ecom.Config.JwtUtil;
import com.ecom.ecom.Dao.SellerDao;
import com.ecom.ecom.Dao.UserDao;
import com.ecom.ecom.Model.CartMapper;
import com.ecom.ecom.Model.Category;
import com.ecom.ecom.Model.Products;
import com.ecom.ecom.Model.Seller;
import com.ecom.ecom.Model.User;

@Service
public class UserService implements UserDetailsService{

    int id;

    @Autowired
    private UserDao userdao;

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private JavaMailSender emailsender;

    @Autowired
    private JwtUtil helper;

    public String addUser(User user){
        return userdao.addUser(user);
    }

    public User viewUser(User user){
        return userdao.viewUser(user.getUserEmail(),user.getUserPassword());
    }

    public String addToCart(String jwtToken,Products products){
        return userdao.addToCart(jwtToken,products);
    }

    public String removeFromCart(String jwtToken,Products products ){
        return userdao.removeFromCart(jwtToken, products);
    }

    public List<Products> viewCart(String jwtToken){
        return userdao.viewCart(jwtToken);
    }
    
    public String setCartProductQuantity(String authorization,CartMapper cartmapper){
        return userdao.setCartProductQuantity(authorization,cartmapper);
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user=userdao.getUserByUserEmail(userEmail);

            if(user!=null){
                return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserEmail())
                    .password(passwordEncoder().encode(user.getUserPassword())) 
                    .roles("User")
                    .build();
               

            }

                  
            Seller seller = sellerDao.getSellerBySellerEmail(userEmail);

            if(seller!=null){
                return org.springframework.security.core.userdetails.User
                    .withUsername(seller.getSellerEmail())
                    .password(passwordEncoder().encode(seller.getSellerPassword())) 
                    .roles("Seller")
                    .build();
            }
              
            throw new UsernameNotFoundException("User or Seller not found with username: " + userEmail);
            

    }
        

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<Products> getProductByCategory(Category category) {
        return this.userdao.getProductByCategory(category);
    }

    public CartMapper getCartDetailsBYId(String jwtToken, Products products) {
        return this.userdao.getCartDetailsBYId(jwtToken,products);    
    }

    public void sendEmail(String to, String text) throws MessagingException {
        MimeMessage mimeMessage = emailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo("recipient@example.com");
        helper.setSubject("Your Order Status");
        helper.setText(text, true); 
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, to);

        emailsender.send(mimeMessage);
    }

    public String placeOrder(String jwtToken) throws MessagingException {
        String text="Your Order Was Confirmed";
        sendEmail(helper.getUsernameFromToken(jwtToken), text);
        return this.userdao.placeOrder(jwtToken);
    }

}
