package com.modasby.sparkusbackend.dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class UserDto {

    private String username;
    private String name;

    @JsonProperty("icon_path")
    private String iconPath;
    private String email;
    private String password;
}
