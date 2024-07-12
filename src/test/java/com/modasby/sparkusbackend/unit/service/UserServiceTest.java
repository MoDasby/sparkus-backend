package com.modasby.sparkusbackend.unit.service;

import com.modasby.sparkusbackend.dto.User.UserDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.dto.UserDetails.UserDetailsResponseDto;
import com.modasby.sparkusbackend.exception.UserOrEmailAlreadyExistsException;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.UserRepository;
import com.modasby.sparkusbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {

        userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setEmail("testuser@example.com");
        userDto.setPassword("password");

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("encodedpassword");
    }

    @Test
    void testAddUser_Success() {
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto response = userService.addUser(userDto);

        assertThat(response.getUsername()).isEqualTo(userDto.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAddUser_UserOrEmailAlreadyExists() {
        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(true);

        assertThatThrownBy(() -> userService.addUser(userDto))
                .isInstanceOf(UserOrEmailAlreadyExistsException.class)
                .hasMessage("Usuário ou email já existe");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserDetails_Success() {
        User searcherUser = new User();
        searcherUser.setUsername("searcherUser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("searcherUser")).thenReturn(Optional.of(searcherUser));

        UserDetailsResponseDto response = userService.getUserDetails("testuser", "searcherUser");

        assertThat(response.getUsername()).isEqualTo("testuser");
    }

    @Test
    void testFollow_Success() {
        User follower = new User();
        follower.setUsername("follower");

        user.setId("1");
        follower.setId("2");

        when(userRepository.findByUsername("follower")).thenReturn(Optional.of(follower));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        userService.follow("testuser", "follower");

        assertThat(follower.getFollowing()).contains(user);
        assertThat(user.getFollowers()).contains(follower);
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testFollow_Yourself() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThatException().isThrownBy(() -> userService.follow("user", "user"));
    }

    @Test
    void testStopFollow_Success() {
        User follower = new User();
        follower.setUsername("follower");
        follower.getFollowing().add(user);
        user.getFollowers().add(follower);

        user.setId("1");
        follower.setId("2");

        when(userRepository.findByUsername("follower")).thenReturn(Optional.of(follower));
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        userService.stopFollow("testuser", "follower");

        assertThat(follower.getFollowing()).doesNotContain(user);
        assertThat(user.getFollowers()).doesNotContain(follower);
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testStopFollow_Yourself() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        assertThatException().isThrownBy(() -> userService.stopFollow("testuser", "follower"));
    }
}
