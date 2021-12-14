package com.example.webblog.repository

import com.example.webblog.model.entity.PostsEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostsRepository : CrudRepository<PostsEntity, Int> {
    @Query("select * from posts where thread_id = :id")
    fun getPostsByThreadId(@Param("id") id: Int): MutableList<PostsEntity?>

    @Query("select * from posts where id = :id")
    fun getPostById(@Param("id") id: Int): PostsEntity?
}