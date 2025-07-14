package com.reservly.users.mapper;

import com.reservly.users.client.keycloak.Model.request.KcCreateUserRequestDto;
import com.reservly.users.model.dao.UserEntity;
import com.reservly.users.model.dto.request.CreateUserRequestDto;
import com.reservly.users.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface GeneralMapper {

    @Mapping(target = "referenceId", source = "referenceId")
    @Mapping(target = "username", source = "createUserRequestDto.email", qualifiedByName = "getUsername")
    @Mapping(target = "email", source = "createUserRequestDto.email")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "firstName", source = "createUserRequestDto.firstName")
    @Mapping(target = "lastName", source = "createUserRequestDto.lastName")
    UserEntity toUserEntity(CreateUserRequestDto createUserRequestDto, String referenceId, String hashedPassword);


    @Mapping(target = "username", source = "userEntity.username")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "firstName", source = "userEntity.firstName")
    @Mapping(target = "lastName", source = "userEntity.lastName")
    @Mapping(target = "createdAt", source = "userEntity.createdAt")
    @Mapping(target = "updatedAt", source = "userEntity.updatedAt")
    UserDto toUserDto(UserEntity userEntity);


    @Mapping(target = "username", source = "createUserRequestDto.email", qualifiedByName = "getUsername")
    @Mapping(target = "email", source = "createUserRequestDto.email")
    @Mapping(target = "firstName", source = "createUserRequestDto.firstName")
    @Mapping(target = "lastName", source = "createUserRequestDto.lastName")
    @Mapping(target = "enabled", constant = "true")
    KcCreateUserRequestDto toKcCreateUserRequest(CreateUserRequestDto createUserRequestDto);


    @Named("getUsername")
    default String getUsername(String email){
        return email.split("@")[0];
    }
}
