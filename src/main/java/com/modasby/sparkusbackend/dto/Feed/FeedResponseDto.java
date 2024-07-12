package com.modasby.sparkusbackend.dto.Feed;

import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class FeedResponseDto {

    private List<UserResponseDto> newUsers;
    private Page<PostResponseDto> posts;
}
