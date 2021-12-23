package com.example.webblog.service

import com.example.webblog.exception.UserAlreadyCreatedException
import com.example.webblog.exception.UserNotFoundException
import com.example.webblog.model.User
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.custom.imp.UserRepositoryCustom
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepositoryCustom, val statisticsService: StatisticsService) {

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
        val user: User? = userRepository.getUserByNickname(nick)
        if (user != null) {
            return user
        }
        else{
            throw UserNotFoundException("Can't find user by nickname")
        }
    }

    fun getUserByEmail(email: String): User
    {
        val user: User? = userRepository.getUserByEmail(email)
        if (user != null) {
            return user
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
        userRepository.save(user)

        statisticsService.updateUsers()
    }

    fun updateProfile(newUser: User, id: String) {
        val currentUser = getUserById(id)
        val userWithEmailForReplace = userRepository.getUserByEmail(newUser.email)

        if (userWithEmailForReplace == null || currentUser.email == newUser.email) {
            newUser.id = id.toInt()
            userRepository.save(newUser)

        } else {
            throw UserAlreadyCreatedException(userWithEmailForReplace)
        }
    }

    fun getAllUser(): List<User>{
        return userRepository.selectAll()
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