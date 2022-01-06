package com.example.webblog.model.dto


data class ForumsDto(
    var author_id: Int,
    var title: String,
    var slug: String,
    var id: Int? = null
)