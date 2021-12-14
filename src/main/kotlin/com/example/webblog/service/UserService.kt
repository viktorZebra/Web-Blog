package com.example.webblog.service

import com.example.webblog.exception.UserAlreadyCreatedException
import com.example.webblog.exception.UserNotFoundException
import com.example.webblog.model.User
import com.example.webblog.model.entity.UserEntity
import com.example.webblog.model.mapper.UserMapper

import com.example.webblog.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepository,
                  val convert: UserMapper
) {

    fun getUserById(id: String): User
    {
        val user: UserEntity? = userRepository.getUserById(id.toInt())
        if (user != null) {
            return user.let { convert.convertEntityToModel(it) }
        }
        else{
            throw UserNotFoundException("Can't find user by id")
        }
    }

    fun getUserByNickname(nick: String): User
    {
        val user: UserEntity? = userRepository.getUserByNickname(nick)
        if (user != null) {
            return user.let { convert.convertEntityToModel(it) }
        }
        else{
            throw UserNotFoundException("Can't find user by nickname")
        }
    }

    fun getUserByEmail(email: String): User
    {
        val user: UserEntity? = userRepository.getUserByEmail(email)
        if (user != null) {
            return user.let { convert.convertEntityToModel(it) }
        }
        else{
            throw UserNotFoundException("Can't find user by email")
        }
    }

    fun isUserWithEmailExists(email: String): Int {
        return userRepository.isUserWithEmailExists(email)
    }

    fun isUserWithNicknameExists(nick: String): Boolean {
        if (userRepository.getCountUsersByNickname(nick) != 0)
            return true
        else
            throw UserNotFoundException("Can't find user")
    }

    fun create(user: User) {
        checkUserExists(user.email, user.nickname!!)
        userRepository.save(user.let { convert.convertModelToEntity(it) })
    }

    fun updateProfile(newUser: User, id: String) {
        val currentUser = getUserById(id)
        val userWithEmailForReplace = userRepository.getUserByEmail(newUser.email)

        if (userWithEmailForReplace == null || currentUser.email == newUser.email) {
            newUser.id = id.toInt()
            userRepository.save(newUser.let { convert.convertModelToEntity(it) })

        } else {
            throw UserAlreadyCreatedException(userWithEmailForReplace)
        }
    }

    private fun checkUserExists(email: String, nickname: String) {
        val existedUserByEmail = userRepository.getUserByEmail(email)
        val existedUserByNickname = userRepository.getUserByNickname(nickname)

        if (existedUserByEmail != null)
            throw UserAlreadyCreatedException(existedUserByEmail)

        if (existedUserByNickname != null)
            throw UserAlreadyCreatedException(existedUserByNickname)
    }
}