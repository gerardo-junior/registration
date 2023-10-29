package com.gerardojunior.registration.mappers;

import com.gerardojunior.registration.dto.RegisterRequest;
import com.gerardojunior.registration.dto.UserResponse;
import com.gerardojunior.registration.entity.meta.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserResponse map(User user);

    @Mapping(target = "password", ignore = true)
    User map(RegisterRequest user);

}
