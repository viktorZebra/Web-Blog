package com.example.webblog.controller

import com.example.webblog.model.dto.PostsDto
import com.example.webblog.model.dto.ThreadsDto
import com.example.webblog.model.mapper.PostsMapper
import com.example.webblog.model.mapper.ThreadsMapper
import com.example.webblog.service.PostService
import com.example.webblog.service.ThreadsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/thread")
class ThreadResource(val threadService: ThreadsService,
                     val postService: PostService,
                     val convertThreads: ThreadsMapper,
                     val convertPosts: PostsMapper
) {

    @GetMapping("/{id}")
    fun getThread(@PathVariable id: String): ResponseEntity<ThreadsDto>{
        val thread = threadService.getThreadById(id.toInt()).let { convertThreads.convertModelToDto(it) }

        return ResponseEntity(thread, HttpStatus.OK)
    }

    @GetMapping("/{id}/posts")
    fun getThreadPosts(@PathVariable id: String): ResponseEntity<List<PostsDto?>>{
        val posts = postService.getPostsByThreadId(id).filterNotNull().map { convertPosts.convertModelToDto(it) }

        return ResponseEntity(posts, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateThread(@RequestBody thread: ThreadsDto, @PathVariable id: String): ResponseEntity<ThreadsDto>{
        threadService.updateThread(thread.let { convertThreads.convertDtoToModel(it) }, id)

        return ResponseEntity(threadService.getThreadById(id.toInt()).let { convertThreads.convertModelToDto(it) }, HttpStatus.OK)
    }
}