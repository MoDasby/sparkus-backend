package com.modasby.sparkusbackend.dto.Auth;

import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class AuthResponse {

    private String token;
    private UserResponseDto user;
}
