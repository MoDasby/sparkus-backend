package com.modasby.sparkusbackend.dto.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modasby.sparkusbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserDetailsResponseDto {

    private String username;
    private String name;

    @JsonProperty("icon_path")
    private String iconPath;

    @JsonProperty("followers_count")
    private long followersCount;

    @JsonProperty("following_count")
    private long followingsCount;

    private boolean following;

    public UserDetailsResponseDto(User user, boolean following) {

        this.username = user.getUsername();
        this.name = user.getName();
        this.iconPath = user.getIconPath();
        this.followersCount = user.getFollowers().size();
        this.followingsCount = user.getFollowing().size();
        this.following = following;
    }
}
