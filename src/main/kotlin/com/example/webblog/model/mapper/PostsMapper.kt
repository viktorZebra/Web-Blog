package com.example.webblog.model.mapper

import com.example.webblog.model.Posts
import com.example.webblog.model.dto.PostsDto
import com.example.webblog.model.entity.PostsEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PostsMapper {
    fun convertEntityToModel(postsEntity: PostsEntity) : Posts

    fun convertModelToEntity(posts: Posts) : PostsEntity

    fun convertModelToDto(posts: Posts) : PostsDto

    fun convertDtoToModel(postsDto: PostsDto) : Posts
}