package com.gapco.backend.repository;

import com.gapco.backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<Permission,Long> {
    Optional<Permission> findByPermissionName(String permissionName);
}
