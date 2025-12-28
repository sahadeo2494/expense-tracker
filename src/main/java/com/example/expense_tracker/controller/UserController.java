package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.UserDTO;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.service.impl.UserServiceImpl;
import com.example.expense_tracker.util.mapper.UserMapper;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Get All User
    @GetMapping
    public List<UserDTO> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user: userList){
            userDTOList.add(userMapper.toDTO(user));
        }
        return userDTOList;
    }

    // Get User by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable @Positive Long id){
        return ResponseEntity.ok(userMapper.toDTO(userService.getUserById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        log.debug("User",user);
        return ResponseEntity.ok(userMapper.toDTO(userService.createUser(user)));
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable @Positive Long id,
                                              @RequestBody @Validated UserDTO userDTO){
        User user = userMapper.toEntity(userDTO);
        return ResponseEntity.ok(userMapper.toDTO(userService.updateUser(id, user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Positive Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
}
