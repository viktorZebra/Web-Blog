package com.example.webblog.model.entity

import org.springframework.data.relational.core.mapping.Table

@Table("thread_votes")
data class ThreadVotesEntity(
    val voice: Int,
    val user_id: Int,
    val threads_id: Int,
    val id: Int
    )