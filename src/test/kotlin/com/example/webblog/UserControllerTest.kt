package com.example.webblog

import com.example.webblog.model.UserModel
import com.example.webblog.service.UserService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTests {
    private val headers = HttpHeaders()

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var service: UserService

    @BeforeEach
    fun before(){

    }

    private fun url(s: String): String {
        return "http://localhost:${port}/api/v1/user/${s}"
    }

    @Test
    fun getUserProfile() {
        val response = restTemplate.exchange(
            url("1"),
            HttpMethod.GET,
            HttpEntity("1", headers),
            UserModel::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals("ilya", response.body!!.fullname)
    }

    @Test
    fun createUser() {
        val user: UserModel = UserModel(fullname = "test",
                                        email = "test@test.ru",
                                        nickname = "null",
                                        about = "test",
                                        count_view_profile = 1,
                                        id = 0)
        val response = restTemplate.exchange(
            url(""),
            HttpMethod.POST,
            HttpEntity(user, headers),
            UserModel::class.java
        )

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertNotNull(response.body)
        assertEquals("test", response.body!!.fullname)
    }

    @Test
    fun updateUserProfile() {
        val user: UserModel = UserModel(fullname = "test",
            email = "newTest@test.ru",
            nickname = "null",
            about = "test",
            count_view_profile = 1,
            id = 1)
        val response = restTemplate.exchange(
            url("1"),
            HttpMethod.PUT,
            HttpEntity(user, headers),
            UserModel::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals("newTest@test.ru", response.body!!.email)
    }
}