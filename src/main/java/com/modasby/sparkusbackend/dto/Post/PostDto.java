package com.modasby.sparkusbackend.dto.Post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class PostDto {

    private String text;
    @JsonProperty("author_username")
    private String authorUsername;
}
