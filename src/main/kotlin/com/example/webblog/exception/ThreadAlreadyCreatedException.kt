package com.example.webblog.exception

import com.example.webblog.model.entity.ThreadsEntity

class ThreadAlreadyCreatedException(val existedThread: ThreadsEntity) : RuntimeException()
