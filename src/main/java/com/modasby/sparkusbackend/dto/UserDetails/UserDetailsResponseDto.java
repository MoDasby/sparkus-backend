package com.modasby.sparkusbackend.dto.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserDetailsResponseDto {

    private String username;
    private String name;
    @JsonProperty("icon_path")
    private String iconPath;

    List<PostResponseDto> posts;

    public UserDetailsResponseDto(User user) {
        Function<Post, Boolean> isLiked = (p) -> user.getLikedPosts().contains(p);

        this.username = user.getUsername();
        this.name = user.getName();
        this.iconPath = user.getIconPath();
        this.posts = user.getPosts().stream().map(p -> new PostResponseDto(p, isLiked.apply(p))).collect(Collectors.toList());
    }
}
