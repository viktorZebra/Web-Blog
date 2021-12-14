package com.example.webblog.service

import com.example.webblog.model.User
import com.example.webblog.model.entity.ForumUsersEntity
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.ForumUsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumUsersService @Autowired constructor(val forumUsersRepository: ForumUsersRepository,
                                               val userService: UserService,
                                               val forumService: ForumService,
                                               val convert: UserMapper
) {

    fun save(authorId: Int, forumId: Int) {
        if (isUserInForumExists(authorId, forumId)) {
            forumUsersRepository.save(ForumUsersEntity(user_id = authorId, forum_id = forumId))
        }
    }

    fun isUserInForumExists(userId: Int, forumId: Int): Boolean {
        return forumUsersRepository.isUserInForumExists(userId, forumId) == 0
    }

    fun getUsersByForum(forumId: Int): List<User?> {
        return forumUsersRepository.getUsersByForum(forumId)
                .filterNotNull()
                .map { convert.convertEntityToModel(it) }
    }
}