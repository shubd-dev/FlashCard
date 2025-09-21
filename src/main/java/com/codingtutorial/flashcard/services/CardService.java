package com.codingtutorial.flashcard.services;

import com.codingtutorial.flashcard.exception.AccessDeniedException;
import com.codingtutorial.flashcard.exception.ResourceNotFoundException;
import com.codingtutorial.flashcard.mappers.CardMapper;
import com.codingtutorial.flashcard.models.Card;
import com.codingtutorial.flashcard.models.Subject;
import com.codingtutorial.flashcard.models.User;
import com.codingtutorial.flashcard.payload.request.CardRequestDto;
import com.codingtutorial.flashcard.payload.response.CardResponseDto;
import com.codingtutorial.flashcard.repository.CardRepository;
import com.codingtutorial.flashcard.repository.SubjectRepository;
import com.codingtutorial.flashcard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final SubjectRepository subjectRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, UserRepository userRepository, SubjectRepository subjectRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.cardMapper = cardMapper;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public CardResponseDto createCard(CardRequestDto cardRequestDto) {
        User currentUser = getCurrentUser();
        Card card = cardMapper.toEntity(cardRequestDto);
        card.setOwner(currentUser);
        if (cardRequestDto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(cardRequestDto.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + cardRequestDto.getSubjectId()));
            card.setSubject(subject);
        }
        Card savedCard = cardRepository.save(card);
        return cardMapper.toDto(savedCard);
    }

    public Page<CardResponseDto> getAllCards(Pageable pageable) {
        User currentUser = getCurrentUser();
        Page<Card> cards = cardRepository.findByOwnerId(currentUser.getId(), pageable);
        return cards.map(cardMapper::toDto);
    }

    public CardResponseDto getCardById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id " + id));
        User currentUser = getCurrentUser();
        if (!card.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this card");
        }
        return cardMapper.toDto(card);
    }

    public CardResponseDto updateCard(Long id, CardRequestDto cardRequestDto) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id " + id));
        User currentUser = getCurrentUser();
        if (!card.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this card");
        }
        card.setQuestion(cardRequestDto.getQuestion());
        card.setAnswer(cardRequestDto.getAnswer());
        if (cardRequestDto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(cardRequestDto.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + cardRequestDto.getSubjectId()));
            card.setSubject(subject);
        }
        Card updatedCard = cardRepository.save(card);
        return cardMapper.toDto(updatedCard);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id " + id));
        User currentUser = getCurrentUser();
        if (!card.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to access this card");
        }
        cardRepository.delete(card);
    }

}
