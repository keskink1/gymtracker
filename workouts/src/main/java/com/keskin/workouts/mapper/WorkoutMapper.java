package com.keskin.workouts.mapper;

import com.keskin.workouts.dto.WorkoutItemDto;
import com.keskin.workouts.dto.requests.CreateWorkoutItemRequestDto;
import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutItemRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.dto.WorkoutDto;
import com.keskin.workouts.entity.Workout;
import com.keskin.workouts.entity.WorkoutItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {
    WorkoutDto entityToDto(Workout workout);

    Workout createRequestToEntity(CreateWorkoutRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Workout updateRequestToEntity(UpdateWorkoutRequestDto requestDto, @MappingTarget Workout workout);


    //ignore fields because types are different
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    @Mapping(target = "workout", ignore = true)
    WorkoutItem workoutItemRequestToEntity(CreateWorkoutItemRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateWorkoutItemFromDto(UpdateWorkoutItemRequestDto itemDto, @MappingTarget WorkoutItem item);

    @Mapping(source = "exercise.id", target = "exerciseId")
    @Mapping(source = "exercise.name", target = "exerciseName")
    WorkoutItemDto itemToItemDto(WorkoutItem item);
}
