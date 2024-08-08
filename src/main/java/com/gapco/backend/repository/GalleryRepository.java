package com.gapco.backend.repository;

import com.gapco.backend.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery,Long> {

    @Query("SELECT g FROM Gallery g WHERE g.service.id = :ServiceId")
    List<Gallery> getAllServicePhotos(Long ServiceId);
}
