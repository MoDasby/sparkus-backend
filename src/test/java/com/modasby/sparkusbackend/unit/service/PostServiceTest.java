package com.modasby.sparkusbackend.unit.service;

import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import com.modasby.sparkusbackend.service.PostService;
import com.modasby.sparkusbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostService postService;

    private User user;
    private List<Post> posts;

    @BeforeEach
    public void setUp() {
        user = new User();

        user.setUsername("giulliano");
        user.setName("giulliano");
        user.setEmail("");
        user.setPassword("");

        posts = Arrays.asList(
                new Post("OLÁ", 0, user),
                new Post("OLÁ 1", 0, user),
                new Post("OLÁ 2", 0, user)
        );
    }

    @Test
    public void testFindPostByUser() {

        when(postRepository.findByAuthor_Username(anyString(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(posts));

        Page<PostResponseDto> postResponseDto = postService.findPostByUser(user.getUsername(), Pageable.ofSize(10));

        assertThat(postResponseDto.getSize()).isEqualTo(3);
        assertThat(postResponseDto.getContent().get(0).getText()).isEqualTo(posts.get(0).getText());
        assertThat(postResponseDto.getContent().get(1).getText()).isEqualTo(posts.get(1).getText());
        assertThat(postResponseDto.getContent().get(2).getText()).isEqualTo(posts.get(2).getText());

        verify(postRepository, times(1)).findByAuthor_Username(anyString(), any(Pageable.class));
    }

    @Test
    public void testAddPost() {
        PostDto postDto = new PostDto("post novo");
        Post post = new Post(postDto);
        post.setAuthor(user);

        when(userService.findByUsername(anyString())).thenReturn(user);

        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostResponseDto postResponseDto = postService.addPost(postDto, user.getUsername());

        assertThat(postResponseDto.getText()).isEqualTo(postDto.getText());
        assertThat(postResponseDto.getAuthor().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void testAddLike() {
        user.getLikedPosts().addAll(List.of(posts.get(0), posts.get(1)));

        when(postRepository.findById(anyString())).thenReturn(Optional.of(posts.get(0)));
        when(userService.findByUsername(anyString())).thenReturn(user);

        assertThatNoException().isThrownBy(() -> postService.addLike("post_id", "user"));

        when(postRepository.findById(anyString())).thenReturn(Optional.of(posts.get(2)));

        assertThatNoException().isThrownBy(() -> postService.addLike("post_id", "user"));

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> postService.addLike("post_id", "user"));
    }

    @Test
    public void testRemoveLike() {
        user.getLikedPosts().addAll(posts);

        when(postRepository.findById(anyString())).thenReturn(Optional.of(posts.get(0)));
        when(userService.findByUsername(anyString())).thenReturn(user);

        assertThatNoException().isThrownBy(() -> postService.removeLike("post_id", "username"));

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatException().isThrownBy(() -> postService.removeLike("post_id", "username"));
    }
}
