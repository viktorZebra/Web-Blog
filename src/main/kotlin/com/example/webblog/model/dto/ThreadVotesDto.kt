package com.example.webblog.model.dto

data class ThreadVotesDto(
    val voice: Int,
    val user_id: Int,
    val threads_id: Int,
    val id: Int
)