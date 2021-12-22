package com.example.webblog.repository.custom.imp

import com.example.webblog.model.Posts
import com.example.webblog.model.Threads
import com.example.webblog.model.User
import com.example.webblog.model.entity.PostsEntity
import com.example.webblog.model.mapper.PostsMapper
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.PostsRepository
import com.example.webblog.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
class PostsRepositoryCustom @Autowired constructor(private val rep: PostsRepository,
                                                   private val convert: PostsMapper) {

    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun getAll(): List<Posts>{
        return rep.findAll().map { convert.convertEntityToModel(it) }
    }

    fun save(posts: Posts): Posts {

        return rep.save(
            posts.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun getPostsByThreadId(id: Int): MutableList<Posts?>{
        val posts = rep.getPostsByThreadId(id)

        val postsMutList: MutableList<Posts?> = mutableListOf()
        for (post in posts){
            if (post != null)
                postsMutList.add(post.let { convert.convertEntityToModel(it) })
        }

        return postsMutList
    }

    fun getPostById(id: Int): Posts?{
        val posts = rep.getPostById(id)

        if(posts != null){
            return posts.let { convert.convertEntityToModel(it) }
        }

        return posts
    }
}