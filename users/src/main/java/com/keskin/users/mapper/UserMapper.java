package com.keskin.users.mapper;

import com.keskin.users.dto.request.RegisterRequestDto;
import com.keskin.users.dto.request.UpdateUserRequestDto;
import com.keskin.users.dto.UserDto;
import com.keskin.users.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);

    User createRequestToEntity(RegisterRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateRequestToEntity(UpdateUserRequestDto requestDto, @MappingTarget User user);
}
