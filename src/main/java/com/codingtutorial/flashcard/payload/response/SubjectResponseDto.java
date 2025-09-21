package com.codingtutorial.flashcard.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class SubjectResponseDto {
    private Long id;
    private String subjectName;
    private List<CardResponseDto> cards;
}
