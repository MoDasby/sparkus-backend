package com.modasby.sparkusbackend.repository;

import com.modasby.sparkusbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, String> {
        List<Post> findByOrderByCreationDateDesc();

}
