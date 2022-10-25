package com.example.user_management.user.service;

import com.example.user_management.user.entity.Role;
import com.example.user_management.user.entity.User;

import java.util.List;

public interface AppUserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    //nen phan trang
    List<User> getUsers();
}
