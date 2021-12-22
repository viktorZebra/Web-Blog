package com.example.webblog.repository

import com.example.webblog.model.entity.PostsEntity
import com.example.webblog.model.entity.StatisticsEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


interface StatisticsRepository : CrudRepository<StatisticsEntity, Int> {
    @Query("select count_forums from statistics")
    fun forums(): Int

    @Query("select count_users from statistics")
    fun users(): Int

    @Query("select count_threads from statistics")
    fun threads(): Int

    @Query("select * from statistics where id = 0")
    fun getStatistics(): StatisticsEntity
}