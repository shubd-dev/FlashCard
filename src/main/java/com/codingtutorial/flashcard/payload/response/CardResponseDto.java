package com.codingtutorial.flashcard.payload.response;

import lombok.Data;

@Data
public class CardResponseDto {
    private Long id;
    private String question;
    private String answer;
    private String subjectName;
}
