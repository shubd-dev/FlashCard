package com.codingtutorial.flashcard.controllers;

import com.codingtutorial.flashcard.payload.request.SubjectRequestDto;
import com.codingtutorial.flashcard.payload.response.SubjectResponseDto;
import com.codingtutorial.flashcard.services.SubjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDto> createSubject(@Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectResponseDto created = subjectService.createSubject(subjectRequestDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<SubjectResponseDto>> getAllSubjects(Pageable pageable) {
        Page<SubjectResponseDto> subjects = subjectService.getAllSubjects(pageable);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable Long id) {
        SubjectResponseDto subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequestDto subjectRequestDto) {
        SubjectResponseDto updatedSubject = subjectService.updateSubject(id, subjectRequestDto);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

}
