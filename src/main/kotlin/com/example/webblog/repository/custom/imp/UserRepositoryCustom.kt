package com.example.webblog.repository.custom.imp

import com.example.webblog.model.User
import com.example.webblog.model.entity.UserEntity
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
class UserRepositoryCustom @Autowired constructor(private val rep: UserRepository,
private val convert: UserMapper) {

    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(user: User): User{

        return rep.save(
            user.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun selectAll(): List<User>{
        return rep.selectAll().map { convert.convertEntityToModel(it) }
    }

    fun getUserByNickname(nickname: String): User?{
        val user = rep.getUserByNickname(nickname)

        if(user != null){
            return user.let { convert.convertEntityToModel(it) }
        }

        return user
    }

    fun getUserById(id: Int): User?{
        val user = rep.getUserById(id)

        if(user != null){
            return user.let { convert.convertEntityToModel(it) }
        }

        return user
    }

    fun isUserWithEmailExists(email: String): Int{
        return rep.isUserWithEmailExists(email)
    }

    fun getCountUsersByNickname(nickname: String): Int{
        return rep.isUserWithEmailExists(nickname)
    }

    fun getUserByEmail(email: String): User?{
        val user = rep.getUserByEmail(email)

        if(user != null){
            return user.let { convert.convertEntityToModel(it) }
        }

        return user
    }

}