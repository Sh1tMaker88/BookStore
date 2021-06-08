package com.security.userService;

import com.security.userModel.User;
import com.security.userModel.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.security.userDao.UserDao;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public MyUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
//                .orElseThrow(() -> new UsernameNotFoundException("No such user was found"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
