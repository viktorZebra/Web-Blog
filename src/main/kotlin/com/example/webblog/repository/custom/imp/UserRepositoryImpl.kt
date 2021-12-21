package com.example.webblog.repository.custom.imp

import com.example.webblog.model.User
import com.example.webblog.model.entity.UserEntity
import com.example.webblog.model.mapper.UserMapper
import com.example.webblog.repository.custom.api.UserRepositoryCustom
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class UserRepositoryImpl @Autowired constructor(
    private val convert: UserMapper
) : UserRepositoryCustom {

    @PersistenceContext
    private val entityManager: EntityManager? = null

    override fun customSave(user: User): User {
        TODO("Not yet implemented")
    }

    override fun customDelete(user: User): User {
        TODO("Not yet implemented")
    }

    override fun customDeleteById(user: User): User {
        TODO("Not yet implemented")
    }

    override fun selectAll(): List<User> {
        return executeQuery("select * from users")
    }

    override fun getUserByNickname(nickname: String): User? {
        TODO("Not yet implemented")
    }

    override fun getUserById(id: Int): User? {
        return executeQuery("select * from users where id = $id")[0]
    }

    override fun isUserWithEmailExists(email: String): Int {
        TODO("Not yet implemented")
    }

    override fun getCountUsersByNickname(nickname: String): Int {
        TODO("Not yet implemented")
    }

    override fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    private fun executeQuery(query: String): List<User>{
        val queryResult = entityManager!!.createNativeQuery(query)
            .resultList

        val userList: MutableList<User> = mutableListOf()

        if (!queryResult.isNullOrEmpty()){
                val it: Iterator<*> = queryResult.iterator()
                while (it.hasNext()) {
                    val row = it.next() as Array<Any>
                    userList.add(convertRowToEntity(row).let { convert.convertEntityToModel(it) })
                }
        }

        return userList
    }

    private fun convertRowToEntity(row: Array<Any>): UserEntity{
        return UserEntity(
            id = row[0] as Int,
            nickname = row[1] as String,
            fullname = row[2] as String,
            about = row[3] as String,
            email = row[4] as String,
            count_view_profile = row[5] as Int
        )
    }
}