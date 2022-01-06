package com.example.webblog.service

import com.example.webblog.exception.ThreadAlreadyCreatedException
import com.example.webblog.exception.ThreadNotFoundException
import com.example.webblog.model.Forums
import com.example.webblog.model.Threads
import com.example.webblog.model.User
import com.example.webblog.repository.custom.imp.ForumsRepositoryCustom
import com.example.webblog.repository.custom.imp.ThreadsRepositoryCustom
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows

internal class ThreadsServiceTest {
    private val threadRepository = mockk<ThreadsRepositoryCustom>()
    private val userService = mockk<UserService>()
    private val forumService = mockk<ForumService>()
    private val forumUserService = mockk<ForumUsersService>()
    private val statisticsService = mockk<StatisticsService>()

    private var threadsService: ThreadsService = ThreadsService(threadRepository, userService, forumService, forumUserService, statisticsService)

    private var notNullId: Int = 0
    private  var nullId: Int = -1

    private lateinit var thread: Threads

    private lateinit var forum: Forums

    @BeforeEach
    fun beforeTests() {
        forum = Forums(id = 0,
            author_id = 0,
            title = "testForum",
            slug = "test-forum")

        thread = Threads(forum_id = 1,
        title = "test title",
        votes = 1,
        message = "test message",
        slug = "test-thread",
        author_id = 1)

        every { threadRepository.getThreadBySlug("test-thread") } returns thread
        every { threadRepository.getThreadBySlug("null") } returns null

        every { threadRepository.getThreadById(notNullId) } returns thread
        every { threadRepository.getThreadById(nullId) } returns null

        every { forumService.getForumById(notNullId.toString()) } returns forum
        every { threadRepository.getThreadByForum(notNullId) } returns mutableListOf(thread, thread)


    }

    @AfterEach
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun checkThreadExists() {
        threadsService.checkThreadExists("null")
    }

    @Test
    fun checkThreadExistsWithException() {
        assertThrows<ThreadAlreadyCreatedException> { threadsService.checkThreadExists("test-thread") }
    }

    @Test
    fun getThreadById() {
        assertEquals(thread, threadsService.getThreadById(notNullId))
    }

    @Test
    fun getThreadByIdWithException() {
        assertThrows<ThreadNotFoundException>{threadsService.getThreadById(nullId)}
    }

    @Test
    fun getThreadByForum() {
        assertEquals(mutableListOf(thread, thread), threadsService.getThreadByForum(notNullId))
    }
}