package com.security.userService;

import com.security.userDao.UserDao;
import com.security.userModel.Role;
import com.security.userModel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
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
        User userFromDB = userDao.findByUserName(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "USER")));
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
    @PostFilter("filterObject.roles.size() > 1")
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
}
