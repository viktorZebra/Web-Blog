package com.example.webblog.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class UserModel(var fullname: String,
                     var email: String,
                     var nickname: String? = null,
                     var about: String,
                     var count_view_profile: Int,
                     @Id var id: Int)
