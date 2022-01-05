package com.example.webblog.controller

import com.example.webblog.model.dto.UserDto
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.service.ForumUsersService
import com.example.webblog.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/users")
class UserResource(val userService: UserService, val convert: UserMapper, val forumUserService: ForumUsersService) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<UserDto>{
        val user = userService.getUserById(id).let { convert.convertModelToDto(it) }

        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping
    fun getAllUser(@RequestParam(required = false) forumId: String?): ResponseEntity<Any?>{
        return if (forumId.isNullOrEmpty()) {
            val users_ = userService.getAllUser().map { convert.convertModelToDto(it) }
            ResponseEntity(users_, HttpStatus.OK)
        } else {
            val usersList = forumUserService.getUsersByForum(forumId.toInt())
            ResponseEntity(usersList.filterNotNull().map { convert.convertModelToDto(it) }, HttpStatus.OK)
        }
    }

    @PostMapping
    fun createUser(@RequestBody user: UserDto): ResponseEntity<UserDto>{
        userService.create(user.let { convert.convertDtoToModel(it) })

        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(@RequestBody user: UserDto, @PathVariable id: String): ResponseEntity<UserDto>{
        userService.updateProfile(user.let { convert.convertDtoToModel(it) }, id)

        return ResponseEntity(userService.getUserById(id).let { convert.convertModelToDto(it) }, HttpStatus.OK)
    }
}