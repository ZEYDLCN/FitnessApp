package com.example.fitnessapp.service;

import com.example.fitnessapp.entity.User; 

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user); 
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails); 
    void deleteUser(Long id); 
    Optional<User> getUserByUsername(String username); 
}