package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.Auth.AuthRequest;
import com.modasby.sparkusbackend.dto.Auth.AuthResponse;
import com.modasby.sparkusbackend.dto.User.UserDto;
import com.modasby.sparkusbackend.service.AuthService;
import com.modasby.sparkusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> createAuthentication(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(authService.authenticate(authRequest));
        } catch (DisabledException e) {
            throw new DisabledException("conta desativada");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("email ou senha incorretos");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

}
