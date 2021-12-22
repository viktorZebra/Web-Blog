package com.example.webblog.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("statistics")
data class StatisticsEntity(
    @Id var id: Int,
    var count_users: Int,
    var count_forums: Int,
    var count_threads: Int,
    var most_popular_user: Int? = null,
    var most_viewed_profile: Int? = null)