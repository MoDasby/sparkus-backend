package com.modasby.sparkusbackend.repository;

import com.modasby.sparkusbackend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
}
