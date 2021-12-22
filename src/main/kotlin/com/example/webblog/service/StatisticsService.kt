package com.example.webblog.service

import com.example.webblog.model.Statistics
import com.example.webblog.repository.custom.imp.StatisticsRepositoryCustom
import org.springframework.stereotype.Service

@Service
class StatisticsService(val statisticsRepository: StatisticsRepositoryCustom) {

    fun save(tmp: Statistics){
        statisticsRepository.save(tmp)
    }

    fun forums(): Int{
        return statisticsRepository.forums()
    }

    fun users(): Int{
        return statisticsRepository.users()
    }

    fun threads(): Int{
        return statisticsRepository.threads()
    }

    fun getStatistics(): Statistics{
        return statisticsRepository.getStatistics()
    }
}