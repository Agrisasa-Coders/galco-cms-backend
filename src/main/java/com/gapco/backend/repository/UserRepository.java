package com.gapco.backend.repository;

import com.gapco.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailOrPhoneNumber(String email,String phoneNumber);
    Optional<User> findByEmail(String email);
}
