package com.example.webblog.model.mapper

import com.example.webblog.model.ThreadVotes
import com.example.webblog.model.dto.ThreadVotesDto
import com.example.webblog.model.entity.ThreadVotesEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface ThreadVotesMapper {
    fun convertEntityToModel(threadVotesEntity: ThreadVotesEntity) : ThreadVotes

    fun convertModelToEntity(threadVotes: ThreadVotes) : ThreadVotesEntity

    fun convertModelToDto(threadVotes: ThreadVotes) : ThreadVotesDto

    fun convertDtoToModel(threadVotesDto: ThreadVotesDto) : ThreadVotes
}