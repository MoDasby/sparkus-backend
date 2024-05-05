package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.config.JwtTokenUtil;
import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.exception.EntityNotFoundException;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.postRepository = postRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public PostResponseDto addPost(PostDto postDto, String tokenHeader) {

        User user = getUserByToken(tokenHeader);

        Post post = new Post(postDto);

        post.setAuthor(user);

        return new PostResponseDto(postRepository.save(post), false);
    }

    public void addLike(String postId, String token) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));

        User user = getUserByToken(token);

        List<Post> likedPosts = user.getLikedPosts();

        if (likedPosts.contains(post)) return;

        likedPosts.add(post);

        user.setLikedPosts(likedPosts);

        post.setLikes(post.getLikes() +1);

        postRepository.save(post);
        userService.save(user);
    }

    public void removeLike(String postId, String token) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));

        User user = getUserByToken(token);

        List<Post> likedPosts = user.getLikedPosts();

        if (!likedPosts.contains(post)) return;

        if (likedPosts.remove(post)) {

            post.setLikes(post.getLikes() -1);

            postRepository.save(post);
        }
    }

    private User getUserByToken(String token) {
        String authorCredential = jwtTokenUtil.getCredentialFromToken(token.substring(7));

        return userService.findByUsername(authorCredential);
    }
}
