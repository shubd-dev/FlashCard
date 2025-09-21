package com.codingtutorial.flashcard.controllers;

import com.codingtutorial.flashcard.payload.request.CardRequestDto;
import com.codingtutorial.flashcard.payload.response.CardResponseDto;
import com.codingtutorial.flashcard.services.CardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CardRequestDto cardRequestDto) {
        CardResponseDto created = cardService.createCard(cardRequestDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Page<CardResponseDto>> getAllCards(Pageable pageable) {
        Page<CardResponseDto> cards = cardService.getAllCards(pageable);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long id) {
        CardResponseDto card = cardService.getCardById(id);
        return ResponseEntity.ok(card);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long id, @Valid @RequestBody CardRequestDto cardRequestDto) {
        CardResponseDto updatedCard = cardService.updateCard(id, cardRequestDto);
        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
