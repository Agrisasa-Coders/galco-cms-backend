package com.gapco.backend.repository;

import com.gapco.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE u.membershipNo IS NOT NULL AND u.membershipStatus = 'ACTIVE'")
    Page<User> getAllActiveMembers(Pageable pageable);
}
