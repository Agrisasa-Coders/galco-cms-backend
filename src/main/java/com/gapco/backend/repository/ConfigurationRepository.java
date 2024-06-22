package com.gapco.backend.repository;

import com.gapco.backend.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ConfigurationRepository extends JpaRepository<Configuration,Integer> {

    @Query("SELECT n FROM Configuration n WHERE n.institution.id =:institutionId")
    Optional<Configuration> findInstitutionConfiguration(@Param("institutionId") Long institutionId);

}
