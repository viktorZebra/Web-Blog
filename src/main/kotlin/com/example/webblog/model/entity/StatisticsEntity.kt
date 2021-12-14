package com.example.webblog.model.entity

import org.springframework.data.relational.core.mapping.Table


@Table("statistics")
data class StatisticsEntity(
    val count_users: Int,
    val count_forums: Int,
    val count_threads: Int,
    val most_popular_user: Int,
    val most_viewed_profile: Int)