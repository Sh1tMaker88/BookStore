package com.security.userService;

import com.security.userDao.UserDao;
import com.security.userModel.Role;
import com.security.userModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean userExists(String username) {
        return userDao.findByUserName(username) != null;
    }

    @Override
    public boolean saveUser(User user) {
        if (userDao.findByUserName(user.getUsername()) != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role("USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createUser(user);
        return true;
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
//    @PostFilter("filterObject.roles.size() > 1")
    public List<User> getList() {
        return userDao.findAllUsers();
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userDao.findById(userId) != null) {
            userDao.deleteUserById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
//                .orElseThrow(() -> new UsernameNotFoundException("No such user was found"));
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user:" + username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
