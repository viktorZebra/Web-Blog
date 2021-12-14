package com.example.webblog.model.mapper

import com.example.webblog.model.Forums
import com.example.webblog.model.dto.ForumsDto
import com.example.webblog.model.entity.ForumsEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ForumsMapper {
    fun convertEntityToModel(forumsEntity: ForumsEntity) : Forums

    fun convertModelToEntity(forums: Forums) : ForumsEntity

    fun convertModelToDto(forums: Forums) : ForumsDto

    fun convertDtoToModel(forumsDto: ForumsDto) : Forums
}