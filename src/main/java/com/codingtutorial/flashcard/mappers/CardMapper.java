package com.codingtutorial.flashcard.mappers;

import com.codingtutorial.flashcard.models.Card;
import com.codingtutorial.flashcard.models.Subject;
import com.codingtutorial.flashcard.payload.request.CardRequestDto;
import com.codingtutorial.flashcard.payload.response.CardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "subject", target = "subjectName")
    CardResponseDto toDto(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Card toEntity(CardRequestDto cardRequestDto);

    default String map(Subject subject) {
        return subject.getSubjectName();
    }
}
