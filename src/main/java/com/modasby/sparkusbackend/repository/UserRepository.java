package com.modasby.sparkusbackend.repository;

import com.modasby.sparkusbackend.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findAllByUsernameContaining(String username);
    List<User> findByOrderByCreationDateDesc(Pageable pageable);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
