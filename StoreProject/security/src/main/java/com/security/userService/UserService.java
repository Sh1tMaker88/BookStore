package com.security.userService;

import com.security.userDao.UserDao;
import com.security.userModel.User;
import com.security.userModel.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService, UserDetailsService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean userExists(String username) {
        return userDao.findByUserName(username) != null;
    }

    @Override
    public void createUser(User user) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(UserRole.USER)));
        }
        userDao.createUser(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public User getUser(Long id) {
//        User user = userDao.findById(id).get();
        User user = userDao.findById(id);

        return user;
    }

    @Override
    @PostFilter("filterObject.roles.size() > 1")
    public List<User> getList() {
        return null;
//                userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        Set<GrantedAuthority> roles = new HashSet<>();
        for (UserRole role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.name()));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), roles);

        return userDetails;
    }
}
