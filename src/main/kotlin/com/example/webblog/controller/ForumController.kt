package com.example.webblog.controller

import com.example.webblog.model.User
import com.example.webblog.model.dto.ForumsDto
import com.example.webblog.model.dto.PostsDto
import com.example.webblog.model.dto.ThreadsDto
import com.example.webblog.model.dto.UserDto
import com.example.webblog.model.mapper.ForumsMapper
import com.example.webblog.model.mapper.PostsMapper
import com.example.webblog.model.mapper.ThreadsMapper
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.service.ForumService
import com.example.webblog.service.ForumUsersService
import com.example.webblog.service.PostService
import com.example.webblog.service.ThreadsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/forums")
class ForumResource(val forumService: ForumService,
                    val threadService: ThreadsService,
                    val forumUserService: ForumUsersService,
                    val convertForum: ForumsMapper,
                    val convertUser: UserMapper,
                    val convertThread: ThreadsMapper,
                    val postService: PostService,
                    val convertPost: PostsMapper) {

    @PostMapping
    fun createForum(@RequestBody forum: ForumsDto): ResponseEntity<ForumsDto>{
        forumService.create(forum.let { convertForum.convertDtoToModel(it) })

        return ResponseEntity(forum, HttpStatus.CREATED)
    }

    @PostMapping("/{id}/threads")
    fun createThread(@RequestBody thread: ThreadsDto, @PathVariable id: String): ResponseEntity<ThreadsDto>{
        thread.forum_id = id.toInt()
        threadService.createThread(thread.let { convertThread.convertDtoToModel(it) })

        return ResponseEntity(thread, HttpStatus.CREATED)
    }

    @PostMapping("/{id_forum}/threads/{id_thread}/posts")
    fun createNewPost(@RequestBody post: PostsDto,
                      @PathVariable id_forum: String,
                      @PathVariable id_thread: String): ResponseEntity<PostsDto>{
        post.forum_id = id_forum.toInt()
        post.thread_id = id_thread.toInt()

        postService.createPost(post.let { convertPost.convertDtoToModel(it) })

        return ResponseEntity(post, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllForums(): List<ForumsDto>{
        return forumService.getAllForums().map { convertForum.convertModelToDto(it) }
    }

    @GetMapping("/{id}")
    fun getForumById(@PathVariable id: String): ResponseEntity<Any> {
        return ResponseEntity(forumService.getForumById(id).let { convertForum.convertModelToDto(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id}/threads")
    fun getThreadByForum(@PathVariable id: String): ResponseEntity<List<ThreadsDto?>>{
        val threads = threadService.getThreadByForum(id.toInt())

        return ResponseEntity(threads.filterNotNull().map { convertThread.convertModelToDto(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id_forum}/threads/{id_threads}/posts/{id_post}")
    fun getMessageInForumInThread(@PathVariable id_forum: String,
                                  @PathVariable id_threads: String,
                                  @PathVariable id_post: String): ResponseEntity<PostsDto>{
        return ResponseEntity(postService.getPostById(id_post).let { convertPost.convertModelToDto(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id_forum}/threads/{id_threads}/posts")
    fun getAllMessageInForumInThread(@PathVariable id_forum: String,
                                     @PathVariable id_threads: String): ResponseEntity<List<PostsDto>>{
        return ResponseEntity(postService.getAll().map { convertPost.convertModelToDto(it) }, HttpStatus.OK)
    }

    @GetMapping("/{id_forum}/threads/{id_thread}")
    fun getTreadByIdInForum(@PathVariable id_forum: String, @PathVariable id_thread: String): ResponseEntity<ThreadsDto>{
        return ResponseEntity(threadService.getThreadById(id_thread.toInt()).let { convertThread.convertModelToDto(it) }, HttpStatus.OK)
    }

    @PatchMapping("/{id_forum}/threads/{id_threads}/posts/{id_post}")
    fun editMessageInPost(@RequestBody message: String,
                          @PathVariable id_forum: String,
                          @PathVariable id_threads: String,
                          @PathVariable id_post: String): ResponseEntity<PostsDto>{
        val updatePost = postService.getPostById(id_post)
        updatePost.message = message
        postService.createPost(updatePost)
        return ResponseEntity(updatePost.let { convertPost.convertModelToDto(it) }, HttpStatus.OK)
    }

    @PutMapping("/{id_forum}/threads/{id_thread}")
    fun editThreadInForum(@RequestBody newMessage: String,
                          @PathVariable id_forum: String,
                          @PathVariable id_threads: String): ResponseEntity<ThreadsDto>{
        val updateThread = threadService.getThreadById(id_threads.toInt())
        updateThread.message = newMessage

        threadService.createThread(updateThread)

        return ResponseEntity(updateThread.let { convertThread.convertModelToDto(it) }, HttpStatus.OK)
    }
}