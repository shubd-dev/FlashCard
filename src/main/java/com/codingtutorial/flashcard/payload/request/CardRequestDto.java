package com.codingtutorial.flashcard.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CardRequestDto {
    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    private Long subjectId;
}
