package com.example.fitnessapp.service;

import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "user1", "pass1", "user1@mail.com", LocalDateTime.now(), LocalDateTime.now());
        user2 = new User(2L, "user2", "pass2", "user2@mail.com", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createUser_shouldSaveAndReturnUser() {
        given(userRepository.save(any(User.class))).willReturn(user1);
        User savedUser = userService.createUser(user1);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("user1");
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        given(userRepository.findAll()).willReturn(Arrays.asList(user1, user2));
        List<User> userList = userService.getAllUsers();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
        assertThat(userList).containsExactlyInAnyOrder(user1, user2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUserOptional() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        Optional<User> foundUserOptional = userService.getUserById(1L);
        assertThat(foundUserOptional).isPresent();
        assertThat(foundUserOptional.get()).isEqualTo(user1);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        given(userRepository.findById(99L)).willReturn(Optional.empty());
        Optional<User> foundUserOptional = userService.getUserById(99L);
        assertThat(foundUserOptional).isNotPresent();
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void deleteUser_whenUserExists_shouldCallDelete() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        willDoNothing().given(userRepository).delete(user1);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void deleteUser_whenUserDoesNotExist_shouldThrowException() {
        given(userRepository.findById(99L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> userService.deleteUser(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id : '99'");
        verify(userRepository, never()).delete(any(User.class));
        verify(userRepository, never()).deleteById(anyLong());
    }
}
