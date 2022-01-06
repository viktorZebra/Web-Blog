package com.example.webblog.service

import com.example.webblog.exception.ForumNotFoundException
import com.example.webblog.model.Forums
import com.example.webblog.model.User
import com.example.webblog.model.mapper.ForumsMapper
import com.example.webblog.repository.custom.imp.ForumsRepositoryCustom
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

internal class ForumServiceTest {
    private val forumRepository = mockk<ForumsRepositoryCustom>()
    private val userService = mockk<UserService>()
    private val statisticsService = mockk<StatisticsService>()

    private var forumService: ForumService = ForumService(forumRepository, userService, statisticsService)

    private var notNullId: Int = 0
    private  var nullId: Int = -1

    private lateinit var forum: Forums

    private lateinit var user: User

    @BeforeEach
    fun beforeTests() {
        forum = Forums(id = 0,
            author_id = 0,
            title = "testForum",
            slug = "test-forum")

        user = User(id = 0,
            email = "test@tets.ru",
            count_view_profile = 0,
            fullname = "test Test",
            about = "test")

        every { forumRepository.getForumById(notNullId) } returns forum
        every { forumRepository.getForumById(nullId) } returns null
        every { forumRepository.save(forum) } returns forum
        every { forumRepository.getAllForums() } returns listOf(forum, forum)
        every { forumRepository.getForumBySlug(forum.slug) } returns forum

        every { userService.getUserById(forum.author_id.toString()) } returns user
    }

    @AfterEach
    fun afterTests() {
        unmockkAll()
    }

    @Test
    fun getForumById() {
        val expected = forum
        assertEquals(expected, forumService.getForumById(notNullId.toString()))
    }

    @Test
    fun getForumByIdWithException() {
        assertThrows<ForumNotFoundException>{ forumService.getForumById(nullId.toString()) }
    }

    @Test
    fun getAllForums() {
        assertEquals(listOf(forum, forum), forumService.getAllForums())
    }
}