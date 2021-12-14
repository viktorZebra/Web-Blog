package com.example.webblog.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp
import java.time.Instant

@Table("threads")
data class ThreadsModel(@Id var id: Int? = null,
                        var forum_id: Int,
                        var title: String,
                        var votes: Int,
                        var message: String,
                        var slug: String,
                        var created: Timestamp = Timestamp.from(Instant.now()),
                        var author_id: Int)