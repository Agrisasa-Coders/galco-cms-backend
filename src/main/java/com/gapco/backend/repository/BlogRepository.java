package com.gapco.backend.repository;


import com.gapco.backend.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {

    @Query("SELECT b FROM Blog b WHERE b.language = :language")
    Page<Blog> getAll(String language, Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.service.id = :serviceId")
    List<Blog> getServiceBlogs(Long serviceId);
}
