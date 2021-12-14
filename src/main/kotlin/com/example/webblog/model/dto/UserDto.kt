package com.example.webblog.model.dto

data class UserDto(
    var fullname: String,
    var email: String,
    var nickname: String? = null,
    var about: String,
    var count_view_profile: Int,
    var id: Int
    )