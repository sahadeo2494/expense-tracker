package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expense_tracker.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    // Create User
    @Override
    public User createUser(User user) {

        // CHeck for User Already Exist
        if(!isUserPresent(user.getEmail(), user.getUsername())){
            return repository.save(user);
        }
        throw new RuntimeException("User already exist");
    }

    // Get All User
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // Get By any field
    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + email));
    }

    @Override
    public User getUserByEmailAndUsername(String email, String username) {
        return repository.findByEmailIgnoreCaseAndUsernameIgnoreCase(email, username)
                .orElseThrow(() -> new RuntimeException("User not found with email : " + email
                        + " and username :" + username ));
    }

    @Override
    public User updateUser(Long id, User user) {
        return repository.findById(id).map( existing -> {
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setFullName(user.getFullName());
            existing.setPasswordHash(user.getPasswordHash());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private boolean isUserPresent(String email, String username) {
        if(repository.existsByEmailIgnoreCase(email) &&
                repository.existsByUsernameIgnoreCase(username)) {
            return true;
        }
        return false;
    }
}
