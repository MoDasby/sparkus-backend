package com.modasby.sparkusbackend.repository;

import com.modasby.sparkusbackend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
        Page<Post> findByOrderByCreationDateDesc(Pageable pageable);

        @Query("select p from Post p where p.author.username like :username")
        Page<Post> findByAuthor_Username(@Param("username") String username, Pageable pageable);
}
