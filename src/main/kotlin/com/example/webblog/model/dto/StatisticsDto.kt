package com.example.webblog.model.dto

data class StatisticsDto(
    var id: Int,
    var count_users: Int,
    var count_forums: Int,
    var count_threads: Int,
    var most_popular_user: Int? = null,
    var most_viewed_profile: Int? = null
)