package com.modasby.sparkusbackend.unit.service;

import com.modasby.sparkusbackend.config.JwtTokenUtil;
import com.modasby.sparkusbackend.dto.Auth.AuthRequest;
import com.modasby.sparkusbackend.dto.Auth.AuthResponse;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.service.AuthService;
import com.modasby.sparkusbackend.service.UserDetailsServiceImpl;
import com.modasby.sparkusbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testAuthenticate() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        UserDetails userDetails = mock(UserDetails.class);
        System.out.println(userDetails.getUsername());
        User user = new User();
        user.setUsername("giulliano");
        String token = "jwttoken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername(authRequest.getCredential()))
                .thenReturn(userDetails);
        when(userService.findByUsername(userDetails.getUsername()))
                .thenReturn(user);
        when(jwtTokenUtil.generateToken(userDetails))
                .thenReturn(token);

        AuthResponse authResponse = authService.authenticate(authRequest);

        assertThat(authResponse).isNotNull();
        assertThat(authResponse.getToken()).isEqualTo(token);
        assertThat(authResponse.getUser()).isNotNull();
        assertThat(authResponse.getUser().getUsername()).isEqualTo(user.getUsername());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername(authRequest.getCredential());
        verify(userService, times(1)).findByUsername(userDetails.getUsername());
        verify(jwtTokenUtil, times(1)).generateToken(userDetails);
    }
}
