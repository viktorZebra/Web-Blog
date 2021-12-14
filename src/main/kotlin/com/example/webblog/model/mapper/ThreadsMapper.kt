package com.example.webblog.model.mapper

import com.example.webblog.model.Threads
import com.example.webblog.model.dto.ThreadsDto
import com.example.webblog.model.entity.ThreadsEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ThreadsMapper {
    fun convertEntityToModel(threadsEntity: ThreadsEntity) : Threads

    fun convertModelToEntity(threads: Threads) : ThreadsEntity

    fun convertModelToDto(threads: Threads) : ThreadsDto

    fun convertDtoToModel(threadsDto: ThreadsDto) : Threads
}