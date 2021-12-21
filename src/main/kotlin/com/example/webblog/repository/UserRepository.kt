package com.example.webblog.repository

import com.example.webblog.model.entity.UserEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

interface UserRepository : CrudRepository<UserEntity, Int> {
    //@Query("select * from users")
    fun selectAll(): List<UserEntity>

    //@Query("select * from users where nickname = :nickname")
    fun getUserByNickname(nickname: String): UserEntity?

    //@Query("select * from users where id = :id")
    fun getUserById(id: Int): UserEntity?

    //@Query("select COUNT(*) from users where email = :email")
    fun isUserWithEmailExists(email: String): Int

    //@Query("select COUNT(*) from users where nickname = :nickname")
    fun getCountUsersByNickname(nickname: String): Int

    //@Query("select * from users where email = :email")
    fun getUserByEmail(email: String): UserEntity?
}


