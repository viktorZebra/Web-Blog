package com.example.webblog.repository.custom.imp

import com.example.webblog.model.Forums
import com.example.webblog.model.mapper.ForumsMapper
import com.example.webblog.repository.ForumsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ForumsRepositoryCustom @Autowired constructor(private val rep: ForumsRepository,
                                                    private val convert: ForumsMapper) {
    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(forums: Forums): Forums {

        return rep.save(
            forums.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun getForumBySlug(slug: String): Forums?{
        val forums = rep.getForumBySlug(slug)

        if(forums != null){
            return forums.let { convert.convertEntityToModel(it) }
        }

        return forums
    }

    fun getForumById(id: Int): Forums?{
        val forums = rep.getForumById(id)

        if(forums != null){
            return forums.let { convert.convertEntityToModel(it) }
        }

        return forums
    }

    fun getCountForum(slug: String): Int{
        return rep.getCountForum(slug)
    }

    fun getAllForums(): List<Forums>{
        return  rep.findAll().map { convert.convertEntityToModel(it) }
    }
}