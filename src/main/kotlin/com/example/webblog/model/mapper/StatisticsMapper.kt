package com.example.webblog.model.mapper

import com.example.webblog.model.Statistics
import com.example.webblog.model.dto.StatisticsDto
import com.example.webblog.model.entity.StatisticsEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface StatisticsMapper {
    fun convertEntityToModel(statusEntity: StatisticsEntity) : Statistics

    fun convertModelToEntity(status: Statistics) : StatisticsEntity

    fun convertModelToDto(status: Statistics) : StatisticsDto

    fun convertDtoToModel(statusDto: StatisticsDto) : Statistics
}