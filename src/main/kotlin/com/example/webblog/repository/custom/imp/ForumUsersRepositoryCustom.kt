package com.example.webblog.repository.custom.imp

import com.example.webblog.model.ForumUsers
import com.example.webblog.model.Posts
import com.example.webblog.model.User
import com.example.webblog.model.entity.UserEntity
import com.example.webblog.model.mapper.ForumUsersMapper
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.ForumUsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
class ForumUsersRepositoryCustom @Autowired constructor(private val rep: ForumUsersRepository,
                                                        private val convert: ForumUsersMapper,
                                                        private val userConvert: UserMapper){

    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(forumUsers: ForumUsers): ForumUsers {

        return rep.save(
            forumUsers.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun isUserInForumExists(user_id: Int, forum_id: Int): Int{
        return rep.isUserInForumExists(user_id, forum_id)
    }

    fun getUsersByForum(id: Int): MutableList<User?>{
        val forumUsers = rep.getUsersByForum(id)

        val forumUsersMutList: MutableList<User?> = mutableListOf()
        for (forumUser in forumUsers){
            if (forumUser != null)
                forumUsersMutList.add(forumUser.let { userConvert.convertEntityToModel(it) })
        }

        return forumUsersMutList
    }
}