package com.example.webblog.exception

import com.example.webblog.model.User

class UserAlreadyCreatedException(val userModel: User?) : RuntimeException() {
}
