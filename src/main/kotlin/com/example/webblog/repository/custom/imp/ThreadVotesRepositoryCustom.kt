package com.example.webblog.repository.custom.imp

import com.example.webblog.model.ThreadVotes
import com.example.webblog.model.mapper.ThreadVotesMapper
import com.example.webblog.repository.ThreadVotesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class ThreadVotesRepositoryCustom @Autowired constructor(private val rep: ThreadVotesRepository,
                                                         private val convert: ThreadVotesMapper) {
    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(threadVotes: ThreadVotes): ThreadVotes {

        return rep.save(
            threadVotes.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

}