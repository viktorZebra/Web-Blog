package com.example.webblog.model.dto

import java.sql.Timestamp
import java.time.Instant


data class PostsDto(
    var path: MutableList<Int>? = null,
    var id: Int? = null,
    var parent: Int,
    var author_id: Int,
    var modified: Timestamp? = null,
    var forum_id: Int,
    var created: Timestamp = Timestamp.from(Instant.now()),
    var message: String,
    var thread_id: Int
)