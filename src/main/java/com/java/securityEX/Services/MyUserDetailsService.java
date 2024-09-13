package com.java.securityEX.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.securityEX.Repo.UserRepo;
import com.java.securityEX.model.User;
import com.java.securityEX.model.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username);
        if (user == null) {
        System.out.println("User not found with username: " + username);
        throw new UsernameNotFoundException("User not found");
    }
    return new UserPrincipal(user);
}

}
