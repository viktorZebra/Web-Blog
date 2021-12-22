package com.example.webblog.controller

import com.example.webblog.model.dto.ForumsDto
import com.example.webblog.service.StatisticsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/statistics")
class StatisticsController(val statisticsService: StatisticsService) {
    @GetMapping("/threads")
    fun threads(): Int{
        return statisticsService.threads()
    }

    @GetMapping("/forums")
    fun forums(): Int{
        return statisticsService.forums()
    }

    @GetMapping("/users")
    fun users(): Int{
        return statisticsService.users()
    }
}