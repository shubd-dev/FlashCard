package com.codingtutorial.flashcard.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequestDto {
    @NotBlank
    private String subjectName;
}
