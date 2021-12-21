package com.example.webblog.service

import com.example.webblog.exception.UserAlreadyCreatedException
import com.example.webblog.exception.UserNotFoundException
import com.example.webblog.model.User
import com.example.webblog.model.entity.UserEntity
import com.example.webblog.model.mapper.UserMapper

import com.example.webblog.repository.UserRepository
import com.example.webblog.repository.custom.imp.UserRepositoryImpl
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepositoryImpl,
                  val convert: UserMapper
) {

    fun getUserById(id: String): User
    {
        val user: User? = userRepository.getUserById(id.toInt())
        if (user != null) {
            return user
        }
        else{
            throw UserNotFoundException("Can't find user by id")
        }
    }

    fun getUserByNickname(nick: String): User
    {
        TODO()
//        val user: UserEntity? = userRepository.getUserByNickname(nick)
//        if (user != null) {
//            return user.let { convert.convertEntityToModel(it) }
//        }
//        else{
//            throw UserNotFoundException("Can't find user by nickname")
//        }
    }

    fun getUserByEmail(email: String): User
    {
        TODO()
//        val user: UserEntity? = userRepository.getUserByEmail(email)
//        if (user != null) {
//            return user.let { convert.convertEntityToModel(it) }
//        }
//        else{
//            throw UserNotFoundException("Can't find user by email")
//        }
    }

    fun isUserWithEmailExists(email: String): Int {
        TODO()
//        return userRepository.isUserWithEmailExists(email)
    }

    fun isUserWithNicknameExists(nick: String): Boolean {
        TODO()
//        if (userRepository.getCountUsersByNickname(nick) != 0)
//            return true
//        else
//            throw UserNotFoundException("Can't find user")
    }

    fun create(user: User) {
        TODO()
//        checkUserExists(user.email, user.nickname!!)
//        userRepository.save(user.let { convert.convertModelToEntity(it) })
    }

    fun updateProfile(newUser: User, id: String) {
//        val currentUser = getUserById(id)
//        val userWithEmailForReplace = userRepository.getUserByEmail(newUser.email)
//
//        if (userWithEmailForReplace == null || currentUser.email == newUser.email) {
//            newUser.id = id.toInt()
//            userRepository.save(newUser.let { convert.convertModelToEntity(it) })
//
//        } else {
//            throw UserAlreadyCreatedException(userWithEmailForReplace)
//        }
        TODO()
    }

    private fun checkUserExists(email: String, nickname: String) {
        TODO()
//        val existedUserByEmail = userRepository.getUserByEmail(email)
//        val existedUserByNickname = userRepository.getUserByNickname(nickname)
//
//        if (existedUserByEmail != null)
//            throw UserAlreadyCreatedException(existedUserByEmail)
//
//        if (existedUserByNickname != null)
//            throw UserAlreadyCreatedException(existedUserByNickname)
    }
}