package com.gapco.backend.repository;


import com.gapco.backend.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology,Long> {
}
