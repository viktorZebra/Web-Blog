package com.example.webblog.controller

import com.example.webblog.model.dto.ForumsDto
import com.example.webblog.model.dto.PostsDto
import com.example.webblog.model.dto.ThreadsDto
import com.example.webblog.model.dto.UserDto
import com.example.webblog.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/database/dropDB.sql", "/database/initDB.sql")
class ControllerIntegrationTest {

    private val headers = HttpHeaders()

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }


    @ParameterizedTest
    @MethodSource("different usersDTO")
    fun `should correct create user`(userDto: UserDto) {
        val response = restTemplate.exchange(
            url("api/v1/users"),
            HttpMethod.POST,
            HttpEntity(userDto, headers),
            UserDto::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(userDto.email, response.body!!.email)
    }

    @ParameterizedTest
    @MethodSource("different forumDTO")
    fun `should correct create forum`(forumDto: ForumsDto) {
        val user = UserDto("Dmitry", "sh1111@mail.ru", "fury1111", "anon", 0, null)
        `should correct create user`(user)

        val response = restTemplate.exchange(
            url("api/v1/forums"),
            HttpMethod.POST,
            HttpEntity(forumDto, headers),
            ForumsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(forumDto.author_id, response.body!!.author_id)
    }

    @ParameterizedTest
    @MethodSource("different threadsDTO")
    fun `should correct create thread`(threadDto: ThreadsDto) {
        val forum = ForumsDto(1, "animals", "animals", null)
        `should correct create forum`(forum)

        val response = restTemplate.exchange(
            url("api/v1/forums/1/threads"),
            HttpMethod.POST,
            HttpEntity(threadDto, headers),
            ThreadsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(threadDto.message, response.body!!.message)
    }

    @ParameterizedTest
    @MethodSource("different postsDTO")
    fun `should correct create posts`(postsDto: PostsDto) {
        val thread = ThreadsDto(null, 1, "cats", 0, "my cat", "cats", author_id = 1)
        `should correct create thread`(thread)

        val response = restTemplate.exchange(
            url("api/v1/forums/1/threads/1/posts"),
            HttpMethod.POST,
            HttpEntity(postsDto, headers),
            PostsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals(postsDto.message, response.body!!.message)
    }

    companion object {
        @JvmStatic
        fun `different usersDTO`() = listOf(
            UserDto("Dmitry", "sh1@mail.ru", "fury1", "anon", 0, null),
            UserDto("ilya1", "avito111@mail.ru", "fury111", "anon1", 0, null),
            UserDto("ilya2", "mail1@mail.ru", "1005001", "anon2", 0, null)
        )

        @JvmStatic
        fun `different forumDTO`() = listOf(
            ForumsDto(1, "animals", "animals", null),
            ForumsDto(1, "cars", "cars", null),
            ForumsDto(1, "computers", "computers", null)
        )

        @JvmStatic
        fun `different threadsDTO`() = listOf(
            ThreadsDto(null, 1, "cats", 0, "my cat", "cats", author_id = 1),
            ThreadsDto(null, 1, "dogs", 0, "my dog", "dogs", author_id = 1),
            ThreadsDto(null, 1, "another", 0, "dfdsgdf", "another", author_id = 1)
        )

        @JvmStatic
        fun `different postsDTO`() = listOf(
            PostsDto(null, null, 0, 1, forum_id = 1, thread_id = 1, message = "hello it's my cat"),
            PostsDto(null, null, 1, 1, forum_id = 1, thread_id = 1, message = "hello it's my dog"),
            PostsDto(null, null, 1, 1, forum_id = 1, thread_id = 1, message = "hello it's my fish")

        )
    }
}