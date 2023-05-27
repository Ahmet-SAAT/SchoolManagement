package com.schoolmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GuestUserRepository extends JpaRepository<GuestUserRepository,Long> {
    boolean existsByUsername(String username);

    
    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);
}
