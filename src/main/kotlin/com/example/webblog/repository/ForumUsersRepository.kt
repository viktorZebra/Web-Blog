package com.example.webblog.repository

import com.example.webblog.model.entity.ForumUsersEntity
import com.example.webblog.model.entity.UserEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ForumUsersRepository : CrudRepository<ForumUsersEntity, Int> {

    @Query("select COUNT(*) from forum_users where user_id = :user_id and forum_id = :forum_id")
    fun isUserInForumExists(@Param("user_id") user_id: Int, @Param("forum_id") forum_id: Int): Int

    @Query("select * from forum_users join users on forum_users.user_id=users.id where forum_users.forum_id = :id")
    fun getUsersByForum(@Param("id") id: Int): MutableList<UserEntity?>
}
