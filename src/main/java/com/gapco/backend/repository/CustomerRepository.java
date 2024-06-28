package com.gapco.backend.repository;

import com.gapco.backend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.language = :language")
    Page<Customer> getAll(String language, Pageable pageable);
}
