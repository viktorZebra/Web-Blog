package com.example.webblog.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("forums")
data class ForumsEntity(
    var author_id: Int,
    var title: String,
    var slug: String,
    @Id var id: Int
    )