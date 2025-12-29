package com.keskin.exercises.mapper;

import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;
import com.keskin.exercises.entity.Exercise;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDto entityToDto(Exercise exercise);

    Exercise createRequestToEntity(CreateExerciseRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Exercise updateRequestToEntity(UpdateExerciseRequestDto requestDto, @MappingTarget Exercise exercise);
}
