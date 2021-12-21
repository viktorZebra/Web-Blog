package com.example.webblog.repository


import com.example.webblog.model.entity.ForumsEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


interface ForumsRepository : CrudRepository<ForumsEntity, Int> {

    @Query("select * from forums where slug = :slug")
    fun getForumBySlug(@Param("slug") slug: String): ForumsEntity?

    @Query("select * from forums where id = :id")
    fun getForumById(@Param("id") id: Int): ForumsEntity?

    @Query("select COUNT(*) from forums where slug = :slug")
    fun getCountForum(@Param("slug") slug: String): Int
}