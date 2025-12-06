package com.example.expense_tracker.service;

import com.example.expense_tracker.entity.User;
import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);
    User getUserByEmail(String email);
    User getUserByEmailAndUsername(String email, String username);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
