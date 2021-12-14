package com.example.webblog.exception

import com.example.webblog.model.entity.ForumsEntity

class ForumAlreadyCreatedException(val forumsModel: ForumsEntity) : RuntimeException() {
}
