package com.modasby.sparkusbackend.unit.data;

import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    User user;
    User user1;

    @BeforeEach
    public void setUp() {
        user = new User();

        user.setUsername("giulliano");
        user.setName("giulliano");
        user.setEmail("giulliano@email");
        user.setPassword("");

        user1 = new User();

        user1.setUsername("giulliano23");
        user1.setName("giulliano2");
        user1.setEmail("giulliano@email2");
        user1.setPassword("");

        userRepository.save(user);
        userRepository.save(user1);
    }

    @Test
    public void testFindByUsername() {
        assertThat(userRepository.findByUsername(user.getUsername()).isPresent()).isTrue();
        assertThat(userRepository.findByUsername("user that not exists").isPresent()).isFalse();
    }

    @Test
    public void testFindByEmail() {
        assertThat(userRepository.findByEmail(user.getEmail()).isPresent()).isTrue();
        assertThat(userRepository.findByEmail("email that not exists").isPresent()).isFalse();
    }

    @Test
    public void testFindAllByUsernameContaining() {
        assertThat(userRepository.findAllByUsernameContaining("giu").size()).isEqualTo(2);
        assertThat(userRepository.findAllByUsernameContaining("user that not exists").size()).isEqualTo(0);
    }
}
