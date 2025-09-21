package com.codingtutorial.flashcard.mappers;

import com.codingtutorial.flashcard.models.Subject;
import com.codingtutorial.flashcard.payload.request.SubjectRequestDto;
import com.codingtutorial.flashcard.payload.response.SubjectResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CardMapper.class)
public interface SubjectMapper {

    SubjectResponseDto toDto(Subject subject);

    Subject toEntity(SubjectRequestDto subjectRequestDto);
}
