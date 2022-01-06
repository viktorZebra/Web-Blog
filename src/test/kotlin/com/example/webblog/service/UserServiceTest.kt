package com.example.webblog.service

import com.example.webblog.exception.ForumNotFoundException
import com.example.webblog.exception.UserNotFoundException
import com.example.webblog.model.User
import com.example.webblog.repository.custom.imp.ForumsRepositoryCustom
import com.example.webblog.repository.custom.imp.UserRepositoryCustom
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

internal class UserServiceTest {
    private val userRepository = mockk<UserRepositoryCustom>()
    private val statisticsService = mockk<StatisticsService>()

    private var notNullId: Int = 0
    private  var nullId: Int = -1

    private val userService: UserService = UserService(userRepository, statisticsService)

    private lateinit var user: User

    @BeforeEach
    fun beforeTests() {
        user = User(fullname = "test user",
                        email = "test@tets",
                        nickname = "testNick",
                        about = "im test user",
                        count_view_profile = 0,
                        id = 0)

        every { userRepository.getUserById(notNullId) } returns user
        every { userRepository.getUserById(nullId) } returns null

        every { userRepository.getUserByNickname("testNick") } returns user
        every { userRepository.getUserByNickname("null") } returns null

        every { userRepository.getUserByEmail("test@tets") } returns user
        every { userRepository.getUserByEmail("null") } returns null

        every { userRepository.selectAll() } returns listOf(user, user)
    }

    @AfterEach
    fun afterTests() {
        unmockkAll()
    }
    @Test
    fun getUserById() {
        assertEquals(0, userService!!.getUserById(notNullId.toString()).id)
    }

    @Test
    fun getUserByNickname() {
        assertEquals(user.nickname, userService.getUserByNickname("testNick").nickname)
    }

    @Test
    fun getUserByEmail() {
        assertEquals(user.email, userService.getUserByEmail("test@tets").email)
    }

    @Test
    fun getUserByIdWithException() {
        assertThrows<UserNotFoundException> { userService.getUserById(nullId.toString()) }
    }

    @Test
    fun getUserByNicknameWithException() {
        assertThrows<UserNotFoundException> { userService.getUserByNickname("null") }
    }

    @Test
    fun getUserByEmailWithException() {
        assertThrows<UserNotFoundException> { userService.getUserByEmail("null") }
    }

    @Test
    fun getAllUser() {
        assertEquals(listOf(user, user), userService.getAllUser())
    }
}