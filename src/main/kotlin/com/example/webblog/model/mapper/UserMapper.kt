package com.example.webblog.model.mapper

import com.example.webblog.model.User
import com.example.webblog.model.dto.UserDto
import com.example.webblog.model.entity.UserEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun convertEntityToModel(userEntity: UserEntity) : User

    fun convertModelToEntity(user: User) : UserEntity

    fun convertModelToDto(user: User) : UserDto

    fun convertDtoToModel(userDto: UserDto) : User
}