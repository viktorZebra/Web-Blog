package com.example.webblog.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("forum_users")
data class ForumUsersEntity(
    @Id var id: Int? = null,
    var user_id: Int,
    var forum_id: Int)