package com.java.securityEX.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.securityEX.Repo.UserRepo;
import com.java.securityEX.model.User;

@Service
public class UserService {

   @Autowired
   private UserRepo repo;

   @Autowired
   AuthenticationManager authManager;

   @Autowired
   private JwtService jwtService;

   private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

   public User register(User user){
    user.setPassword(encoder.encode(user.getPassword())); 
    return repo.save(user);
   }

  public String verify(User user) {
    try {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Authentication failed";
        }
    } catch (Exception e) {
        return "Error during authentication: " + e.getMessage();
    }
}
    
}   
