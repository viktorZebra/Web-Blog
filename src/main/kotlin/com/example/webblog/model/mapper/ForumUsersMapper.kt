package com.example.webblog.model.mapper

import com.example.webblog.model.ForumUsers
import com.example.webblog.model.dto.ForumUsersDto
import com.example.webblog.model.entity.ForumUsersEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ForumUsersMapper {
    fun convertEntityToModel(forumUsersEntity: ForumUsersEntity) : ForumUsers

    fun convertModelToEntity(forumUsers: ForumUsers) : ForumUsersEntity

    fun convertModelToDto(forumUsers: ForumUsers) : ForumUsersDto

    fun convertDtoToModel(forumUsersDto: ForumUsersDto) : ForumUsers
}