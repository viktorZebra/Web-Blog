package com.example.webblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebBlogApplication

fun main(args: Array<String>) {
    runApplication<WebBlogApplication>(*args)
}
