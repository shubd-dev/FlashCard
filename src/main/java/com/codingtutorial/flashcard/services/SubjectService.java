package com.codingtutorial.flashcard.services;

import com.codingtutorial.flashcard.exception.AccessDeniedException;
import com.codingtutorial.flashcard.exception.ResourceNotFoundException;
import com.codingtutorial.flashcard.mappers.SubjectMapper;
import com.codingtutorial.flashcard.models.Subject;
import com.codingtutorial.flashcard.models.User;
import com.codingtutorial.flashcard.payload.request.SubjectRequestDto;
import com.codingtutorial.flashcard.payload.response.SubjectResponseDto;
import com.codingtutorial.flashcard.repository.SubjectRepository;
import com.codingtutorial.flashcard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SubjectMapper subjectMapper;


    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.subjectMapper = subjectMapper;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public SubjectResponseDto createSubject(SubjectRequestDto subjectRequestDto) {
        User currentUser = getCurrentUser();
        Subject subject = subjectMapper.toEntity(subjectRequestDto);
        subject.setOwner(currentUser);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(savedSubject);
    }

    public Page<SubjectResponseDto> getAllSubjects(Pageable pageable) {
        User currentUser = getCurrentUser();
        Page<Subject> subjects = subjectRepository.findByOwnerId(currentUser.getId(), pageable);
        return subjects.map(subjectMapper::toDto);
    }

    public SubjectResponseDto getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
        User currentUser = getCurrentUser();
        if (!subject.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this subject");
        }
        return subjectMapper.toDto(subject);
    }

    public SubjectResponseDto updateSubject(Long id, SubjectRequestDto subjectRequestDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
        User currentUser = getCurrentUser();
        if (!subject.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this subject");
        }
        subject.setSubjectName(subjectRequestDto.getSubjectName());
        Subject updatedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(updatedSubject);
    }

    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + id));
        User currentUser = getCurrentUser();
        if (!subject.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this subject");
        }
        subjectRepository.delete(subject);
    }
}


