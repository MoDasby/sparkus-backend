package com.modasby.sparkusbackend.unit.service;

import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import com.modasby.sparkusbackend.service.FeedService;
import com.modasby.sparkusbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FeedService feedService;

    @Test
    public void testGetFeed() {

        User user = new User();

        user.setUsername("giulliano");
        user.setName("giulliano");
        user.setEmail("");
        user.setPassword("");

        List<Post> posts = Arrays.asList(
                new Post("OLÁ", 0, user),
                new Post("OLÁ 1", 0, user),
                new Post("OLÁ 2", 0, user)
        );

        when(postRepository.findByOrderByCreationDateDesc(Pageable.ofSize(20))).thenReturn(
                new PageImpl<>(posts)
        );

        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        when(userService.findNewUsers()).thenReturn(List.of(user));

        FeedResponseDto feedResponse = feedService.getFeed("giulliano", Pageable.ofSize(20));

        assertThat(feedResponse.getNewUsers().size()).isEqualTo(1);
        assertThat(feedResponse.getNewUsers().get(0).getUsername()).isEqualTo(user.getUsername());

        assertThat(feedResponse.getPosts().getSize()).isEqualTo(3);

        List<PostResponseDto> feedResponsePosts = feedResponse.getPosts().getContent();

        assertThat(feedResponsePosts.get(0).getText()).isEqualTo(posts.get(0).getText());
        assertThat(feedResponsePosts.get(1).getText()).isEqualTo(posts.get(1).getText());
        assertThat(feedResponsePosts.get(2).getText()).isEqualTo(posts.get(2).getText());
    }
}
