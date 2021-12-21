package com.example.webblog.repository.custom.api

import com.example.webblog.model.User

interface UserRepositoryCustom {
    fun customSave(user: User): User

    fun customDelete(user: User): User

    fun customDeleteById(user: User): User

    //@Query("select * from users")
    fun selectAll(): List<User>

    //@Query("select * from users where nickname = :nickname")
    fun getUserByNickname(nickname: String): User?

    //@Query("select * from users where id = :id")
    fun getUserById(id: Int): User?

    //@Query("select COUNT(*) from users where email = :email")
    fun isUserWithEmailExists(email: String): Int

    //@Query("select COUNT(*) from users where nickname = :nickname")
    fun getCountUsersByNickname(nickname: String): Int

    //@Query("select * from users where email = :email")
    fun getUserByEmail(email: String): User?
}