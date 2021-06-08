package com.security.configuration;

import com.security.userService.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class MyAuthenticationProvider /*implements AuthenticationProvider*/ {

//    private final MyUserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public MyAuthenticationProvider(MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//        if (userDetailsService == null) {
//            throw new InternalAuthenticationServiceException("User com.service is null");
//        }
//
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//
//        if (user == null) {
//            throw new AuthenticationCredentialsNotFoundException("No such user was found");
//        }
//
//        if (passwordEncoder.matches(password, user.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
//        } else {
//            throw new AuthenticationServiceException("Unable authenticate user with such fields");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
}
