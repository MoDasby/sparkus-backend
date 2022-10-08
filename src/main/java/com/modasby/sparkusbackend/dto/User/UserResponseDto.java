package com.modasby.sparkusbackend.dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modasby.sparkusbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserResponseDto {

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.iconPath = user.getIconPath();
    }

    private String username;
    private String name;

    @JsonProperty("icon_path")
    private String iconPath;
}
