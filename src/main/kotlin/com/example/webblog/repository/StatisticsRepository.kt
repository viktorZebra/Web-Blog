package com.example.webblog.repository

import com.example.webblog.model.entity.StatisticsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


interface StatisticsRepository : CrudRepository<StatisticsEntity, Int> {
}