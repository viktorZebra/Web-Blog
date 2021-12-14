package com.example.webblog.exception

import com.example.webblog.model.entity.UserEntity

class UserAlreadyCreatedException(val userModel: UserEntity?) : RuntimeException() {
}
