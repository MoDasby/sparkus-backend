package com.modasby.sparkusbackend.unit.service;

import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.service.UserDetailsServiceImpl;
import com.modasby.sparkusbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    User user;

    @BeforeEach
    public void setUp() {

        user = new User();

        user.setUsername("giulliano");
        user.setName("giulliano");
        user.setEmail("");
        user.setPassword("123456");
    }

    @Test
    public void testLoadByUsername() {
        when(userService.existsByUsername(anyString())).thenReturn(true);
        when(userService.findByUsername(anyString())).thenReturn(user);

        UserDetails authenticatedUser = userDetailsService.loadUserByUsername("user");

        assertThat(authenticatedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(authenticatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void testLoadByEmail() {
        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(true);
        when(userService.findByEmail(anyString())).thenReturn(user);

        UserDetails authenticatedUser = userDetailsService.loadUserByUsername("user");

        assertThat(authenticatedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(authenticatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    public void testInvalidCredential() {
        when(userService.existsByEmail(anyString())).thenReturn(false);
        when(userService.existsByUsername(anyString())).thenReturn(false);

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userDetailsService.loadUserByUsername("user"));
    }
}
