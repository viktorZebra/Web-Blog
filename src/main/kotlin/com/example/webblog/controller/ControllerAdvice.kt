package com.example.webblog.controller

import com.example.webblog.exception.*
import com.example.webblog.model.Forums
import com.example.webblog.model.Threads
import com.example.webblog.model.User
import com.example.webblog.model.entity.ForumsEntity
import com.example.webblog.model.entity.ThreadsEntity
import com.example.webblog.model.entity.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundException(e: UserNotFoundException): ResponseEntity<Response> {
        val response = Response(e.message)
        return ResponseEntity<Response>(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserAlreadyCreatedException::class)
    fun userAlreadyCreatedException(e: UserAlreadyCreatedException): ResponseEntity<User> {
        return ResponseEntity<User>(e.userModel, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ForumAlreadyCreatedException::class)
    fun forumAlreadyCreatedException(e: ForumAlreadyCreatedException): ResponseEntity<Forums> {
        return ResponseEntity<Forums>(e.forumsModel, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ForumNotFoundException::class)
    fun forumNotFoundException(e: ForumNotFoundException): ResponseEntity<Response> {
        val response = Response(e.message)
        return ResponseEntity<Response>(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ThreadAlreadyCreatedException::class)
    fun threadAlreadyCreatedException(e: ThreadAlreadyCreatedException): ResponseEntity<Threads> {
        return ResponseEntity<Threads>(e.existedThread, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(ThreadNotFoundException::class)
    fun threadNotFoundException(e: ThreadNotFoundException): ResponseEntity<Response> {
        val response = Response(e.message)
        return ResponseEntity<Response>(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PostNotFoundException::class)
    fun forumNotFoundException(e: PostNotFoundException): ResponseEntity<Response> {
        val response = Response(e.message)
        return ResponseEntity<Response>(response, HttpStatus.NOT_FOUND)
    }
}

class Response {
    var message: String? = null

    constructor(message: String?) {
        this.message = message
    }
}