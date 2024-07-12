package com.modasby.sparkusbackend.unit.data;

import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import com.modasby.sparkusbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user = new User();

        user.setUsername("giulliano");
        user.setName("giulliano");
        user.setEmail("");
        user.setPassword("");

        userRepository.save(user);
    }

    @Test
    public void testFeed() {
        List<Post> posts = Arrays.asList(
                new Post("OLÁ", 0, user),
                new Post("OLÁ", 0, user),
                new Post("OLÁ", 0, user)
        );

        postRepository.saveAll(posts);

        Page<Post> feedPosts = postRepository.findByOrderByCreationDateDesc(Pageable.ofSize(20));

        List<Post> feedPostsSorted = new ArrayList<>(feedPosts.getContent());

        feedPostsSorted.sort(Comparator.comparing(Post::getCreationDate));

        for (int i = 0; i >= feedPosts.getContent().size() - 1; i++) {
            assertThat(feedPosts.getContent().get(i).getCreationDate().compareTo(feedPostsSorted.get(i).getCreationDate())).isEqualTo(0);
        }
    }

    @Test
    public void testFindPostByUsername() {
        User user1 = new User();

        user1.setUsername("giulliano23");
        user1.setName("giulliano2");
        user1.setEmail("giulliano@email2");
        user1.setPassword("");

        userRepository.save(user1);

        postRepository.saveAll(
            Arrays.asList(
                    new Post("OLÁ", 0, user),
                    new Post("OLÁ", 0, user),
                    new Post("OLÁ", 0, user),
                    new Post("OLÁ", 0, user1)
            )
        );

        Page<Post> posts = postRepository.findByAuthor_Username("giulliano", Pageable.unpaged());

        assertThat(posts.getContent().size()).isEqualTo(3);
    }
}
