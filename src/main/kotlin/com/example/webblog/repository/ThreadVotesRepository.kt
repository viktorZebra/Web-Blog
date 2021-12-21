package com.example.webblog.repository

import com.example.webblog.model.entity.ThreadVotesEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


interface ThreadVotesRepository : CrudRepository<ThreadVotesEntity, Int> {
}