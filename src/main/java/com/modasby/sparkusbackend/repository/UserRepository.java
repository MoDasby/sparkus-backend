package com.modasby.sparkusbackend.repository;

import com.modasby.sparkusbackend.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllByUsernameContaining(String username);
    List<User> findByOrderByCreationDateDesc(Pageable pageable);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
