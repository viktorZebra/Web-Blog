package com.example.webblog.repository.custom.imp

import com.example.webblog.model.Statistics
import com.example.webblog.model.entity.StatisticsEntity
import com.example.webblog.model.mapper.StatisticsMapper
import com.example.webblog.repository.StatisticsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class StatisticsRepositoryCustom  @Autowired constructor(private val rep: StatisticsRepository,
                                                         private val convert: StatisticsMapper) {
    fun delete(id: Int){
        rep.deleteById(id)
    }

    fun save(statistics: Statistics): Statistics {

        return rep.save(
            statistics.let { convert.convertModelToEntity(it) }
        )
            .let { convert.convertEntityToModel(it) }
    }

    fun updateUsers() {
        return rep.updateCountUsers()
    }

    fun updateForums() {
        return rep.updateCountForums()
    }

    fun updateThreads() {
        return rep.updateCountThreads()
    }

    fun forums(): Int{
        return rep.forums()
    }

    fun users(): Int{
        return rep.users()
    }

    fun threads(): Int{
        return rep.threads()
    }

    fun getStatistics(): Statistics{
        return rep.getStatistics().let { convert.convertEntityToModel(it) }
    }
}