package com.example.webblog.model.dto

import java.sql.Timestamp
import java.time.Instant

data class ThreadsDto(
    var id: Int? = null,
    var forum_id: Int,
    var title: String,
    var votes: Int,
    var message: String,
    var slug: String,
    var created: Timestamp = Timestamp.from(Instant.now()),
    var author_id: Int
)