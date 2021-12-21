package com.example.webblog.repository.custom.imp

import com.example.webblog.model.Threads
import com.example.webblog.model.mapper.ThreadsMapper
import com.example.webblog.repository.ThreadsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ThreadsRepositoryCustom @Autowired constructor(private val rep: ThreadsRepository,
                                                     private val convert: ThreadsMapper) {

    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(threads: Threads): Threads {

        return rep.save(
            threads.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun getThreadBySlug(slug: String): Threads?{
        val threads = rep.getThreadBySlug(slug)

        if(threads != null){
            return threads.let { convert.convertEntityToModel(it) }
        }

        return threads
    }


    fun isThreadExistsById(id: Int): Int{
        return rep.isThreadExistsById(id)
    }


    fun getThreadById(id: Int): Threads?{
        val threads = rep.getThreadById(id)

        if(threads != null){
            return threads.let { convert.convertEntityToModel(it) }
        }

        return threads
    }


    fun getThreadByForum(id: Int): MutableList<Threads?>{
        val threads = rep.getThreadByForum(id)

        val threadsMutList: MutableList<Threads?> = mutableListOf()
        for (thread in threads){
            if (thread != null)
                threadsMutList.add(thread.let { convert.convertEntityToModel(it) })
        }

        return threadsMutList
    }

}