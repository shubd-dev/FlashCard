package com.codingtutorial.flashcard.repository;

import com.codingtutorial.flashcard.models.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Page<Subject> findByOwnerId(Long ownerId, Pageable pageable);
}
