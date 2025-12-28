package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.UserDTO;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.service.impl.UserServiceImpl;
import com.example.expense_tracker.util.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // loads only MVC stuff + this controller.
@AutoConfigureMockMvc(addFilters = false) // disable security filters for this test
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserServiceImpl userService;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;


    @Test
    void getUserById_shouldReturnUserDto() throws Exception {

        // given
        Long id = 1L;

        // refercne DTO that will return by API
        User user = new User();
        user.setId(id);
        user.setUsername("sahadeo");
        UserDTO userDTO = new UserDTO(id, "sahadeo", "sa@gmail.com", null, "Sahadeo");


        when(userService.getUserById(id)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        //when + then
        mockMvc.perform(get("/api/v1/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("sahadeo"));
//                .andExpect(jsonPath("$.email").value("sa@gmail.com");
    }

    @Test
    void createUser_shouldReturnUserDto() throws Exception {

        // Given
        UserDTO userDTO = new UserDTO(
                null,
                "sahadeo",
                "sa@gmail.com",
                null,
                "Sahadeo"
        );

        User user = new User();
        user.setId(1L);
        user.setUsername("sahadeo");
        user.setEmail("sa@gmail.com");
        user.setFullName("Sahadeo");

        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.createUser(any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        //when + then
        mockMvc.perform(
                post("/api/v1/users")    // URL + requestparm
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))  // RequestBody
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("sahadeo"));

    }

    @Test
    void updateUser_shouldReturnUserDto() throws Exception {
        // Given
        Long id = 1L;
        UserDTO userDTO = new UserDTO(
                id,
                "sahadeo",
                "sa@gmail.com",
                "newPassword123",
                "Sahadeo"
        );

        User user = new User();
        user.setId(id);
        user.setUsername("sahadeo");
        user.setEmail("sa@gmail.com");
        user.setFullName("Sahadeo");

        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(
                        put("/api/v1/users/{id}", id)    // URL + requestparm
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))  // RequestBody
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("sahadeo"));
    }
}
