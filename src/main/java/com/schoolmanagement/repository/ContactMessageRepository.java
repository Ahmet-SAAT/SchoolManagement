package com.schoolmanagement.repository;

import com.schoolmanagement.entity.concretes.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    boolean existsByEmailEqualsAndDateEquals(String email, LocalDate now);
    //email ve date bilgisi paremetrelerdei esit olan bir veri var mi

    //Page<ContactMessageResponse> degil POJO dönüs olmali
    Page<ContactMessage> findByEmailEquals(String email, Pageable pageable);

    //Page<ContactMessageResponse> degil POJO dönüs olmali
    Page<ContactMessage> findBySubjectEquals(String subject, Pageable pageable);
}
