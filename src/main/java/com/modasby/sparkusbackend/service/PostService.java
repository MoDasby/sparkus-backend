package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.exception.EntityNotFoundException;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Page<PostResponseDto> findPosts(String username, Pageable pageable) {
        Page<Post> feedPosts = postRepository.findByOrderByCreationDateDesc(pageable);

        User user = userService.findByUsername(username);
        Function<Post, Boolean> isLiked = (p) -> user.getLikedPosts().contains(p);

        return feedPosts.map(p -> new PostResponseDto(p, isLiked.apply(p)));
    }

    public Page<PostResponseDto> findPostByUser(String username, Pageable pageable) {
        User user = userService.findByUsername(username);
        Function<Post, Boolean> isLiked = (p) -> user.getLikedPosts().contains(p);

        return postRepository
                .findByAuthor_Username(username, pageable)
                .map(p -> new PostResponseDto(p, isLiked.apply(p)));
    }

    public PostResponseDto addPost(PostDto postDto, String username) {

        User user = userService.findByUsername(username);

        Post post = new Post(postDto);

        post.setAuthor(user);

        return new PostResponseDto(postRepository.save(post), false);
    }

    public void addLike(String postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));

        User user = userService.findByUsername(username);

        List<Post> likedPosts = user.getLikedPosts();

        if (likedPosts.contains(post)) return;

        likedPosts.add(post);

        user.setLikedPosts(likedPosts);

        post.setLikes(post.getLikes() +1);

        postRepository.save(post);
        userService.save(user);
    }

    public void removeLike(String postId, String username) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post não encontrado"));

        User user = userService.findByUsername(username);

        List<Post> likedPosts = user.getLikedPosts();

        if (!likedPosts.contains(post)) return;

        if (likedPosts.remove(post)) {

            post.setLikes(post.getLikes() -1);

            postRepository.save(post);
        }
    }
}
