package com.security.userService;

import com.security.userModel.User;

import java.util.List;

public interface IUserService {

    public boolean userExists(String username);

    public boolean saveUser(User user);

    public User getUser(Long id);

    public List<User> getList();

    boolean deleteUser(Long userId);
}
