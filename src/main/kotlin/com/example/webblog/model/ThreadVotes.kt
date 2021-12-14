package com.example.webblog.model

data class ThreadVotes(
    val voice: Int,
    val user_id: Int,
    val threads_id: Int,
    val id: Int
)