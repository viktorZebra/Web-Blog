package com.example.webblog.service

import com.example.webblog.exception.PostNotFoundException
import com.example.webblog.model.Posts
import com.example.webblog.repository.custom.imp.PostsRepositoryCustom
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PostServiceTest {
    private val postRepository = mockk<PostsRepositoryCustom>()
    private val threadService = mockk<ThreadsService>()
    private val userService = mockk<UserService>()
    private val forumUserService = mockk<ForumUsersService>()
    private val forumService = mockk<ForumService>()

    private var postService: PostService = PostService(postRepository, threadService, userService, forumUserService, forumService)

    private lateinit var post: Posts

    @BeforeEach
    fun beforeTests() {
        post = Posts(parent = 1,
        author_id = 1,
        forum_id = 1,
        message = "test message",
        thread_id = 1)

        every { postRepository.getPostById(0) } returns post
        every { postRepository.getPostById(-1) } returns null

        every { postRepository.getAll() } returns listOf(post, post)
    }

    @Test
    fun getPostById() {
        assertEquals(post, postService.getPostById("0"))
    }

    @Test
    fun getPostByIdWithException() {
        assertThrows<PostNotFoundException> { postService.getPostById("-1") }
    }

    @Test
    fun getAll() {
        assertEquals(listOf(post, post), postService.getAll())
    }
}