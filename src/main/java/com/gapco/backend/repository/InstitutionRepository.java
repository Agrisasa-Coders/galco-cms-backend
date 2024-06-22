package com.gapco.backend.repository;


import com.gapco.backend.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution,Long> {
    Optional<Institution> findByInstitutionId(Integer institutionId);
    Optional<Institution> findByName(String name);

    @Query(value = "SELECT max(institutionId) FROM Institution")
    int getMaxInstitutionId();

    @Query(value = "SELECT i FROM Institution i WHERE i.institutionId <> 0 AND i.state=1")
    Page<Institution> getAllActiveMembers(Pageable pageable);

    @Query(value = "SELECT i FROM Institution i WHERE i.institutionId=0 AND i.state=1")
    Page<Institution> getSuperInstitution(Pageable pageable);
    
    @Query(value = "SELECT i FROM Institution i WHERE i.institutionType='TEAM' AND i.state=1")
    Page<Institution> getAllActiveTeams(Pageable pageable);

    @Query(value = "SELECT i FROM Institution i WHERE i.institutionType='SHOP' AND i.state=1")
    Page<Institution> getAllActiveShops(Pageable pageable);

}
