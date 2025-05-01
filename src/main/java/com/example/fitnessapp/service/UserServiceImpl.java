package com.example.fitnessapp.service;

import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.exception.ResourceNotFoundException; // PetTracker'daki gibi bir exception sınıfı lazım olacak
import com.example.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Transaction importu

import java.util.List;
import java.util.Optional;

@Service 
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired 
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional 
    public User createUser(User user) {
        
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true) // Okuma işlemi
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
      
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional // Yazma işlemi
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id)); // 

      
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional 
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user); 
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}