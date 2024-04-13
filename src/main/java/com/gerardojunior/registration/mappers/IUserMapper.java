package com.gerardojunior.registration.mappers;

import com.gerardojunior.registration.dto.RegisterUserRequest;
import com.gerardojunior.registration.dto.UpdateUserRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public  interface IUserMapper {

    UserResponse map(User user);

    @Mapping(target = "password", ignore = true)
    User map(RegisterUserRequest user);

    void merge(UpdateUserRequest updateUser, @MappingTarget User user);

}
