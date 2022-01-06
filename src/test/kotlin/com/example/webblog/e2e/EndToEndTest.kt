package com.example.webblog.e2e

import com.example.webblog.model.dto.ForumsDto
import com.example.webblog.model.dto.PostsDto
import com.example.webblog.model.dto.ThreadsDto
import com.example.webblog.model.dto.UserDto
import org.junit.Test
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
internal class EndToEndTest {

    private val headers = HttpHeaders()

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @ParameterizedTest
    @MethodSource("test")
    fun `should correct pass e2e post`() {
        val userDto = UserDto("Dmitry", "sh1228@mail.ru", "fury1228", "anon", 0, null)
        val forumDto = ForumsDto(1, "computers", "computers", null)
        val threadDto = ThreadsDto(null, 1, "another", 0, "dfdsgdf", "another", author_id = 1)
        val postDto = PostsDto(null, null, 1, 1, forum_id = 1, thread_id = 1, message = "hello it's my fish")

        val responseUserCreate = restTemplate.exchange(
            url("api/v1/users"),
            HttpMethod.POST,
            HttpEntity(userDto, headers),
            UserDto::class.java
        )

        assertEquals(HttpStatus.CREATED, responseUserCreate.statusCode)
        assertNotNull(responseUserCreate.body)
        assertEquals(userDto.email, responseUserCreate.body!!.email)

        val responseForumCreate = restTemplate.exchange(
            url("api/v1/forums"),
            HttpMethod.POST,
            HttpEntity(forumDto, headers),
            ForumsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, responseForumCreate.statusCode)
        assertNotNull(responseForumCreate.body)
        assertEquals(forumDto.author_id, responseForumCreate.body!!.author_id)

        val responseThreadCreate = restTemplate.exchange(
            url("api/v1/forums/1/threads"),
            HttpMethod.POST,
            HttpEntity(threadDto, headers),
            ThreadsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, responseThreadCreate.statusCode)
        assertNotNull(responseThreadCreate.body)
        assertEquals(threadDto.title, responseThreadCreate.body!!.title)

        val responsePostCreate = restTemplate.exchange(
            url("api/v1/forums/1/threads/1/posts"),
            HttpMethod.POST,
            HttpEntity(postDto, headers),
            PostsDto::class.java
        )

        assertEquals(HttpStatus.CREATED, responsePostCreate.statusCode)
        assertNotNull(responsePostCreate.body)
        assertEquals(postDto.message, responsePostCreate.body!!.message)
    }

    companion object {
        @JvmStatic
        fun `test`() = listOf(
            1
        )
    }
}