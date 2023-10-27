package com.gerardojunior.registration.mappers;

import com.gerardojunior.registration.dto.RegisterRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserResponse map(User user);

    User map(RegisterRequest user, String hashedPassword);

    @AfterMapping
    default void afterMap(RegisterRequest user, String hashedPassword, @MappingTarget User userEntity) {
        userEntity.setPassword(hashedPassword);
    }

}
