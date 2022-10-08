package com.modasby.sparkusbackend.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class AuthRequest {

    private String credential;
    private String password;
}
