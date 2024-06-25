package com.gapco.backend.repository;



import com.gapco.backend.entity.Technology;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TechnologyRepository extends JpaRepository<Technology,Long> {

    @Query("SELECT tec FROM Technology tec WHERE tec.language = :language")
    Page<Technology> getAll(String language, Pageable pageable);
}
