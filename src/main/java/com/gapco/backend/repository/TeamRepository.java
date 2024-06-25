package com.gapco.backend.repository;

import com.gapco.backend.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("SELECT t FROM Team t WHERE t.language = :language")
    Page<Team> getAll(String language, Pageable pageable);
}
