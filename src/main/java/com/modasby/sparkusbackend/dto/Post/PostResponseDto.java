package com.modasby.sparkusbackend.dto.Post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PostResponseDto {
    private String id;

    private String text;
    private int likes;

    @JsonProperty("is_liked")
    private Boolean isLiked;
    private UserResponseDto author;

    public PostResponseDto(Post post, boolean isLiked) {
        this(post);
        this.isLiked = isLiked;
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.likes = post.getLikes();
        this.author = new UserResponseDto(post.getAuthor());
    }
}
