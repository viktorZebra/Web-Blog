package com.example.webblog.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp
import java.time.Instant

@Table("posts")
data class PostsModel(var path: MutableList<Int>? = null,
                      @Id var id: Int? = null,
                      var parent: Int,
                      var author_id: Int,
                      var modified: Timestamp? = null,
                      var forum_id: Int,
                      var created: Timestamp = Timestamp.from(Instant.now()),
                      var message: String,
                      var thread_id: Int)